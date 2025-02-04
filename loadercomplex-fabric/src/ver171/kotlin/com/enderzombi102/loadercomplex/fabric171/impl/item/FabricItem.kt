package com.enderzombi102.loadercomplex.fabric171.impl.item

import com.enderzombi102.loadercomplex.api.minecraft.item.Item
import com.enderzombi102.loadercomplex.fabric171.impl.block.FabricBlockState
import com.enderzombi102.loadercomplex.fabric171.impl.entity.FabricEntity
import com.enderzombi102.loadercomplex.fabric171.impl.entity.FabricLivingEntity
import com.enderzombi102.loadercomplex.fabric171.impl.entity.FabricPlayerEntity
import com.enderzombi102.loadercomplex.fabric171.impl.utils.toLC
import com.enderzombi102.loadercomplex.fabric171.impl.utils.toMC
import com.enderzombi102.loadercomplex.fabric171.impl.world.FabricWorld
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.TypedActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.world.World
import net.minecraft.block.BlockState as McBlockState
import net.minecraft.entity.LivingEntity as McLivingEntity
import net.minecraft.entity.player.PlayerEntity as McPlayerEntity
import net.minecraft.item.Item as McItem
import net.minecraft.item.ItemStack as McItemStack
import net.minecraft.util.ActionResult as McActionResult
import net.minecraft.util.Hand as McHand
import net.minecraft.util.UseAction as McUseAction
import net.minecraft.world.World as McWorld


class FabricItem( private val itemImpl: Item ) : McItem( createSettings( itemImpl ) ) {
	init {
		itemImpl.implementationItem = this
//				if (this.itemImpl.maxDamage > 0) {
//			this.addModelPredicateProvider(
//					new Identifier("damaged"),
//					ItemAccessor.getDamagedProvider()
//			);
//			this.addModelPredicateProvider(
//					new Identifier("damage"),
//					ItemAccessor.getDamageProvider()
//			);
//		}
	}


	// region logic methods override
	override fun postProcessNbt( nbt: NbtCompound ) {
		itemImpl.validateTag( nbt )
	}

	override fun useOnBlock( ctx: ItemUsageContext ): McActionResult =
		itemImpl.useOnBlock(
			FabricWorld( ctx.world ),
			FabricPlayerEntity( ctx.player as McPlayerEntity ),
			ctx.blockPos.toLC(),
			ctx.hand.toLC(),
			ctx.side.toLC()
		).toMC()

	override fun use( world: McWorld, player: McPlayerEntity, hand: McHand ): TypedActionResult<McItemStack> {
		val holder = itemImpl.startUsing(
			FabricWorld( world ),
			FabricPlayerEntity( player ),
			FabricItemStack( player.getStackInHand( hand ) )
		)
		return TypedActionResult( holder.result.toMC(), holder.`object`.toMC() )
	}

	override fun finishUsing( stack: McItemStack, world: McWorld, entity: McLivingEntity ): McItemStack =
		itemImpl.finishUsing( FabricItemStack( stack ), FabricWorld( world ), FabricLivingEntity( entity ) ).toMC()

	override fun postHit( stack: McItemStack, target: McLivingEntity, attacker: McLivingEntity ): Boolean =
		itemImpl.postAttack( FabricItemStack( stack ), FabricLivingEntity( target ), FabricLivingEntity( attacker ) )

	override fun postMine( stack: McItemStack, world: McWorld, state: McBlockState, pos: BlockPos, entity: McLivingEntity): Boolean =
		itemImpl.postMine(
			FabricItemStack( stack ),
			FabricWorld(world),
			FabricBlockState( state ),
			pos.toLC(),
			FabricLivingEntity( entity )
		)

	override fun isSuitableFor( state: McBlockState ): Boolean =
		itemImpl.canMineBlock( FabricBlockState( state ) )

	override fun useOnEntity( stack: McItemStack, player: McPlayerEntity, entity: McLivingEntity, hand: McHand ): ActionResult =
		itemImpl.useOnEntity(
			FabricItemStack( stack ),
			FabricPlayerEntity( player ),
			FabricLivingEntity( entity ),
			hand.toLC()
		).toMC()

	override fun inventoryTick( stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean ) {
		itemImpl.inventoryTick(
			FabricItemStack( stack ),
			FabricEntity( entity ),
			slot,
			selected
		)
	}

	override fun onCraft( stack: McItemStack, world: McWorld, player: McPlayerEntity ) {
		itemImpl.onCraft( FabricItemStack( stack ), FabricPlayerEntity( player ) )
	}

	override fun onStoppedUsing( stack: ItemStack, world: World, user: LivingEntity, remainingUseTicks: Int ) {
		itemImpl.onStoppedUsing(
			FabricItemStack( stack ),
			FabricWorld( world ),
			FabricLivingEntity( user ),
			remainingUseTicks
		)
	}
	// endregion

	// region getter methods override
	override fun getMiningSpeedMultiplier(stack: ItemStack?, state: BlockState?): Float =
		itemImpl.miningSpeedMultiplier

	override fun isDamageable(): Boolean =
		itemImpl.maxDamage > 0 && (!itemImpl.hasVariants || itemImpl.maxCount == 1)

	override fun getUseAction( stack: McItemStack): McUseAction =
		itemImpl.useAction.toMC()

	override fun getEnchantability(): Int =
		itemImpl.enchantability

	override fun canRepair( stack: McItemStack, ingredient: McItemStack ): Boolean {
		val material = itemImpl.repairMaterial?.toString()

		return material != null && Registry.ITEM.getKey( ingredient.item ).toString() == material
	}
	// endregion

	companion object {
		fun createSettings( item: Item ): FabricItemSettings {
			val settings = FabricItemSettings()
				.maxCount( item.maxCount )
				.maxDamage( item.maxDamage )

			return settings
		}
	}
}
