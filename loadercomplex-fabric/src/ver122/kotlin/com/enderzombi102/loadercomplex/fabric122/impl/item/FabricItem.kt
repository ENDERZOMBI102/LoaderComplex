package com.enderzombi102.loadercomplex.fabric122.impl.item

import com.enderzombi102.loadercomplex.api.minecraft.item.Item
import com.enderzombi102.loadercomplex.api.minecraft.util.ActionResult
import com.enderzombi102.loadercomplex.fabric122.impl.block.FabricBlockState
import com.enderzombi102.loadercomplex.fabric122.impl.entity.FabricEntity
import com.enderzombi102.loadercomplex.fabric122.impl.entity.FabricLivingEntity
import com.enderzombi102.loadercomplex.fabric122.impl.entity.FabricPlayerEntity
import com.enderzombi102.loadercomplex.fabric122.impl.utils.toLC
import com.enderzombi102.loadercomplex.fabric122.impl.utils.toMC
import com.enderzombi102.loadercomplex.fabric122.impl.world.FabricWorld
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.state.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.living.LivingEntity
import net.minecraft.entity.living.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.UseAction
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.World
import net.minecraft.item.Item as McItem


class FabricItem( val itemImpl: Item ) : McItem() {
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


	// logic methods override
	override fun validateNbt( nbt: NbtCompound ): Boolean =
		itemImpl.validateTag( nbt )

	override fun use( player: PlayerEntity, world: World, pos: BlockPos, hand: InteractionHand, face: Direction, faceX: Float, faceY: Float, faceZ: Float ): InteractionResult =
		itemImpl.useOnBlock(FabricWorld(world), FabricPlayerEntity( player ), pos.toLC(), hand.toLC(), face.toLC() ).toMC()

	override fun startUsing( world: World, player: PlayerEntity, hand: InteractionHand ): InteractionResultHolder<ItemStack> {
		val holder = itemImpl.startUsing(
			FabricWorld(
				world
			), FabricPlayerEntity( player ), FabricItemStack( player.getItemInHand( hand ) ) )
		return InteractionResultHolder( holder.result.toMC(), holder.`object`.toMC() )
	}

	override fun finishUsing( stack: ItemStack, world: World, entity: LivingEntity ): ItemStack =
		itemImpl.finishUsing( FabricItemStack( stack ),
			FabricWorld(world), FabricLivingEntity( entity ) ).toMC()

	override fun attack( stack: ItemStack, target: LivingEntity, attacker: LivingEntity ): Boolean =
		itemImpl.postAttack(
			FabricItemStack( stack ),
			FabricLivingEntity( target ),
			FabricLivingEntity( attacker )
		)

	override fun mineBlock( stack: ItemStack, world: World, state: BlockState, pos: BlockPos, entity: LivingEntity ): Boolean =
		itemImpl.postMine(
			FabricItemStack( stack ),
			FabricWorld(world),
			FabricBlockState( state ),
			pos.toLC(),
			FabricLivingEntity( entity )
		)

	override fun canMineBlock( state: BlockState ): Boolean =
		itemImpl.canMineBlock( FabricBlockState( state ) )

	override fun interact( stack: ItemStack, player: PlayerEntity, entity: LivingEntity, hand: InteractionHand ): Boolean =
		itemImpl.useOnEntity(
			FabricItemStack( stack ),
			FabricPlayerEntity( player ),
			FabricLivingEntity( entity ),
			hand.toLC()
		) == ActionResult.SUCCESS

	override fun tick( stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean ) {
		itemImpl.inventoryTick(
			FabricItemStack( stack ),
			FabricEntity( entity ),
			slot,
			selected
		)
	}

	override fun onResult( stack: ItemStack, world: World, player: PlayerEntity ) {
		itemImpl.onCraft(
			FabricItemStack( stack ),
			FabricPlayerEntity( player )
		)
	}

	override fun stopUsing( stack: ItemStack, world: World, entity: LivingEntity, remainingUseTime: Int ) {
		itemImpl.onStoppedUsing(
			FabricItemStack( stack ),
			FabricWorld(world),
			FabricLivingEntity( entity ),
			remainingUseTime
		)
	}


	// getter methods override
	override fun getMiningSpeed( stack: ItemStack, state: BlockState ): Float =
		itemImpl.miningSpeedMultiplier

	override fun getMaxStackSize(): Int =
		itemImpl.maxCount

	override fun getMaxDamage(): Int =
		itemImpl.maxDamage

	override fun isDamageable(): Boolean =
		itemImpl.maxDamage > 0 && (!itemImpl.hasVariants || itemImpl.maxCount == 1)

	@Environment( EnvType.CLIENT )
	override fun isHandheld(): Boolean =
		itemImpl.is3D

	@Environment( EnvType.CLIENT )
	override fun shouldRotate(): Boolean =
		itemImpl.requiresRenderRotation

	override fun getUseAction( stack: ItemStack ): UseAction =
		itemImpl.useAction.toMC()

	override fun getEnchantability(): Int =
		itemImpl.enchantability

	override fun isRepairable( stack: ItemStack, ingredient: ItemStack ): Boolean {
		val material = itemImpl.repairMaterial?.toString()

		return material != null && REGISTRY.getKey( ingredient.item ).toString() == material
	}
}
