package com.enderzombi102.loadercomplex.forge122.impl.item

import net.minecraft.item.ItemStack

class ForgeItemStack(private val stack: ItemStack) : com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack {
	override fun getStack(): Any {
		return this.stack
	}

	override fun getDisplayName(): String? {
		return stack.displayName
	}

	override fun setDisplayName(newName: String) {
		stack.setStackDisplayName(newName)
	}

	override fun getType(): Any {
		return stack.item
	}

	override fun getAmount(): Int {
		return stack.count
	}

	override fun setAmount(amount: Int) {
		stack.count = amount
	}

	override fun setDamage(damage: Int) {
		stack.itemDamage = damage
	}

	override fun getDamage(): Int {
		return stack.itemDamage
	}

	override fun getMaxDurability(): Int {
		return stack.maxDamage
	}

	override fun getMaxStackSize(): Int {
		return stack.maxStackSize
	}
}
