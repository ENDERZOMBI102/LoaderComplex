package com.enderzombi102.loadercomplex.fabric;

import com.enderzombi102.loadercomplex.Utils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;

/**
 * Dispatcher for any fabric version
 */
public class FabricDispatcher implements ModInitializer {
	private final ModInitializer impl;

	public FabricDispatcher() {
		String ver = FabricLoaderImpl.INSTANCE.getGameProvider().getNormalizedGameVersion();
		try {
			switch (ver) {
				case "1.12.2":
					impl = (ModInitializer) Class.forName("com.enderzombi102.loadercomplex.fabric12.LoaderComplexFabric")
							.getConstructor()
							.newInstance();
					break;
				case "1.17.1":
					impl = (ModInitializer) Class.forName("com.enderzombi102.loadercomplex.fabric17.LoaderComplexFabric")
							.getConstructor()
							.newInstance();
					break;
				default:
					throw new IllegalStateException( Utils.format( "Fabric for {} is not supported!", ver ) );
			}
		} catch (Exception e) {
				throw new IllegalStateException( e );
		}
	}

	@Override
	public void onInitialize() {
		this.impl.onInitialize();
	}
}
