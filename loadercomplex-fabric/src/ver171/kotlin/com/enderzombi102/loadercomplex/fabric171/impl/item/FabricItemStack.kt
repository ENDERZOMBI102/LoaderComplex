package com.enderzombi102.loadercomplex.fabric171.impl.item

import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.item.ItemStack as McItemStack


class FabricItemStack( private val stack: McItemStack ) : ItemStack {
	override fun getStack(): McItemStack =
		this.stack

	override fun getDisplayName(): String =
		Text.Serializer.toJson( stack.name )

	override fun setDisplayName( newName: String ) {
		stack.setCustomName( LiteralText( newName ) )
	}

	override fun getType(): Any =
		stack.item

	override fun getAmount(): Int =
		stack.count

	override fun setAmount( amount: Int ) {
		stack.count = amount
	}

	override fun setDamage( damage: Int ) {
		stack.damage = damage
	}

	override fun getDamage(): Int =
		stack.damage

	override fun getMaxDurability(): Int =
		stack.maxDamage

	override fun getMaxStackSize(): Int =
		stack.maxCount
}
