package com.enderzombi102.loadercomplex.quilt.impl.item;

import com.enderzombi102.loadercomplex.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import org.jetbrains.annotations.NotNull;

public class QuiltItemStack implements ItemStack {

	private final net.minecraft.item.ItemStack stack;

	public QuiltItemStack(net.minecraft.item.ItemStack stack) {
		this.stack = stack;
	}

	public @NotNull Object getStack() {
		return stack;
	}

	@Override
	public String getDisplayName() {
		return this.stack.getName().getString();
	}

	@Override
	public void setDisplayName(String newName) {
		this.stack.setCustomName( new LiteralText( newName ) );
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
	public void setAmount(int amount) {
		this.stack.setCount(amount);
	}

	@Override
	public void setDamage(int damage) {
		this.stack.setDamage(damage);
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
		return this.stack.getMaxCount();
	}
}
