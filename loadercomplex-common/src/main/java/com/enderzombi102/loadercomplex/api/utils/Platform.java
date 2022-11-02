package com.enderzombi102.loadercomplex.api.utils;

import org.jetbrains.annotations.ApiStatus.AvailableSince;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the loader that LoaderComplex is currently running under.
 */
@AvailableSince("0.2.0")
public interface Platform {
	/** The name of the platform. */
	@NotNull String name();

	/** The version of the currently running loader. */
	@NotNull Version version();

	/** The id of the platform. */
	@NotNull String id();

	/** The currently running minecraft version. */
	@NotNull String minecraftVersion();
}
