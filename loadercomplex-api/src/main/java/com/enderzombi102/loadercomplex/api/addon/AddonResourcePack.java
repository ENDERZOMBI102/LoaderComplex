package com.enderzombi102.loadercomplex.api.addon;

import org.jetbrains.annotations.ApiStatus;

import java.util.Set;

/**
 * Represents an {@link Addon}'s resource pack
 */
@ApiStatus.AvailableSince( "0.1.4" )
public interface AddonResourcePack {
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
