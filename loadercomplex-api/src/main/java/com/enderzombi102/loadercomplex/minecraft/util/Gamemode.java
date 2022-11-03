package com.enderzombi102.loadercomplex.minecraft.util;

import com.enderzombi102.loadercomplex.api.Loader;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a player's gamemode
 */
@ApiStatus.AvailableSince("0.1.3")
public enum Gamemode {
	CREATIVE,
	SURVIVAL,
	ADVENTURE,
	SPECTATOR;

	/**
	 * Checks if this gamemode is supported for the current platform.
	 * @param loader current loader object.
	 * @return true if supported, false otherwise.
	 */
	public boolean isSupported(Loader loader) {
		switch ( this ) {
			case CREATIVE:
			case SURVIVAL:
				return true;
			case ADVENTURE:
				return loader.isAtLeastMinecraft("1.3.1");
			case SPECTATOR:
				return loader.isAtLeastMinecraft("1.8");
			default:
				return false;
		}
	}
}
