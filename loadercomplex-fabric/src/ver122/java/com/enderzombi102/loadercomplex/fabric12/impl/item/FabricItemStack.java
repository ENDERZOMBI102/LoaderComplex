package com.enderzombi102.loadercomplex.fabric12.impl.item;

import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FabricItemStack implements ItemStack {

	private final net.minecraft.item.ItemStack stack;

	public FabricItemStack( net.minecraft.item.ItemStack stack ) {
		this.stack = stack;
	}

	public net.minecraft.item.@NotNull ItemStack getStack() {
		return this.stack;
	}

	@Override
	public String getDisplayName() {
		return this.stack.getHoverName();
	}

	@Override
	public void setDisplayName( String newName ) {
		this.stack.setHoverName( newName );
	}

	@Override
	public Object getType() {
		return this.stack.getItem();
	}

	@Override
	public int getAmount() {
		return this.stack.getSize();
	}

	@Override
	public void setAmount( int amount ) {
		this.stack.setSize( amount );
	}

	@Override
	public void setDamage( int damage ) {
		this.stack.setDamage( damage );
	}

	@Override
	public int getDamage() {
		return this.stack.getDamage();
	}

	@Override
	public int getMaxDurability() {
		return this.stack.getMaxDamage();
	}

	@Override
	public int getMaxStackSize() {
		return this.stack.getMaxSize();
	}
}
