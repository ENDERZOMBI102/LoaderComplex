package com.enderzombi102.loadercomplex.fabric;

import com.enderzombi102.loadercomplex.fabric12.LoaderComplexFabric;
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
		switch ( ver ) {
			case "1.12.2":
				impl = new LoaderComplexFabric();
				break;
			case "1.17.1":
				try {
					impl = (ModInitializer) Class.forName("com.enderzombi102.loadercomplex.fabric17.LoaderComplexFabric")
							.getConstructor()
							.newInstance();
				} catch (Exception e) { throw new IllegalStateException( e ); }
				break;
			default:
				throw new IllegalStateException( Utils.format( "Fabric for {} is not supported!", ver ) );
		}
	}

	@Override
	public void onInitialize() {
		this.impl.onInitialize();
	}
}
