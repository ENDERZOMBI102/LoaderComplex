package com.enderzombi102.loadercomplex.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import static com.enderzombi102.enderlib.SafeUtils.doSafely;

/**
 * Dispatcher for any fabric version
 */
public class FabricDispatcher implements ModInitializer {
	private static final ModInitializer impl;
	private static boolean initialized = false;

	static {
		impl = doSafely( () -> {
			switch ( getMinecraftVersion() ) {
				case "1.12.2":
					return create( "fabric12.LoaderComplexFabric" );
				case "1.17.1":
					return create( "fabric17.LoaderComplexFabric" );
				case "b1.7.3":
					return create( "fabric173.LoaderComplexFabric" );
				default:
					throw new IllegalStateException( String.format( "Fabric for %s is not supported!", getMinecraftVersion() ) );
			}
		} );
	}

	@Override
	public void onInitialize() {
		// prevent double init
		if ( !initialized ) {
			impl.onInitialize();
			initialized = true;
		}
	}

	public static String getMinecraftVersion() {
		return FabricLoader.getInstance()
			.getModContainer( "minecraft" )
			.orElseThrow( IllegalStateException::new )
			.getMetadata()
			.getVersion()
			.getFriendlyString();
	}

	private static ModInitializer create( String clazz ) throws Throwable {
		return (ModInitializer) Class.forName( "com.enderzombi102.loadercomplex." + clazz ).getConstructor().newInstance();
	}
}
