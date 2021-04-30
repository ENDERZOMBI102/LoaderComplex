package com.enderzombi102.loadercomplex.bukkit.impl.item;

import com.enderzombi102.loadercomplex.api.item.ItemStack;

public class BukkitItemStack implements ItemStack {

	private final org.bukkit.inventory.ItemStack stack;

	public BukkitItemStack(org.bukkit.inventory.ItemStack stack) {
		this.stack = stack;
	}
	
	@Override
	public Object getStack() {
		return stack;
	}
}
