package com.enderzombi102.loadercomplex.api.minecraft.item;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an {@link ItemStack} in an inventory.
 */
public interface ItemStack {

	/**
	 * Getter for the raw ItemStack object
	 */
	@NotNull Object getStack();

	/**
	 * Getter for the name of this {@link ItemStack}
	 */
	@Nullable String getDisplayName();
	void setDisplayName(String newName);
	@NotNull Object getType();
	int getAmount();
	void setAmount(int amount);

	/**
	 * Sets the damage this item stack has.
	 * @param damage new damage, may be cast to short on some platforms.
	 */
	void setDamage( final int damage );
	int getDamage();
	int getMaxDurability();
	int getMaxStackSize();
}
