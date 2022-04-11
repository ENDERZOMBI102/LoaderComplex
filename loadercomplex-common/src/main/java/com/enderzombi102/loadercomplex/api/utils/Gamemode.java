package com.enderzombi102.loadercomplex.api.utils;

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
