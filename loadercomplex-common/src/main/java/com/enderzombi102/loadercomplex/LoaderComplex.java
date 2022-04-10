package com.enderzombi102.loadercomplex;

import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.modloader.LCAddonLoader;
import com.enderzombi102.loadercomplex.modloader.AddonContainer;

import java.util.function.Consumer;

/**
 * Internal class, should not be used.
 */
public abstract class LoaderComplex {

	protected Loader loader;
	protected Consumer<AddonContainer> resourceHelper;
	private final LCAddonLoader addonLoader = new LCAddonLoader();

	public LoaderComplex() {
		this.addonLoader.loadAddons();
	}

	protected void initAddons() {
		for ( AddonContainer container : this.addonLoader.getAddons() ) {
			this.resourceHelper.accept(container);
			container.getImplementation().init(this.loader);
		}
	}

	public LCAddonLoader getAddonLoader() {
		return this.addonLoader;
	}

}
