package com.enderzombi102.loadercomplex.api.addonloader;

import com.enderzombi102.loadercomplex.addonloader.AddonContainer;

import java.util.List;

/**
 * Represents the thing that loaded you.
 */
public interface AddonLoader {
	/**
	 * Returns the list of loaded addon containers
	 */
	List<AddonContainer> getAddons();
}
