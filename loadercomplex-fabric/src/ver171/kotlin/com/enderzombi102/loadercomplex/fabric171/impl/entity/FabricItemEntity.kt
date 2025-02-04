package com.enderzombi102.loadercomplex.fabric171.impl.entity

import com.enderzombi102.loadercomplex.api.minecraft.entity.ItemEntity
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack
import com.enderzombi102.loadercomplex.fabric171.impl.item.FabricItemStack
import com.enderzombi102.loadercomplex.fabric171.impl.utils.toMC
import net.minecraft.entity.ItemEntity as McItemEntity

class FabricItemEntity( private val backingEntity: McItemEntity ) : FabricEntity( backingEntity ), ItemEntity {
	override fun getStack(): ItemStack {
		return FabricItemStack( backingEntity.stack )
	}

	override fun setStack( stack: ItemStack ) {
		backingEntity.stack = stack.toMC()
	}
}
