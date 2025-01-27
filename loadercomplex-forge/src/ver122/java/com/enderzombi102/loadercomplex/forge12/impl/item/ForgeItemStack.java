package com.enderzombi102.loadercomplex.forge12.impl.item;

import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ForgeItemStack implements ItemStack {
	private final net.minecraft.item.ItemStack stack;

	public ForgeItemStack( net.minecraft.item.ItemStack stack ) {
		this.stack = stack;
	}

	public @NotNull Object getStack() {
		return this.stack;
	}

	@Override
	public String getDisplayName() {
		return this.stack.getDisplayName();
	}

	@Override
	public void setDisplayName( String newName ) {
		this.stack.setStackDisplayName( newName );
	}

	@Override
	public @NotNull Object getType() {
		return this.stack.getItem();
	}

	@Override
	public int getAmount() {
		return this.stack.getCount();
	}

	@Override
	public void setAmount( int amount ) {
		this.stack.setCount( amount );
	}

	@Override
	public void setDamage( int damage ) {
		this.stack.setItemDamage( damage );
	}

	@Override
	public int getDamage() {
		return this.stack.getItemDamage();
	}

	@Override
	public int getMaxDurability() {
		return this.stack.getMaxDamage();
	}

	@Override
	public int getMaxStackSize() {
		return this.stack.getMaxStackSize();
	}
}
