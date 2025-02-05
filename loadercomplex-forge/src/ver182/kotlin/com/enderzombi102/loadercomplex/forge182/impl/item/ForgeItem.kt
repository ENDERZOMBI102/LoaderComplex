package com.enderzombi102.loadercomplex.forge182.impl.item

import com.enderzombi102.loadercomplex.api.math.Direction
import com.enderzombi102.loadercomplex.api.minecraft.item.Item
import com.enderzombi102.loadercomplex.forge182.impl.block.ForgeBlockState
import com.enderzombi102.loadercomplex.forge182.impl.entity.ForgeEntity
import com.enderzombi102.loadercomplex.forge182.impl.entity.ForgeLivingEntity
import com.enderzombi102.loadercomplex.forge182.impl.entity.ForgePlayer
import com.enderzombi102.loadercomplex.forge182.impl.utils.toLC
import com.enderzombi102.loadercomplex.forge182.impl.utils.toMC
import com.enderzombi102.loadercomplex.forge182.impl.world.ForgeWorld
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.UseAction
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.world.World
import net.minecraftforge.registries.RegistryManager


class ForgeItem(val itemImpl: Item) : net.minecraft.item.Item( Settings().maxCount(itemImpl.maxCount).maxDamage(itemImpl.maxDamage) ) {
	init {
		itemImpl.implementationItem = this
	}

	// logic methods override
	override fun postProcessNbt(nbt: NbtCompound) {
		itemImpl.validateTag(nbt)
	}

	override fun useOnBlock(ctx: ItemUsageContext): ActionResult {
		return ActionResult.valueOf(
			itemImpl.useOnBlock(
				ForgeWorld(ctx.world),
				ForgePlayer(ctx.player!!),
				ctx.blockPos.toLC(),
				com.enderzombi102.loadercomplex.api.minecraft.util.Hand.valueOf(ctx.hand.name),
				Direction.valueOf(ctx.playerFacing.name)
			).name
		)
	}

	override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
		val stack = user.getStackInHand(hand)
		val res = itemImpl.startUsing( ForgeWorld(world), ForgePlayer(user), ForgeItemStack(stack) )
		return TypedActionResult( ActionResult.valueOf( res.result.name ), res.`object`.toMC() )
	}

	override fun finishUsing(stack: ItemStack, world: World, user: LivingEntity): ItemStack {
		return (itemImpl.finishUsing( ForgeItemStack(stack), ForgeWorld(world), ForgeLivingEntity(user) ) as ForgeItemStack).stack as ItemStack
	}

	override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity): Boolean {
		return itemImpl.postAttack( ForgeItemStack(stack), ForgeLivingEntity(target), ForgeLivingEntity(attacker) )
	}

	override fun postMine( stack: ItemStack, world: World, state: BlockState, pos: BlockPos, miner: LivingEntity ): Boolean {
		return itemImpl.postMine(
			ForgeItemStack(stack),
			ForgeWorld(world),
			ForgeBlockState(state),
			pos.toLC(),
			ForgeLivingEntity(miner)
		)
	}

	override fun isSuitableFor(state: BlockState): Boolean {
		return itemImpl.canMineBlock(ForgeBlockState(state))
	}

	override fun useOnEntity(stack: ItemStack, user: PlayerEntity, entity: LivingEntity, hand: Hand): ActionResult {
		return (itemImpl.useOnEntity(
				ForgeItemStack(stack),
				ForgePlayer(user),
				ForgeLivingEntity(entity),
				com.enderzombi102.loadercomplex.api.minecraft.util.Hand.valueOf(hand.name)
			)
		).toMC()
	}

	override fun inventoryTick(stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean) {
		itemImpl.inventoryTick(
			ForgeItemStack(stack),
			ForgeEntity(entity),
			slot,
			selected
		)
	}

	override fun onCraft(stack: ItemStack, world: World, player: PlayerEntity) {
		itemImpl.onCraft(
			ForgeItemStack(stack),
			ForgePlayer(player)
		)
	}

	override fun onStoppedUsing(stack: ItemStack, world: World, user: LivingEntity, remainingUseTicks: Int) {
		itemImpl.onStoppedUsing(
			ForgeItemStack(stack),
			ForgeWorld(world),
			ForgeLivingEntity(user),
			remainingUseTicks
		)
	}

	override fun isIn(group: ItemGroup): Boolean {
		val itemGroup = this.getGroup()
		return itemGroup != null && (group === ItemGroup.SEARCH || group === itemGroup)
	}

	// getter methods override
	// getMiningSpeedMultiplier
	override fun getMiningSpeedMultiplier(stack: ItemStack, state: BlockState): Float {
		return itemImpl.miningSpeedMultiplier
	}

	override fun isDamageable(): Boolean {
		return itemImpl.maxDamage > 0 && (!itemImpl.hasVariants || itemImpl.maxCount == 1)
	}

	override fun getUseAction(stack: ItemStack): UseAction {
		return UseAction.valueOf(itemImpl.useAction.name)
	}

	override fun getMaxUseTime(stack: ItemStack): Int {
		return itemImpl.maxUseTime
	}

	override fun getEnchantability(): Int {
		return itemImpl.enchantability
	}

	override fun canRepair(stack: ItemStack, ingredient: ItemStack): Boolean {
		val material = itemImpl.repairMaterial?.toString()
		if (material != null) {
			return RegistryManager.ACTIVE.getRegistry(Registry.ITEM_KEY)
				.getKey(ingredient.item)
				.toString() == material
		}
		return false
	}
}
