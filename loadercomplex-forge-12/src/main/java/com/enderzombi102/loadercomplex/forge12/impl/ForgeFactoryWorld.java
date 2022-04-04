package com.enderzombi102.loadercomplex.forge12.impl;

import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.entity.ItemEntity;
import com.enderzombi102.loadercomplex.api.item.ItemStack;
import com.enderzombi102.loadercomplex.api.utils.FactoryWorld;
import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import com.enderzombi102.loadercomplex.api.world.World;

public class ForgeFactoryWorld implements FactoryWorld {
	@Override
	public ItemStack createStack(ResourceIdentifier type) {
		return null;
	}

	@Override
	public Entity createEntity(ResourceIdentifier type) {
		return null;
	}

	@Override
	public ItemEntity createItemEntity(ItemStack stack) {
		return null;
	}

	@Override
	public World adaptWorld(int id) {
		return null;
	}
}
