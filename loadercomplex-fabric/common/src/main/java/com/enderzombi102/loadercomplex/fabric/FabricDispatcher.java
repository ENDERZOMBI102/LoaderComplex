package com.enderzombi102.loadercomplex.fabric;

import com.enderzombi102.loadercomplex.impl.KotlinLoader;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;

/**
 * Dispatcher for any fabric version
 */
public class FabricDispatcher implements ModInitializer {
	private final ModInitializer impl;

	public FabricDispatcher() {
		try {
			switch ( getMinecraftVersion() ) {
				case "1.12.2":
					impl = construct( "com.enderzombi102.loadercomplex.fabric12.LoaderComplexFabric" );
					break;
				case "1.17.1":
					impl = construct( "com.enderzombi102.loadercomplex.fabric17.LoaderComplexFabric" );
					break;
				case "b1.7.3":
					impl = construct( "com.enderzombi102.loadercomplex.fabric173.LoaderComplexFabric" );
					break;
				default:
					throw new IllegalStateException( String.format( "Fabric for %s is not supported!", getMinecraftVersion() ) );
			}
		} catch ( Exception e ) {
			throw new IllegalStateException( e );
		}
		System.out.println("Hello world!");
	}

	private static @NotNull ModInitializer construct( @NotNull String path ) {
		return (ModInitializer) KotlinLoader.bootstrap( path );
	}

	@Override
	public void onInitialize() {
		this.impl.onInitialize();
	}

	public static String getMinecraftVersion() {
		//noinspection OptionalGetWithoutIsPresent
		return FabricLoader.getInstance()
			.getModContainer("minecraft")
			.get()
			.getMetadata()
			.getVersion()
			.getFriendlyString();
	}
}
