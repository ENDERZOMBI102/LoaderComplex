package com.enderzombi102.loadercomplex.quilt.impl.entity;

import com.enderzombi102.loadercomplex.minecraft.entity.ItemEntity;
import com.enderzombi102.loadercomplex.minecraft.item.ItemStack;
import com.enderzombi102.loadercomplex.quilt.impl.item.QuiltItemStack;

public class QuiltItemEntity extends QuiltEntity implements ItemEntity {
	private final net.minecraft.entity.ItemEntity backingEntity;

	public QuiltItemEntity(net.minecraft.entity.ItemEntity backingEntity) {
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
