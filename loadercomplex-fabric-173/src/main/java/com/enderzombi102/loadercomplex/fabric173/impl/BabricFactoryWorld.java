package com.enderzombi102.loadercomplex.fabric173.impl;

import com.enderzombi102.loadercomplex.api.block.Blockstate;
import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.entity.ItemEntity;
import com.enderzombi102.loadercomplex.api.item.ItemStack;
import com.enderzombi102.loadercomplex.api.utils.FactoryWorld;
import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import com.enderzombi102.loadercomplex.api.utils.Server;
import com.enderzombi102.loadercomplex.api.world.World;

public class BabricFactoryWorld implements FactoryWorld {
	@Override
	public ItemStack createStack(ResourceIdentifier type) {
		return null;
	}

	@Override
	public Entity createEntity(World world, ResourceIdentifier type) {
		return null;
	}

	@Override
	public ItemEntity createItemEntity(World world, ItemStack stack) {
		return null;
	}

	@Override
	public Blockstate createBlockstate(ResourceIdentifier type) {
		return null;
	}

	@Override
	public Blockstate airBlockstate() {
		return null;
	}

	@Override
	public World adaptWorld(Server server, int id) {
		return null;
	}
}
