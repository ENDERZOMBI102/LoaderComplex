package com.enderzombi102.loaderComplex.fabric12;

import com.enderzombi102.loaderComplex.fabric12.impl.FabricLoader;
import com.enderzombi102.loadercomplex.LoaderComplex;
import net.fabricmc.api.ModInitializer;

public class LoaderComplexFabric extends LoaderComplex implements ModInitializer {

	public FabricLoader loader = new FabricLoader();

	public LoaderComplexFabric() {
		super();
	}

	@Override
	public void onInitialize() {
		this.initMods();
	}
}
