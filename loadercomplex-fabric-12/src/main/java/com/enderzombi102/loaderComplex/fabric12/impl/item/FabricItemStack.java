package com.enderzombi102.loaderComplex.fabric12.impl.item;

import com.enderzombi102.loadercomplex.api.item.ItemStack;

public class FabricItemStack implements ItemStack {

	private final net.minecraft.item.ItemStack stack;

	public FabricItemStack(net.minecraft.item.ItemStack stack) {
		this.stack = stack;
	}

	public net.minecraft.item.ItemStack getStack() {
		return stack;
	}

	@Override
	public String getDisplayName() {
		return this.stack.getName();
	}

	@Override
	public void setDisplayName(String newName) {
		this.stack.setCustomName(newName);
	}

	@Override
	public Object getType() {
		return this.stack.getItem();
	}

	@Override
	public int getAmount() {
		return this.stack.getCount();
	}

	@Override
	public void setAmount(int amount) {
		this.stack.setCount(amount);
	}

	@Override
	public void setDurability(short durability) {
		this.stack.setDamage(durability);
	}

	@Override
	public int getDurability() {
		return this.stack.getDamage();
	}

	@Override
	public int getMaxDurability() {
		return this.stack.getMaxDamage();
	}

	@Override
	public int getMaxStackSize() {
		return this.stack.getMaxCount();
	}
}
