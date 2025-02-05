package com.enderzombi102.loadercomplex.forge182.impl.entity

import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack
import com.enderzombi102.loadercomplex.forge182.impl.item.ForgeItemStack
import net.minecraft.entity.ItemEntity

class ForgeItemEntity(private val backingEntity: ItemEntity?) : ForgeEntity(
	backingEntity!!
),
	com.enderzombi102.loadercomplex.api.minecraft.entity.ItemEntity {
	override fun getStack(): ItemStack {
		return ForgeItemStack(backingEntity!!.stack)
	}

	override fun setStack(stack: ItemStack) {
		backingEntity!!.stack = stack.stack as net.minecraft.item.ItemStack
	}
}
