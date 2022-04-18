package com.enderzombi102.loadercomplex;

import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.modloader.AddonLoader;
import com.enderzombi102.loadercomplex.modloader.AddonContainer;

import java.util.function.Consumer;

/**
 * Internal class used by LoaderComplex implementations, should not be used by addons.
 */
public abstract class LoaderComplex {
	private final AddonLoader addonLoader = new AddonLoader();
	protected Consumer<AddonContainer> resourceHelper;
	protected Loader loader;
	private boolean addonsInitialized = false;

	public LoaderComplex() {
		this.addonLoader.loadAddons();
	}

	protected void initAddons() {
		if (! this.addonsInitialized ) {
			this.addonsInitialized = true;
			for ( AddonContainer container : this.addonLoader.getAddons() ) {
				this.resourceHelper.accept(container);
				container.getImplementation().init(this.loader);
			}
		}
	}

	public AddonLoader getAddonLoader() {
		return this.addonLoader;
	}

	public boolean didInitializeAddons() {
		return this.addonsInitialized;
	}

}
