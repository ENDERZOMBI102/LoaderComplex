package com.enderzombi102.loadercomplex.api;

import com.enderzombi102.loadercomplex.api.minecraft.command.CommandManager;
import com.enderzombi102.loadercomplex.api.minecraft.keybind.KeybindManager;
import com.enderzombi102.loadercomplex.api.minecraft.network.NetworkManager;
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

	/**
	 * Getter for the loader's {@link KeybindManager}, an object capable of:<br>
	 * - Registering keybindings<br>
	 * - Querying registered keybindings<br>
	 */
	@AvailableSince( "0.2.0" )
	default KeybindManager getKeybindManager() {
		throw new IllegalStateException( "This function has not been implemented in this impl!" );
	}

	/**
	 * Getter for the loader's {@link NetworkManager}, an object capable of creating network channels.<br>
	 * Channels can:<br>
	 * - Send data between client server ( full-duplex )<br>
	 * - Nothing else, what do you expect??
	 */
	@AvailableSince( "0.2.0" )
	default NetworkManager getNetworkManager() {
		throw new IllegalStateException( "This function has not been implemented in this impl!" );
	}

	/**
	 * Getter for the loader's {@link CommandManager}, an object capable of creating commands.
	 */
	@AvailableSince( "0.2.0" )
	default CommandManager getCommandManager() {
		throw new IllegalStateException( "This function has not been implemented in this impl!" );
	}
}
