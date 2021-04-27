package com.enderzombi102.loadercomplex.forge12.impl.item;

import com.enderzombi102.loadercomplex.abstraction.item.ItemStack;

public class ForgeItemStack implements ItemStack {

	private final net.minecraft.item.ItemStack stack;

	public ForgeItemStack(net.minecraft.item.ItemStack stack) {
		this.stack = stack;
	}

	public net.minecraft.item.ItemStack getStack() {
		return stack;
	}

}
