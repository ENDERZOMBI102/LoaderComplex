package com.enderzombi102.loadercomplex.fabric;

import com.enderzombi102.loadercomplex.impl.EnvironmentLoader;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;

/**
 * Dispatcher for any fabric version
 */
public class FabricDispatcher implements ModInitializer {
	private final ModInitializer impl;

	public FabricDispatcher() {
		switch ( getMinecraftVersion() ) {
			case "1.12.2":
				this.impl = construct( "com.enderzombi102.loadercomplex.fabric122.LoaderComplexFabric" );
				break;
			case "1.17.1":
				this.impl = construct( "com.enderzombi102.loadercomplex.fabric171.LoaderComplexFabric" );
				break;
			case "b1.7.3":
				this.impl = construct( "com.enderzombi102.loadercomplex.fabric73b.LoaderComplexFabric" );
				break;
			default:
				throw new IllegalStateException( String.format( "Fabric for %s is not supported!", getMinecraftVersion() ) );
		}

		System.out.println( "Hello world!" );
	}

	private static @NotNull ModInitializer construct( @NotNull String path ) {
		return (ModInitializer) EnvironmentLoader.bootstrap( path );
	}

	@Override
	public void onInitialize() {
		this.impl.onInitialize();
	}

	public static String getMinecraftVersion() {
		//noinspection OptionalGetWithoutIsPresent
		return FabricLoader.getInstance()
			.getModContainer( "minecraft" )
			.get()
			.getMetadata()
			.getVersion()
			.getFriendlyString();
	}
}
