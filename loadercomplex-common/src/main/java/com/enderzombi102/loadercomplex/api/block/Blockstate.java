package com.enderzombi102.loadercomplex.api.block;

import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a blockstate.
 */
@ApiStatus.AvailableSince("0.1.3")
public interface Blockstate {

	/**
	 * Getter for the type of the block this blockstate represents
	 */
	ResourceIdentifier getBlockType();

	/**
	 * Getter for the raw BlockState object
	 */
	Object getObject();
}
