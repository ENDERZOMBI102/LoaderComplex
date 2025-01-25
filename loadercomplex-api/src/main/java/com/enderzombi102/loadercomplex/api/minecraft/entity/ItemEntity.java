package com.enderzombi102.loadercomplex.api.minecraft.entity;

import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a dropped ItemStack
 */
@ApiStatus.AvailableSince( "0.1.3" )
public interface ItemEntity extends Entity {

	/**
	 * Getter for the {@link ItemStack} this entity holds
	 */
	ItemStack getStack();

	/**
	 * Setter for the {@link ItemStack} this entity holds
	 */
	void setStack( ItemStack stack );
}
