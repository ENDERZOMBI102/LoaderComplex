package com.enderzombi102.loadercomplex.api.item;

public interface ItemStack {

	Object getStack();
	String getDisplayName();
	void setDisplayName(String newName);
	Object getType();
	int getAmount();
	void setAmount(int amount);

	/**
	 * Sets the durability of this itemstack.
	 * @param durability new durability, may be casted to short on some platforms.
	 */
	void setDurability(final int durability);
	int getDurability();
	int getMaxDurability();
	int getMaxStackSize();

}
