package com.enderzombi102.loadercomplex.forge12.impl.item;


import com.enderzombi102.loadercomplex.abstraction.item.Item;
import com.enderzombi102.loadercomplex.abstraction.utils.Hand;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class ForgeItem extends net.minecraft.item.Item {

	private final Item itemImpl;

	public ForgeItem(Item item) {
		this.itemImpl = item;
		item.implementationItem = this;
		this.setMaxDamage(this.itemImpl.maxDamage);
	}

	// logic methods override


	@Override
	@ParametersAreNonnullByDefault
	public boolean updateItemStackNBT(NBTTagCompound tag) {
		return false;
	}

	@Override
	@ParametersAreNonnullByDefault
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, net.minecraft.util.EnumHand hand, EnumFacing facing, float x, float y, float z) {
		return EnumActionResult.valueOf( this.itemImpl.useOnBlock().name() );
	}

	@Override
	@ParametersAreNonnullByDefault
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer user, net.minecraft.util.EnumHand hand) {
		return new ActionResult<>( EnumActionResult.PASS, user.getHeldItem(hand) );
	}

	@Override
	@ParametersAreNonnullByDefault
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase user) {
		return ( (ForgeItemStack) this.itemImpl.finishUsing( new ForgeItemStack(stack), user ) ).getStack();
	}

	@Override
	@ParametersAreNonnullByDefault
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		return this.itemImpl.postHit( new ForgeItemStack( stack ), target, attacker);
	}

	@Override
	@ParametersAreNonnullByDefault
	public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase miner) {
		return this.itemImpl.postMine( new ForgeItemStack(stack), miner );
	}

	@Override
	@ParametersAreNonnullByDefault
	public boolean canHarvestBlock(IBlockState state) {
		return this.itemImpl.isEffectiveOn(state);
	}

	@Override
	@ParametersAreNonnullByDefault
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer user, EntityLivingBase entity, EnumHand hand) {
		return this.itemImpl.useOnEntity( new ForgeItemStack(stack), user, entity, Hand.valueOf( hand.name() ) );
	}

	@Override
	@ParametersAreNonnullByDefault
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		this.itemImpl.inventoryTick( new ForgeItemStack(stack), entity, slot, selected );
	}

	@Override
	@ParametersAreNonnullByDefault
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		this.itemImpl.onCraft( new ForgeItemStack(stack), player );
	}

	@Override
	@ParametersAreNonnullByDefault
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase user, int remainingUseTicks) {
		this.itemImpl.onStoppedUsing( new ForgeItemStack(stack), user, remainingUseTicks);
	}

	@Override
	@ParametersAreNonnullByDefault
	protected boolean isInCreativeTab(CreativeTabs group) {
		CreativeTabs itemGroup = this.getCreativeTab();
		return itemGroup != null && (group == CreativeTabs.SEARCH || group == itemGroup);
	}

	// getter methods override


	@Override
	@ParametersAreNonnullByDefault
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		return this.itemImpl.miningSpeedMultiplier;
	}

	@Override
	public int getItemStackLimit() {
		return this.itemImpl.maxCount;
	}

	@Override
	public int getMetadata(int damage) {
		return 0;
	}

	@Override
	public boolean getHasSubtypes() {
		return this.itemImpl.hasVariants;
	}

	@Override
	public boolean isDamageable() {
		return this.itemImpl.maxDamage > 0 && (!this.itemImpl.hasVariants || this.itemImpl.maxCount == 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return this.itemImpl.is3D;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldRotateAroundWhenRendering() {
		return this.itemImpl.requiresRenderRotation;
	}

	@Override
	@ParametersAreNonnullByDefault
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.valueOf( this.itemImpl.useAction.name() );
	}
	@Override
	@ParametersAreNonnullByDefault
	public int getMaxItemUseDuration(ItemStack stack) {
		return this.itemImpl.maxUseTime;
	}

	@Override
	public int getItemEnchantability() {
		return this.itemImpl.enchantability;
	}

	@Nullable
	@Override
	public CreativeTabs getCreativeTab() {
		switch ( this.itemImpl.group ) {
			case "minecraft:itemgroup.brewing":
				return CreativeTabs.BREWING;
			case "minecraft:itemgroup.building_blocks":
				return CreativeTabs.BUILDING_BLOCKS;
			case "minecraft:itemgroup.combat":
				return CreativeTabs.COMBAT;
			case "minecraft:itemgroup.decorations":
				return CreativeTabs.DECORATIONS;
			case "minecraft:itemgroup.food":
				return CreativeTabs.FOOD;
			case "minecraft:itemgroup.materials":
				return CreativeTabs.MATERIALS;
			case "minecraft:itemgroup.redstone":
				return CreativeTabs.REDSTONE;
			case "minecraft:itemgroup.tools":
				return CreativeTabs.TOOLS;
			case "minecraft:itemgroup.transportation":
				return CreativeTabs.TRANSPORTATION;
			default:
				return CreativeTabs.MISC;
		}
	}

	@Override
	@ParametersAreNonnullByDefault
	public boolean getIsRepairable(ItemStack stack, ItemStack ingredient) {
		String material = this.itemImpl.repairMaterial.toString();
		if ( material != null ) {
			return net.minecraft.item.Item.REGISTRY
					.getNameForObject( ingredient.getItem() )
					.toString()
					.equals( material );
		}
		return false;
	}

}
