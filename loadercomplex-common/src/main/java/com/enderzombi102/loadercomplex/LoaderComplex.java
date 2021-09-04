package com.enderzombi102.loadercomplex;

import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.modloader.LCModLoader;
import com.enderzombi102.loadercomplex.modloader.Mod;

import java.util.function.Consumer;

public abstract class LoaderComplex {

	protected Loader loader;
	protected Consumer<Mod> resourceHelper;
	private final LCModLoader modLoader = new LCModLoader();

	public LoaderComplex() {
		this.modLoader.loadMods();
	}

	protected void initMods() {
		for ( Mod mod : this.modLoader.getAddons() ) {
			this.resourceHelper.accept(mod);
			mod.getImplementation().init(this.loader);
		}
	}

	public LCModLoader getModLoader() {
		return this.modLoader;
	}

}
