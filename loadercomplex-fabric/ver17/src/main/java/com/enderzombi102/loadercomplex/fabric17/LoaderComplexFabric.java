package com.enderzombi102.loadercomplex.fabric17;

import com.enderzombi102.loadercomplex.impl.LoaderComplex;
import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.fabric17.impl.FabricLoader;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class LoaderComplexFabric extends LoaderComplex implements ModInitializer {
	public static final ArrayList<FabricResourcePack> packs = new ArrayList<>();
	public static final Logger LOGGER = LoggerFactory.getLogger( "LoaderComplex | Fabric17" );
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
