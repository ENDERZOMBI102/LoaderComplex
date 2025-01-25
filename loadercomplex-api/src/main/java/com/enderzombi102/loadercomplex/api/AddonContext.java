package com.enderzombi102.loadercomplex.api;

import com.enderzombi102.loadercomplex.api.platform.*;
import org.jetbrains.annotations.ApiStatus.AvailableSince;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

/**
 * The main LoaderComplex interface, many methods require this as parameter.
 * <br/><br/>
 * This is the main way you'll be able to interact with the underlying game.
 */
public interface AddonContext {
	/**
	 * Returns the currently running mod loader type
	 */
	@AvailableSince( "0.2.0" )
	@NotNull PlatformInfo getPlatformInfo();

	/**
	 * Returns the registry implementation
	 */
	@NotNull Registry getRegistry();

	/**
	 * Checks with the underlying platform if a mod is loaded
	 *
	 * @param id of the mod to check
	 * @return true if it is
	 */
	boolean isModLoaded( String id );

	/**
	 * Returns an implementation of {@link FactoryWorld}, an object capable
	 * of creating stuff normally inaccessible.
	 */
	@AvailableSince("0.1.3")
	@NotNull FactoryWorld getFactoryWorld();

	/**
	 * Returns true if we're running on at least minecraft version
	 *
	 * @param version minecraft version to check
	 */
	@AvailableSince("0.1.3")
	boolean isAtLeastMinecraft( String version );

	/**
	 * Returns true if LC is running on a dedicated server
	 */
	@AvailableSince("0.1.4")
	boolean isDedicatedServer();

	/**
	 * Factory method for logger implementations
	 *
	 * @param addonId id of the addon asking for a logger
	 * @return a Logger object
	 */
	default @NotNull Logger getLogger( String addonId ) {
		return LoggerFactory.getLogger( addonId );
	}

	/**
	 * Returns the current LoaderComplex implementation.
	 */
	@AvailableSince("0.1.4")
	@NotNull LoaderComplex getLoaderComplex();

	/**
	 * The localization system, used to localize strings.
	 */
	@AvailableSince("0.2.0")
	@NotNull I18nSystem getI18nSystem();

	/**
	 * The resource loader, used for, you guessed it, load resources!
	 */
	@AvailableSince("0.2.0")
	@NotNull ResourceLoader getResourceLoader();

	/**
	 * The config directory for this game instance.
	 */
	@AvailableSince("0.2.0")
	@NotNull Path getConfigDir();
}
