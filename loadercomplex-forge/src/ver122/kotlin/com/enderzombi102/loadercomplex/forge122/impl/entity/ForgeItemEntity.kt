package com.enderzombi102.loadercomplex.forge122.impl.entity

import com.enderzombi102.loadercomplex.api.minecraft.entity.ItemEntity
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack
import com.enderzombi102.loadercomplex.forge122.impl.item.ForgeItemStack
import net.minecraft.entity.item.EntityItem

class ForgeItemEntity(private val wrappedEntity: EntityItem) : ForgeEntity(
	wrappedEntity
),
	ItemEntity {
	override fun asItem(): ItemEntity {
		return this
	}

	override fun getStack(): ItemStack {
		return ForgeItemStack(wrappedEntity.item)
	}

	override fun setStack(stack: ItemStack) {
		wrappedEntity.item = stack.stack as net.minecraft.item.ItemStack
	}
}
