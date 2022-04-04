package com.enderzombi102.loadercomplex.api.utils;

import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.entity.ItemEntity;
import com.enderzombi102.loadercomplex.api.item.ItemStack;
import com.enderzombi102.loadercomplex.api.world.World;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.AvailableSince("0.1.3")
public interface FactoryWorld {
	ItemStack createStack(ResourceIdentifier type);
	Entity createEntity(ResourceIdentifier type);
	ItemEntity createItemEntity(ItemStack stack);
	World adaptWorld(int id);
}
