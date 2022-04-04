package com.enderzombi102.loaderComplex.fabric;

import com.enderzombi102.loaderComplex.fabric12.LoaderComplexFabric;
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
		//noinspection SwitchStatementWithTooFewBranches
		switch ( ver ) {
			case "1.12.2":
				impl = new LoaderComplexFabric();
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
