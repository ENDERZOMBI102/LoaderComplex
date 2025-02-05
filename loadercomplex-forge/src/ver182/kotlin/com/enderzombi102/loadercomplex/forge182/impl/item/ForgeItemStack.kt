package com.enderzombi102.loadercomplex.forge182.impl.item

import net.minecraft.item.ItemStack
import net.minecraft.text.LiteralText

class ForgeItemStack(private val stack: ItemStack) : com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack {
	override fun getStack(): Any {
		return stack
	}

	override fun getDisplayName(): String? {
		return stack.name.string
	}

	override fun setDisplayName(newName: String) {
		stack.setCustomName(LiteralText(newName))
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
		stack.damage = damage
	}

	override fun getDamage(): Int {
		return stack.damage
	}

	override fun getMaxDurability(): Int {
		return stack.maxDamage
	}

	override fun getMaxStackSize(): Int {
		return stack.maxCount
	}
}
