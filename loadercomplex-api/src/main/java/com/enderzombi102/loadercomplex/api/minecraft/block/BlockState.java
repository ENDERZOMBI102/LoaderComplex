package com.enderzombi102.loadercomplex.api.minecraft.block;

import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier;
import org.jetbrains.annotations.ApiStatus;

import static com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier.ri;

/**
 * Represents a blockstate.
 */
@ApiStatus.AvailableSince("0.1.3")
public interface BlockState {

	/**
	 * Getter for the type of the block this blockstate represents
	 */
	ResourceIdentifier getBlockType();

	/**
	 * Getter for the raw BlockState object
	 */
	Object getObject();

	/**
	 * Whether this BlockState is for air.
	 */
	default boolean isAir() {
		return getBlockType().equals( ri( "minecraft:air" ) );
	}
}
