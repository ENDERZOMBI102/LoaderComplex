package com.enderzombi102.loaderComplex.fabric12.impl.item;

import com.enderzombi102.loadercomplex.abstraction.item.ItemStack;

public class FabricItemStack implements ItemStack {

	private final net.minecraft.item.ItemStack stack;


	public FabricItemStack(net.minecraft.item.ItemStack stack) {
		this.stack = stack;
	}

	public net.minecraft.item.ItemStack getStack() {
		return stack;
	}
}
