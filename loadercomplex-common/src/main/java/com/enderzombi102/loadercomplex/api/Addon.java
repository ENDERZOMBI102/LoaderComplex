package com.enderzombi102.loadercomplex.api;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an addon for LoaderComplex.<br/>
 *<br/>
 * All addons must have an implementation of this class.
 */
public interface Addon {

	/**
	 * Called on mod initialization, do all your stuff here.
	 * @param loader a {@link Loader} implementation.
	 */
	void init(Loader loader);

	/**
	 * Getter for the authors of this addon
	 * @return a comma-separated list of names
	 */
	String getAuthors();

	/**
	 * Getter for this addon's name.<br/>
	 * If this returns null, the name will be obtained from the file name.
	 * @return addon's name
	 */
	default @Nullable String getName() { return null; }

	/**
	 * Getter for the Addon's description
	 */
	default String getDescription() { return ""; }

	/**
	 * Getter for the Addon's icon path
	 */
	default @Nullable String getIconPath() { return null; }

	/**
	 * Getter for the Addon's links
	 * The map may contain the following keys:<br/>
	 *  - source<br/>
	 *  - discord<br/>
	 *  - website<br/>
	 *  - issues<br/>
	 *
	 * <b>NOTE:</b> some LoaderComplex implementations may not show all links
	 */
	default Map<String, String> getLinks() { return new HashMap<>(); }
}
