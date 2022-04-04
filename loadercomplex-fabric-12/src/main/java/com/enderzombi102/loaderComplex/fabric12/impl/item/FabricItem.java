package com.enderzombi102.loaderComplex.fabric12.impl.item;


import com.enderzombi102.loaderComplex.fabric12.impl.block.FabricBlockstate;
import com.enderzombi102.loaderComplex.fabric12.impl.entity.FabricEntity;
import com.enderzombi102.loaderComplex.fabric12.impl.entity.FabricLivingEntity;
import com.enderzombi102.loaderComplex.fabric12.impl.entity.FabricPlayer;
import com.enderzombi102.loaderComplex.fabric12.impl.utils.BlockUtils;
import com.enderzombi102.loaderComplex.fabric12.impl.world.FabricWorld;
import com.enderzombi102.loadercomplex.api.item.Item;
import com.enderzombi102.loadercomplex.api.utils.Hand;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.itemgroup.ItemGroup;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FabricItem extends net.minecraft.item.Item {
	private final Item itemImpl;

	public FabricItem( Item item ) {
		this.itemImpl = item;
		item.implementationItem = this;
//		if (this.itemImpl.maxDamage > 0) {
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


	@Override
	public boolean postProcessNbt( NbtCompound nbt ) {
		return this.itemImpl.postProcesstag( nbt );
	}


	public ActionResult useOnBlock(PlayerEntity player, World world, BlockPos pos, net.minecraft.util.Hand hand, Direction facing, float x, float y, float z) {
		return ActionResult.valueOf(
			this.itemImpl.useOnBlock(
				new FabricWorld( world ),
				new FabricPlayer( player ),
				BlockUtils.toPosition( pos ),
				Hand.valueOf( hand.name() ),
				com.enderzombi102.loadercomplex.api.utils.Direction.valueOf( facing.name() )
			).name()
		);
	}

	@Override
	public ActionResult use(PlayerEntity player, World world, BlockPos pos, net.minecraft.util.Hand hand, Direction direction, float x, float y, float z) {
		// useOnBlock
		return ActionResult.valueOf(
			this.itemImpl.useOnBlock(
				new FabricWorld( world ),
				new FabricPlayer( player ),
				BlockUtils.toPosition( pos ),
				Hand.valueOf( hand.name() ),
				com.enderzombi102.loadercomplex.api.utils.Direction.valueOf( direction.name() )
			).name()
		);
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, net.minecraft.util.Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		return new TypedActionResult<>(
			ActionResult.valueOf(
				this.itemImpl.use(
					new FabricWorld( world ),
					new FabricPlayer( user ),
					new FabricItemStack( stack )
				).name()
			),
			stack
		);
	}

	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		return (
			(FabricItemStack) this.itemImpl.finishUsing(
				new FabricItemStack( stack ),
				new FabricWorld( world ),
				new FabricLivingEntity( user )
			)
		).getStack();
	}

	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		return this.itemImpl.postHit(
			new FabricItemStack( stack ),
			new FabricLivingEntity( target ),
			new FabricLivingEntity( attacker )
		);
	}

	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		return this.itemImpl.postMine(
			new FabricItemStack( stack ),
			new FabricWorld( world ),
			new FabricBlockstate( state ),
			BlockUtils.toPosition( pos ),
			new FabricLivingEntity( miner )
		);
	}

	public boolean isEffectiveOn(BlockState state) {
		return this.itemImpl.isEffectiveOn( new FabricBlockstate( state ) );
	}

	public boolean useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, net.minecraft.util.Hand hand) {
		return this.itemImpl.useOnEntity(
			new FabricItemStack(stack),
			new FabricPlayer( user ),
			new FabricLivingEntity( entity ),
			Hand.valueOf( hand.name() )
		);
	}

	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		this.itemImpl.inventoryTick(
			new FabricItemStack(stack),
			new FabricEntity( entity ),
			slot,
			selected
		);
	}

	public void onCraft(ItemStack stack, World world, PlayerEntity player) {
		this.itemImpl.onCraft(
			new FabricItemStack(stack),
			new FabricPlayer( player )
		);
	}

	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		this.itemImpl.onStoppedUsing(
			new FabricItemStack(stack),
			new FabricWorld( world ),
			new FabricLivingEntity( user ),
			remainingUseTicks
		);
	}

	protected boolean isIn(ItemGroup group) {
		ItemGroup itemGroup = this.getGroup();
		return itemGroup != null && ( group == ItemGroup.SEARCH || group == itemGroup );
	}

	// getter methods override

	public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
		return this.itemImpl.miningSpeedMultiplier;
	}

	public int getMaxCount() {
		return this.itemImpl.maxCount;
	}

	public int getMetadata(int damage) {
		return 0;
	}

	public boolean hasVariants() {
		return this.itemImpl.hasVariants;
	}

	public int getMaxDamage() {
		return this.itemImpl.maxDamage;
	}

	public boolean isDamageable() {
		return this.itemImpl.maxDamage > 0 && (!this.itemImpl.hasVariants || this.itemImpl.maxCount == 1);
	}

	@Environment(EnvType.CLIENT)
	public boolean is3D() {
		return this.itemImpl.is3D;
	}

	@Environment(EnvType.CLIENT)
	public boolean requiresRenderRotation() {
		return this.itemImpl.requiresRenderRotation;
	}

	public UseAction getUseAction(ItemStack stack) {
		return UseAction.valueOf( this.itemImpl.useAction.name() );
	}

	public int getMaxUseTime(ItemStack stack) {
		return this.itemImpl.maxUseTime;
	}

	public int getEnchantability() {
		return this.itemImpl.enchantability;
	}

	@Nullable
	@Override
	public ItemGroup getItemGroup() {
		return this.getGroup();
	}

	@Nullable
	public ItemGroup getGroup() {
		if ( this.itemImpl.group == null )
			return null;
		switch ( this.itemImpl.group ) {
			case "minecraft:itemgroup.brewing":
				return ItemGroup.BREWING;
			case "minecraft:itemgroup.building_blocks":
				return ItemGroup.BUILDING_BLOCKS;
			case "minecraft:itemgroup.combat":
				return ItemGroup.COMBAT;
			case "minecraft:itemgroup.decorations":
				return ItemGroup.DECORATIONS;
			case "minecraft:itemgroup.food":
				return ItemGroup.FOOD;
			case "minecraft:itemgroup.materials":
				return ItemGroup.MATERIALS;
			case "minecraft:itemgroup.redstone":
				return ItemGroup.REDSTONE;
			case "minecraft:itemgroup.tools":
				return ItemGroup.TOOLS;
			case "minecraft:itemgroup.transportation":
				return ItemGroup.TRANSPORTATION;
			case "minecraft:itemgroup.misc":
				return ItemGroup.MISC;
			default:
				return ItemGroup.MISC;
		}
	}

	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		String material = this.itemImpl.repairMaterial.toString();
		if ( material != null ) {
			//noinspection ConstantConditions
			return net.minecraft.item.Item.REGISTRY
					.getIdentifier( ingredient.getItem() )
					.toString()
					.equals( material );
		}
		return false;
	}
}
