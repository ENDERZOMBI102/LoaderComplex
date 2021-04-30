package com.enderzombi102.loaderComplex.fabric12.impl.item;


import com.enderzombi102.loaderComplex.fabric12.mixin.ItemAccessor;
import com.enderzombi102.loadercomplex.api.item.Item;
import com.enderzombi102.loadercomplex.api.utils.Hand;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FabricItem extends net.minecraft.item.Item {

	private final Item itemImpl;

	public FabricItem(Item item) {
		this.itemImpl = item;
		item.implementationItem = this;
		if (this.itemImpl.maxDamage > 0) {
			this.addModelPredicateProvider(
					new Identifier("damaged"),
					ItemAccessor.getDamagedProvider()
			);
			this.addModelPredicateProvider(
					new Identifier("damage"),
					ItemAccessor.getDamageProvider()
			);
		}
	}

	// logic methods override

	public boolean postProcessTag(CompoundTag tag) {
		return false;
	}

	public ActionResult useOnBlock(PlayerEntity player, World world, BlockPos pos, net.minecraft.util.Hand hand, Direction facing, float x, float y, float z) {
		return ActionResult.valueOf( this.itemImpl.useOnBlock().name() );
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, net.minecraft.util.Hand hand) {
		return new TypedActionResult<>( ActionResult.PASS, user.getStackInHand(hand) );
	}

	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		return ( (FabricItemStack) this.itemImpl.finishUsing( new FabricItemStack(stack), user ) ).getStack();
	}

	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		return this.itemImpl.postHit( new FabricItemStack( stack ), target, attacker);
	}

	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		return this.itemImpl.postMine( new FabricItemStack(stack), miner );
	}

	public boolean isEffectiveOn(BlockState state) {
		return this.itemImpl.isEffectiveOn(state);
	}

	public boolean useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, net.minecraft.util.Hand hand) {
		return this.itemImpl.useOnEntity( new FabricItemStack(stack), user, entity, Hand.valueOf( hand.name() ) );
	}

	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		this.itemImpl.inventoryTick( new FabricItemStack(stack), entity, slot, selected );
	}

	public void onCraft(ItemStack stack, World world, PlayerEntity player) {
		this.itemImpl.onCraft( new FabricItemStack(stack), player );
	}

	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		this.itemImpl.onStoppedUsing( new FabricItemStack(stack), user, remainingUseTicks);
	}

	protected boolean isIn(ItemGroup group) {
		ItemGroup itemGroup = this.getGroup();
		return itemGroup != null && (group == ItemGroup.SEARCH || group == itemGroup);
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
	public ItemGroup getGroup() {
		if ( this.itemImpl == null || this.itemImpl.group == null ) return null;
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
			default:
				return ItemGroup.MISC;
		}
	}

	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		String material = this.itemImpl.repairMaterial.toString();
		if ( material != null ) {
			return net.minecraft.item.Item.REGISTRY
					.getId( ingredient.getItem() )
					.toString()
					.equals( material );
		}
		return false;
	}
}
