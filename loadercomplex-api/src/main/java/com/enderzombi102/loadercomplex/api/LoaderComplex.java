package com.enderzombi102.loadercomplex.api;

import com.enderzombi102.loadercomplex.api.addon.AddonLoader;
import com.enderzombi102.loadercomplex.api.addon.AddonContainer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * The LoaderComplex main class.<br>
 * may be used to do more <i>complex</i> stuff.
 */
public interface LoaderComplex {
	/**
	 * Get the current {@link AddonLoader} instance
	 */
	@NotNull AddonLoader getAddonLoader();

	/**
	 * Get a container from an addon id
	 */
	Optional<AddonContainer> getContainer(String id);

	/**
	 * Whether this object is a dummy
	 */
	default boolean isDummy() {
		return false;
	}
}
