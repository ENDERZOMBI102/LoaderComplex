package com.enderzombi102.loadercomplex.api.item;

public interface ItemStack {

	Object getStack();
	String getDisplayName();
	void setDisplayName(String newName);
	Object getType();
	int getAmount();
	void setAmount(int amount);
	void setDurability(final short durability);
	int getDurability();
	int getMaxDurability();
	int getMaxStackSize();

}
