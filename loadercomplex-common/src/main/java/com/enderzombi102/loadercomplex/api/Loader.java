package com.enderzombi102.loadercomplex.api;

import com.enderzombi102.loadercomplex.api.utils.LoaderType;
import com.enderzombi102.loadercomplex.api.utils.Version;
import com.enderzombi102.loadercomplex.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Loader {
	/**
	 * Returns the currently running loader type
	 */
	LoaderType getLoaderType();

	/**
	 * Returns the registry implementation for this loader
	 */
	Registry getRegistry();

	/**
	 * Returns the currently running minecraft version
	 */
	String getMinecraftVersion();

	/**
	 * Returns the version of the currently running loader
	 */
	String getLoaderVersion();

	/**
	 * Checks with the underlying loader if a mod is loaded
	 * @param id of the mod to check
	 * @return true if it is
	 */
	boolean isModLoaded(String id);

	/**
	 * Asks the underlying loader if we're on a developer environment
	 * @return true if we are in one
	 */
	boolean isDeveloperEnvironment();

	/**
	 * Returns the API implementation version of this layer, might be different based on underlying loader
	 */
	default Version getApiVersion() {
		return Utils.getApiVersion( isDeveloperEnvironment() );
	}

	/**
	 * Factory method for logger implementations
	 * @param addonid id of the addon asking for a logger
	 * @return a Logger object
	 */
	default Logger getLogger(String addonid) {
		return LogManager.getLogger(addonid);
	}

}
