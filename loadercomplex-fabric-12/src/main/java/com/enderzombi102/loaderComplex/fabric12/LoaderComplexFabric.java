package com.enderzombi102.loaderComplex.fabric12;

import com.enderzombi102.loaderComplex.fabric12.impl.FabricLoader;
import com.enderzombi102.loadercomplex.LoaderComplex;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resource.ResourcePack;

import java.util.ArrayList;

public class LoaderComplexFabric extends LoaderComplex implements ModInitializer {

	public static final ArrayList<FabricResourcePack> packs = new ArrayList<>();

	public LoaderComplexFabric() {
		super();
		this.loader = new FabricLoader();
		this.resourceHelper = mod -> packs.add( new FabricResourcePack(mod) );
	}

	@Override
	public void onInitialize() {
		this.initMods();
	}

}
