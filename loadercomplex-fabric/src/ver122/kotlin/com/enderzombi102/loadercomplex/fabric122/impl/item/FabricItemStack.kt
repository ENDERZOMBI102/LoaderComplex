package com.enderzombi102.loadercomplex.fabric122.impl.item

import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack
import net.minecraft.item.ItemStack as McItemStack


class FabricItemStack( private val stack: McItemStack ) : ItemStack {
	override fun getStack(): McItemStack =
		this.stack

	override fun getDisplayName(): String? =
		stack.hoverName

	override fun setDisplayName( newName: String ) {
		stack.setHoverName( newName )
	}

	override fun getType(): Any =
		stack.item

	override fun getAmount(): Int =
		stack.size

	override fun setAmount( amount: Int ) {
		stack.size = amount
	}

	override fun setDamage( damage: Int ) {
		stack.damage = damage
	}

	override fun getDamage(): Int =
		stack.damage

	override fun getMaxDurability(): Int =
		stack.maxDamage

	override fun getMaxStackSize(): Int =
		stack.maxSize
}
