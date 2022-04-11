package com.enderzombi102.loadercomplex.api.item;

/**
 * Represents an {@link ItemStack} in an inventory.
 */
public interface ItemStack {

	/**
	 * Getter for the raw ItemStack object
	 */
	Object getStack();

	/**
	 * Getter for the name of this {@link ItemStack}
	 * @return
	 */
	String getDisplayName();
	void setDisplayName(String newName);
	Object getType();
	int getAmount();
	void setAmount(int amount);

	/**
	 * Sets the damage of this itemstack.
	 * @param damage new damage, may be casted to short on some platforms.
	 */
	void setDamage(final int damage);
	int getDamage();
	int getMaxDurability();
	int getMaxStackSize();
}
