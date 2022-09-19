package com.enderzombi102.loadercomplex.api.addonloader;

import org.jetbrains.annotations.ApiStatus;

import java.util.List;

/**
 * Represents the thing that loaded you.
 */
@ApiStatus.AvailableSince("0.1.4")
public interface AddonLoader {
	/**
	 * Returns the list of loaded addon containers
	 */
	List<AddonContainer> getAddons();
}
