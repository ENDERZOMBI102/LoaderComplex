package com.enderzombi102.loadercomplex.api.entity;

import com.enderzombi102.loadercomplex.api.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.AvailableSince("0.1.3")
public interface ItemEntity extends Entity {
	ItemStack getStack();
	void setStack(ItemStack stack);
}
