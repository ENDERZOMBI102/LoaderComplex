package com.enderzombi102.loadercomplex;

import com.enderzombi102.loadercomplex.api.ContentMod;
import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.modloader.LCModLoader;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public abstract class LoaderComplex {

	protected Loader loader;
	private final LCModLoader modLoader = new LCModLoader();
	private final ArrayList<ContentMod> mods = new ArrayList<>();

	public LoaderComplex() {
		this.modLoader.loadMods();
		this.mods.addAll( this.modLoader.getMods() );
	}

	protected void initMods() {
		for ( ContentMod mod : mods ) mod.init( this.loader );
	}

	public LCModLoader getModLoader() {
		return this.modLoader;
	}

	public List<ContentMod> getMods() {
		return ImmutableList.copyOf( this.mods );
	}

}
