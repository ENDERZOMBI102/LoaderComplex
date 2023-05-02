package com.enderzombi102.loadercomplex.fabric17;

import com.enderzombi102.loadercomplex.fabric17.impl.FabricLoader;
import com.enderzombi102.loadercomplex.impl.LoaderComplex;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class LoaderComplexFabric extends LoaderComplex implements ModInitializer {
	public static final ArrayList<FabricResourcePack> packs = new ArrayList<>();
	public static final Logger LOGGER = LoggerFactory.getLogger( "LoaderComplex | Fabric17" );
	public LoaderComplexFabric() {
		super( new FabricLoader() );
		resourceHelper = mod -> packs.add( new FabricResourcePack(mod) );
	}

	@Override
	public void onInitialize() {
		initAddons();
	}
}
