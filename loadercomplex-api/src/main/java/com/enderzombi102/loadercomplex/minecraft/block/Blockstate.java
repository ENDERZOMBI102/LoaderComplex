package com.enderzombi102.loadercomplex.minecraft.block;

import com.enderzombi102.loadercomplex.minecraft.util.ResourceIdentifier;
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
