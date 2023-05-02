package com.enderzombi102.loadercomplex.api.platform;

import org.jetbrains.annotations.ApiStatus.AvailableSince;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the loader that LoaderComplex is currently running under.
 */
@AvailableSince("0.2.0")
public interface Platform {
	/**
	 * The name of the platform.
	 */
	@NotNull String name();

	/**
	 * The version of the currently running loader.
	 */
	@NotNull String version();

	/**
	 * The id of the platform.
	 */
	@NotNull String id();

	/**
	 * The currently running minecraft version.
	 */
	@NotNull String minecraftVersion();

	/**
	 * Returns the API implementation version of this layer, might be different based on underlying loader.
	 */
	@NotNull String getApiVersion();

	/**
	 * Asks the underlying loader if we're on a developer environment
	 * @return true if we are in one
	 */
	boolean isDeveloperEnvironment();
}
