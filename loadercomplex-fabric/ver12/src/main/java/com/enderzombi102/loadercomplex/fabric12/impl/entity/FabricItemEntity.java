package com.enderzombi102.loadercomplex.fabric12.impl.entity;

import com.enderzombi102.loadercomplex.fabric12.impl.item.FabricItemStack;
import com.enderzombi102.loadercomplex.minecraft.entity.ItemEntity;
import com.enderzombi102.loadercomplex.minecraft.item.ItemStack;

public class FabricItemEntity extends FabricEntity implements ItemEntity {
	private final net.minecraft.entity.ItemEntity backingEntity;

	public FabricItemEntity(net.minecraft.entity.ItemEntity backingEntity) {
		super( backingEntity );
		this.backingEntity = backingEntity;
	}

	@Override
	public ItemStack getStack() {
		return new FabricItemStack( this.backingEntity.getItemStack() );
	}

	@Override
	public void setStack(ItemStack stack) {
		this.backingEntity.setItemStack( (net.minecraft.item.ItemStack) stack.getStack() );
	}
}
