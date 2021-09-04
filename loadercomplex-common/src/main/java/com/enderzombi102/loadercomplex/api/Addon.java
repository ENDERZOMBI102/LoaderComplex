package com.enderzombi102.loadercomplex.api;

import org.jetbrains.annotations.Nullable;

public interface Addon {

	/**
	 * Called on mod initialization, do all your stuff here.
	 * @param loader a {@link Loader} implementation.
	 */
	void init(Loader loader);

	/**
	 * Getter for the authors of this addon
	 * @return a ,-separated list of names
	 */
	String getAuthors();

	/**
	 * Getter for this addon's name.
	 * If this returns null, the name will be obtained from the file name.
	 * @return addon's name
	 */
	default @Nullable String getName() { return null; }

}
