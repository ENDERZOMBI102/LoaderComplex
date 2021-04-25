package com.enderzombi102.loaderComplex.fabric12;

import com.enderzombi102.loaderComplex.fabric12.impl.FabricLoader;
import com.enderzombi102.loadercomplex.abstraction.ContentMod;
import com.enderzombi102.loadercomplex.testmod.TestMod;
import net.fabricmc.api.ModInitializer;

import java.util.ArrayList;

public class LoaderComplexFabric implements ModInitializer {

	private final ArrayList<ContentMod> mods = new ArrayList<>();
	public FabricLoader loader = new FabricLoader();

	@Override
	public void onInitialize() {
		mods.add( new TestMod() );
		for ( ContentMod mod : mods ) mod.init( this.loader );
	}
}
