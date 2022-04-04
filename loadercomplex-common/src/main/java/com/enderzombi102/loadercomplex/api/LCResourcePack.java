package com.enderzombi102.loadercomplex.api;

import java.util.Set;

public interface LCResourcePack {
	/**
	 * Getter for the id of the addon this rp represents.
	 */
	String getAddonID();

	/**
	 * Returns a set with all contained namespaces.
	 */
	Set<String> getNamespaces();

	/**
	 * Returns the name of the rp.
	 */
	String getName();
}
