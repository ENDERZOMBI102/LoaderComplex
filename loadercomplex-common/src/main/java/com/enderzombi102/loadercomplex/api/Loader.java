package com.enderzombi102.loadercomplex.api;

import com.enderzombi102.loadercomplex.api.utils.FactoryWorld;
import com.enderzombi102.loadercomplex.api.utils.LoaderType;
import com.enderzombi102.loadercomplex.api.utils.Version;
import com.enderzombi102.loadercomplex.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;

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
	 * Returns an implementation of {@link FactoryWorld}, an object capable
	 * of creating stuff normally inacessible.
	 */
	@ApiStatus.AvailableSince("0.1.3")
	default FactoryWorld getFactoryWorld() { return null; };

	/**
	 * Returns true if we're running on at least minecraft version
	 * @param version minecraft version to check
	 */
	@ApiStatus.AvailableSince("0.1.3")
	default boolean isAtLeastMinecraft(String version) { return true; };


	/**
	 * Returns the API implementation version of this layer, might be different based on underlying loader
	 */
	default Version getApiVersion() {
		return new Version( "0.1.3", Utils.getApiVersion(true).getBuildDate() );
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
