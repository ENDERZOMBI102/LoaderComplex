package com.enderzombi102.loadercomplex.api.minecraft.server;

import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a minecraft server
 */
@ApiStatus.AvailableSince("0.1.3")
public interface Server {

	/**
	 * Getter for the raw Server object
	 */
	Object getObject();
}
