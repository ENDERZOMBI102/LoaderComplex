package com.enderzombi102.loadercomplex.api.item;

public interface ItemStack {

	Object getStack();
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
