package com.enderzombi102.loadercomplex.fabric122.impl.entity

import com.enderzombi102.loadercomplex.api.minecraft.entity.ItemEntity
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack
import com.enderzombi102.loadercomplex.fabric122.impl.item.FabricItemStack
import com.enderzombi102.loadercomplex.fabric122.impl.utils.toMC
import net.minecraft.entity.ItemEntity as McItemEntity

class FabricItemEntity( private val backingEntity: McItemEntity ) : FabricEntity( backingEntity ), ItemEntity {
	override fun getStack(): ItemStack {
		return FabricItemStack(backingEntity.item)
	}

	override fun setStack( stack: ItemStack ) {
		backingEntity.item = stack.toMC()
	}
}
