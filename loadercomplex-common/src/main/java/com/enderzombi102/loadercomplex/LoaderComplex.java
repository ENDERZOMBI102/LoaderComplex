package com.enderzombi102.loadercomplex;

import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.addonloader.AddonLoader;
import com.enderzombi102.loadercomplex.addonloader.AddonContainer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Internal class used by LoaderComplex implementations, should not be used by addons.
 */
public abstract class LoaderComplex implements com.enderzombi102.loadercomplex.api.LoaderComplex {
	private final AddonLoader addonLoader = new AddonLoader();
	protected Consumer<AddonContainer> resourceHelper = container -> { };
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

	@Override
	public @NotNull AddonLoader getAddonLoader() {
		return this.addonLoader;
	}

	@Override
	public Optional<com.enderzombi102.loadercomplex.api.addonloader.AddonContainer> getContainer( @NotNull String id ) {
		return this.addonLoader.getAddons().stream()
				.filter( container -> container.getId().equals(id) )
				.findFirst()
				.map(com.enderzombi102.loadercomplex.api.addonloader.AddonContainer.class::cast);
	}

	public boolean didInitializeAddons() {
		return this.addonsInitialized;
	}

}
