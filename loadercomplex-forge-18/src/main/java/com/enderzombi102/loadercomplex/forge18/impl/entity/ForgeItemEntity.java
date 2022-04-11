package com.enderzombi102.loadercomplex.forge18.impl.entity;

import com.enderzombi102.loadercomplex.api.entity.ItemEntity;
import com.enderzombi102.loadercomplex.api.item.ItemStack;
import com.enderzombi102.loadercomplex.quilt.impl.item.QuiltItemStack;

public class ForgeItemEntity extends ForgeEntity implements ItemEntity {
	private final net.minecraft.entity.ItemEntity backingEntity;

	public ForgeItemEntity(net.minecraft.entity.ItemEntity backingEntity) {
		super( backingEntity );
		this.backingEntity = backingEntity;
	}

	@Override
	public ItemStack getStack() {
		return new QuiltItemStack( this.backingEntity.getStack() );
	}

	@Override
	public void setStack(ItemStack stack) {
		this.backingEntity.setStack( (net.minecraft.item.ItemStack) stack.getStack() );
	}
}
