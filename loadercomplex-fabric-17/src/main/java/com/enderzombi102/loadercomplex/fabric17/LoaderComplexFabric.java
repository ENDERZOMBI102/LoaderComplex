package com.enderzombi102.loadercomplex.fabric17;

import com.enderzombi102.loadercomplex.LoaderComplex;
import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.fabric17.impl.FabricLoader;
import net.fabricmc.api.ModInitializer;

import java.util.ArrayList;

public class LoaderComplexFabric extends LoaderComplex implements ModInitializer {
	public static final ArrayList<FabricResourcePack> packs = new ArrayList<>();
	public static LoaderComplexFabric INSTANCE;

	public LoaderComplexFabric() {
		super();
		loader = new FabricLoader();
		resourceHelper = mod -> packs.add( new FabricResourcePack(mod) );
		INSTANCE = this;
	}

	@Override
	public void onInitialize() {
		initAddons();
	}

	public Loader getLoader() {
		return loader;
	}
}
