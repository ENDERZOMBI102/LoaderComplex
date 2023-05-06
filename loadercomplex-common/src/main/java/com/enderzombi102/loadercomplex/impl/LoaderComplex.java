package com.enderzombi102.loadercomplex.impl;

import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.api.addon.AddonLoader;
import com.enderzombi102.loadercomplex.impl.addon.AddonLoaderImpl;
import com.enderzombi102.loadercomplex.impl.addon.AddonContainerImpl;
import com.enderzombi102.loadercomplex.api.addon.AddonContainer;
import com.enderzombi102.loadercomplex.impl.addon.finder.AddonFinder;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Internal class used by LoaderComplex implementations, should not be used by addons.
 */
@Internal
public abstract class LoaderComplex implements com.enderzombi102.loadercomplex.api.LoaderComplex {
	private static LoaderComplex instance;

	private boolean initialized = false;
	private final @NotNull Loader loader;
	private final @NotNull Logger logger;
	private final @NotNull AddonLoaderImpl addonLoader;
	private @NotNull Consumer<AddonContainerImpl> resourceHelper;

	public LoaderComplex( @NotNull String name, @NotNull Loader loader ) {
		instance = this;
		this.loader = loader;
		this.resourceHelper = container -> { };
		this.addonLoader = new AddonLoaderImpl();
		this.logger = LoggerFactory.getLogger( "LoaderComplex | " + name );
	}

	public void loadAddons( @NotNull List<AddonFinder> finders ) {
		this.addonLoader.loadAddons( finders );
	}

    public void initAddons() {
		if (! this.initialized ) {
			this.initialized = true;
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
		return this.loader;
	}

	public @NotNull Logger getLogger() {
		return this.logger;
	}

	protected void setResourceHelper( @NotNull Consumer<AddonContainerImpl> resourceHelper ) {
		this.resourceHelper = resourceHelper;
	}

	public static @NotNull LoaderComplex get() {
		return instance;
	}
}
