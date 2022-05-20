package com.enderzombi102.loadercomplex.api;

import com.enderzombi102.eventsystem.EventSystem;
import com.enderzombi102.loadercomplex.api.addonloader.AddonContainer;
import com.enderzombi102.loadercomplex.api.utils.FactoryWorld;
import com.enderzombi102.loadercomplex.api.utils.LoaderType;
import com.enderzombi102.loadercomplex.api.utils.Version;
import com.enderzombi102.loadercomplex.Utils;
import com.enderzombi102.loadercomplex.addonloader.AddonLoader;
import org.jetbrains.annotations.ApiStatus.AvailableSince;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * The main LoaderComplex interface, many methods require this as parameter.<br/>
 * <br/>
 * This is the main way you'll be able to interact with the underlying modloader
 */
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
	@AvailableSince("0.1.3")
	default FactoryWorld getFactoryWorld() { return null; };

	/**
	 * Returns true if we're running on at least minecraft version
	 * @param version minecraft version to check
	 */
	@AvailableSince("0.1.3")
	default boolean isAtLeastMinecraft(String version) { return true; };

	/**
	 * Returns true if LC is running on a dedicated server
	 */
	@AvailableSince("0.1.4")
	default boolean isDedicatedServer() { return false; };

	/**
	 * Returns the API implementation version of this layer, might be different based on underlying loader.
	 */
	default Version getApiVersion() {
		return new Version( "0.1.3", Utils.getApiVersion( isDeveloperEnvironment() ).getBuildDate() );
	}

	/**
	 * Factory method for logger implementations
	 * @param addonid id of the addon asking for a logger
	 * @return a Logger object
	 */
	default Logger getLogger(String addonid) {
		return LoggerFactory.getLogger(addonid);
	}

	/**
	 * Returns the current LoaderComplex implementation.<br>
	 * If the underlying implementation doesn't support the required api level, a dummy object will be returned.<br>
	 * You can check whether you got a dummy object by calling {@link LoaderComplex#isDummy()}
	 */
	@AvailableSince( "0.1.4" )
	default @NotNull LoaderComplex getLoaderComplex() {
		return new LoaderComplex() {
			@Override
			public @NotNull AddonLoader getAddonLoader() {
				throw new IllegalStateException("This is a dummy object! Do not use it!");
			}

			@Override
			public Optional<AddonContainer> getContainer(String id) {
				throw new IllegalStateException("This is a dummy object! Do not use it!");
			}

			@Override
			public boolean isDummy() {
				return true;
			}
		};
	}


	/**
	 * Returns the {@link EventSystem} instance used by LoaderComplex.<br>
	 * If the underlying implementation doesn't support the required api level, a new dummy object will be returned.<br>
	 */
	@AvailableSince( "0.2.0" )
	default @NotNull EventSystem getEventSystem() {
		return new EventSystem( LoggerFactory.getLogger("LoaderComplex | EventSystem | Dummy") );
	}

}
