package com.enderzombi102.loadercomplex.impl;

import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.api.addon.AddonLoader;
import com.enderzombi102.loadercomplex.impl.addon.AddonLoaderImpl;
import com.enderzombi102.loadercomplex.impl.addon.AddonContainerImpl;
import com.enderzombi102.loadercomplex.api.addon.AddonContainer;
import com.enderzombi102.loadercomplex.impl.addon.finder.AddonFinder;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Internal class used by LoaderComplex implementations, should not be used by addons.
 */
@Internal
public abstract class LoaderComplex implements com.enderzombi102.loadercomplex.api.LoaderComplex {
	private static boolean initialized = false;
	public static LoaderComplex instance;
	protected @NotNull Consumer<AddonContainerImpl> resourceHelper = container -> { };
	private final @NotNull AddonLoaderImpl addonLoader = new AddonLoaderImpl();
	private final @NotNull Loader loader;

	public LoaderComplex( @NotNull Loader loader ) {
		instance = this;
		this.loader = loader;
	}

	public void loadAddons( @NotNull List<AddonFinder> finders ) {
		this.addonLoader.loadAddons( finders );
	}

    public void initAddons() {
		if (! initialized ) {
			initialized = true;
			for ( AddonContainer container : this.addonLoader.getAddons() ) {
				this.resourceHelper.accept( (AddonContainerImpl) container);
				container.getImplementation().init(this.loader);
			}
		}
	}

	@Override
	public @NotNull AddonLoader getAddonLoader() {
		return this.addonLoader;
	}

	@Override
	public @NotNull Optional<AddonContainer> getContainer( @NotNull String id ) {
		return this.addonLoader.getAddons()
			.stream()
			.filter( container -> container.getId().equals(id) )
			.findFirst()
			.map( AddonContainer.class::cast );
	}

	public @NotNull Loader getLoader() {
		return loader;
	}
}
