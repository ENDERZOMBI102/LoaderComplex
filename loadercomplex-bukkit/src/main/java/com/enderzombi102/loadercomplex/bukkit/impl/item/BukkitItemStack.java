package com.enderzombi102.loadercomplex.bukkit.impl.item;

import com.enderzombi102.loadercomplex.api.item.ItemStack;
import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;

public class BukkitItemStack implements ItemStack {

	private final org.bukkit.inventory.ItemStack stack;

	public BukkitItemStack(org.bukkit.inventory.ItemStack stack) {
		this.stack = stack;
	}
	
	@Override
	public Object getStack() {
		return stack;
	}

	@Override
	public String getDisplayName() {
		return this.stack.getItemMeta().getDisplayName();
	}

	@Override
	public void setDisplayName(String newName) {
		this.stack.getItemMeta().setDisplayName( newName );
	}

	@Override
	public ResourceIdentifier getType() {
		return new ResourceIdentifier( "minecraft", this.stack.getType().name().toLowerCase() );
	}

	@Override
	public int getAmount() {
		return this.stack.getAmount();
	}

	@Override
	public void setAmount(int amount) {
		this.stack.setAmount( amount );
	}

	@Override
	public void setDurability(short durability) {
		this.stack.setDurability( durability );
	}

	@Override
	public int getDurability() {
		return this.stack.getDurability();
	}

	@Override
	public int getMaxDurability() {
		return this.stack.getType().getMaxDurability();
	}

	@Override
	public int getMaxStackSize() {
		return this.stack.getMaxStackSize();
	}
}
