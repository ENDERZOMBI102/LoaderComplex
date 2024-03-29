package com.enderzombi102.loadercomplex.forge12.impl.item;

import com.enderzombi102.loadercomplex.api.item.ItemStack;

public class ForgeItemStack implements ItemStack {

	private final net.minecraft.item.ItemStack stack;

	public ForgeItemStack(net.minecraft.item.ItemStack stack) {
		this.stack = stack;
	}

	public net.minecraft.item.ItemStack getStack() {
		return stack;
	}

	@Override
	public String getDisplayName() {
		return stack.getDisplayName();
	}

	@Override
	public void setDisplayName(String newName) {
		stack.setStackDisplayName(newName);
	}

	@Override
	public Object getType() {
		return stack.getItem();
	}

	@Override
	public int getAmount() {
		return stack.getCount();
	}

	@Override
	public void setAmount(int amount) {
		stack.setCount(amount);
	}

	@Override
	public void setDamage(int damage) {
		stack.setItemDamage(damage);
	}

	@Override
	public int getDamage() {
		return stack.getItemDamage();
	}

	@Override
	public int getMaxDurability() {
		return stack.getMaxDamage();
	}

	@Override
	public int getMaxStackSize() {
		return stack.getMaxStackSize();
	}

}
