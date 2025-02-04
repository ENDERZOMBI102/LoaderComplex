package com.enderzombi102.loadercomplex.forge122.impl.item

import com.enderzombi102.loadercomplex.api.math.Direction
import com.enderzombi102.loadercomplex.api.minecraft.item.Item
import com.enderzombi102.loadercomplex.api.minecraft.util.ActionResult
import com.enderzombi102.loadercomplex.api.minecraft.util.Hand
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier.ri
import com.enderzombi102.loadercomplex.forge122.impl.block.ForgeBlockState
import com.enderzombi102.loadercomplex.forge122.impl.entity.ForgeEntity
import com.enderzombi102.loadercomplex.forge122.impl.entity.ForgeLivingEntity
import com.enderzombi102.loadercomplex.forge122.impl.entity.ForgePlayer
import com.enderzombi102.loadercomplex.forge122.impl.platform.ForgeRegistry
import com.enderzombi102.loadercomplex.forge122.impl.utils.toLC
import com.enderzombi102.loadercomplex.forge122.impl.world.ForgeWorld
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumAction
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly


class ForgeItem(private val itemImpl: Item) : net.minecraft.item.Item() {
	init {
		itemImpl.implementationItem = this
		this.setMaxDamage(itemImpl.maxDamage)
	}


	// logic methods override
	override fun updateItemStackNBT(tag: NBTTagCompound): Boolean {
		return false
	}

	override fun onItemUse( player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, x: Float, y: Float, z: Float ): EnumActionResult {
		return EnumActionResult.valueOf(
			itemImpl.useOnBlock(
				ForgeWorld(world),
				ForgePlayer(player),
				pos.toLC(),
				Hand.valueOf(hand.name),
				Direction.valueOf(facing.name)
			).name
		)
	}

	override fun onItemRightClick( world: World, user: EntityPlayer, hand: EnumHand ): net.minecraft.util.ActionResult<ItemStack> {
		val stack = user.getHeldItem(hand)
		val x = itemImpl.startUsing(ForgeWorld(world), ForgePlayer(user), ForgeItemStack(stack))
		return net.minecraft.util.ActionResult(
			EnumActionResult.valueOf(x.result.name),
			x.getObject().stack as ItemStack
		)
	}

	override fun onItemUseFinish(stack: ItemStack, world: World, user: EntityLivingBase): ItemStack {
		return itemImpl.finishUsing(
			ForgeItemStack(stack),
			ForgeWorld(world),
			ForgeLivingEntity(user)
		).stack as ItemStack
	}

	override fun hitEntity(stack: ItemStack, target: EntityLivingBase, attacker: EntityLivingBase): Boolean {
		return itemImpl.postAttack(
			ForgeItemStack(stack),
			ForgeLivingEntity(target),
			ForgeLivingEntity(attacker)
		)
	}

	override fun onBlockDestroyed( stack: ItemStack, world: World, state: IBlockState, pos: BlockPos, miner: EntityLivingBase ): Boolean {
		return itemImpl.postMine(
			ForgeItemStack(stack),
			ForgeWorld(world),
			ForgeBlockState(state),
			pos.toLC(),
			ForgeLivingEntity(miner)
		)
	}

	override fun canHarvestBlock(state: IBlockState): Boolean {
		return itemImpl.canMineBlock(ForgeBlockState(state))
	}

	override fun itemInteractionForEntity( stack: ItemStack, user: EntityPlayer, entity: EntityLivingBase, hand: EnumHand ): Boolean {
		return itemImpl.useOnEntity(
			ForgeItemStack(stack),
			ForgePlayer(user),
			ForgeLivingEntity(entity),
			Hand.valueOf(hand.name)
		) == ActionResult.SUCCESS
	}

	override fun onUpdate(stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean) {
		itemImpl.inventoryTick(ForgeItemStack(stack), ForgeEntity(entity), slot, selected)
	}

	override fun onCreated(stack: ItemStack, world: World, player: EntityPlayer) {
		itemImpl.onCraft(ForgeItemStack(stack), ForgePlayer(player))
	}

	override fun onPlayerStoppedUsing(stack: ItemStack, world: World, user: EntityLivingBase, remainingUseTicks: Int) {
		itemImpl.onStoppedUsing(
			ForgeItemStack(stack),
			ForgeWorld(world),
			ForgeLivingEntity(user),
			remainingUseTicks
		)
		// TODO: Check if correct
		itemImpl.finishUsing(
			ForgeItemStack(stack),
			ForgeWorld(world),
			ForgeLivingEntity(user)
		)
	}

	override fun isInCreativeTab(group: CreativeTabs): Boolean {
		val itemGroup = this.creativeTab
		return itemGroup != null && (group === CreativeTabs.SEARCH || group === itemGroup)
	}


	// getter methods override
	override fun getDestroySpeed(stack: ItemStack, state: IBlockState): Float {
		return itemImpl.miningSpeedMultiplier
	}

	@Suppress("deprecation")
	override fun getItemStackLimit(): Int {
		return itemImpl.maxCount
	}

	override fun getMetadata(damage: Int): Int {
		throw RuntimeException("Not implemented")
	}

	override fun getHasSubtypes(): Boolean {
		return itemImpl.hasVariants
	}

	override fun isDamageable(): Boolean {
		return itemImpl.maxDamage > 0 && (!itemImpl.hasVariants || itemImpl.maxCount == 1)
	}

	@SideOnly(Side.CLIENT)
	override fun isFull3D(): Boolean {
		return itemImpl.is3D
	}

	@SideOnly(Side.CLIENT)
	override fun shouldRotateAroundWhenRendering(): Boolean {
		return itemImpl.requiresRenderRotation
	}

	override fun getItemUseAction(stack: ItemStack): EnumAction {
		return EnumAction.valueOf(itemImpl.useAction.name)
	}

	override fun getMaxItemUseDuration(stack: ItemStack): Int {
		return itemImpl.maxUseTime
	}

	override fun getItemEnchantability(): Int {
		return itemImpl.enchantability
	}

	override fun getCreativeTab(): CreativeTabs? {
		return ForgeRegistry.getOrCreateCreativeTab( itemImpl.creativeTab, ri( requireNotNull( this.registryName ).toString() ) )
	}

	override fun getIsRepairable(stack: ItemStack, ingredient: ItemStack): Boolean {
		return REGISTRY.getNameForObject( ingredient.item ).toString() == itemImpl.repairMaterial.toString()
	}
}
