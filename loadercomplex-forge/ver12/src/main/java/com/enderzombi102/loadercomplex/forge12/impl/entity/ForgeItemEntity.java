package com.enderzombi102.loadercomplex.forge12.impl.entity;

import com.enderzombi102.loadercomplex.api.minecraft.entity.ItemEntity;
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack;
import com.enderzombi102.loadercomplex.forge12.impl.item.ForgeItemStack;
import net.minecraft.entity.item.EntityItem;

public class ForgeItemEntity extends ForgeEntity implements ItemEntity {
	private final EntityItem wrappedEntity;

	public ForgeItemEntity(EntityItem entityItem) {
		super(entityItem);
		this.wrappedEntity = entityItem;
	}

	@Override
	public ItemEntity asItem() {
		return this;
	}

	@Override
	public ItemStack getStack() {
		return new ForgeItemStack( this.wrappedEntity.getItem() );
	}

	@Override
	public void setStack(ItemStack stack) {
		this.wrappedEntity.setItem( (net.minecraft.item.ItemStack) stack.getStack() );
	}
}
