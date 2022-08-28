package com.enderzombi102.loadercomplex;

import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.addonloader.AddonLoaderImpl;
import com.enderzombi102.loadercomplex.addonloader.AddonContainerImpl;
import com.enderzombi102.loadercomplex.api.LoaderComplex;
import com.enderzombi102.loadercomplex.api.addonloader.AddonContainer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Internal class used by LoaderComplex implementations, should not be used by addons.
 */
@ApiStatus.Internal
public abstract class LoaderComplexBase implements LoaderComplex {
	protected Consumer<AddonContainerImpl> resourceHelper = container -> { };
	private final AddonLoaderImpl addonLoader = new AddonLoaderImpl();
	private boolean addonsInitialized = false;
	protected Loader loader;

	public LoaderComplexBase() {
		this.addonLoader.loadAddons();
	}

	protected void initAddons() {
		if (! this.addonsInitialized ) {
			this.addonsInitialized = true;
			for ( AddonContainerImpl container : this.addonLoader.getAddons() ) {
				this.resourceHelper.accept(container);
				container.getImplementation().init(this.loader);
			}
		}
	}

	@Override
	public @NotNull AddonLoaderImpl getAddonLoader() {
		return this.addonLoader;
	}

	@Override
	public Optional<AddonContainer> getContainer( @NotNull String id ) {
		return this.addonLoader.getAddons().stream()
				.filter( container -> container.getId().equals(id) )
				.findFirst()
				.map( AddonContainer.class::cast );
	}

	public boolean didInitializeAddons() {
		return this.addonsInitialized;
	}
}
