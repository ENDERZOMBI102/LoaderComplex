package com.enderzombi102.loadercomplex.forge12.impl.item;


import com.enderzombi102.loadercomplex.api.minecraft.item.Item;
import com.enderzombi102.loadercomplex.api.minecraft.util.Direction;
import com.enderzombi102.loadercomplex.api.minecraft.util.Hand;
import com.enderzombi102.loadercomplex.forge12.impl.utils.ForgeRegistry;
import com.enderzombi102.loadercomplex.forge12.impl.block.ForgeBlockstate;
import com.enderzombi102.loadercomplex.forge12.impl.entity.ForgeEntity;
import com.enderzombi102.loadercomplex.forge12.impl.entity.ForgeLivingEntity;
import com.enderzombi102.loadercomplex.forge12.impl.entity.ForgePlayer;
import com.enderzombi102.loadercomplex.forge12.impl.utils.BlockUtils;
import com.enderzombi102.loadercomplex.forge12.impl.world.ForgeWorld;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier.ri;


public class ForgeItem extends net.minecraft.item.Item {
	private final Item itemImpl;

	public ForgeItem( Item item ) {
		this.itemImpl = item;
		item.implementationItem = this;
		this.setMaxDamage( this.itemImpl.maxDamage );
	}

	// logic methods override


	@Override
	public boolean updateItemStackNBT( @NotNull NBTTagCompound tag ) {
		return false;
	}

	@Override
	public @NotNull EnumActionResult onItemUse( @NotNull EntityPlayer player, @NotNull World world, @NotNull BlockPos pos, @NotNull net.minecraft.util.EnumHand hand, @NotNull EnumFacing facing, float x, float y, float z ) {
		return EnumActionResult.valueOf(
			this.itemImpl.useOnBlock(
				new ForgeWorld( world ),
				new ForgePlayer( player ),
				BlockUtils.toPosition( pos ),
				Hand.valueOf( hand.name() ),
				Direction.valueOf( facing.name() )
			).name()
		);
	}

	@Override
	public @NotNull ActionResult<ItemStack> onItemRightClick( @NotNull World world, @NotNull EntityPlayer user, @NotNull net.minecraft.util.EnumHand hand ) {
		ItemStack stack = user.getHeldItem( hand );
		return new ActionResult<>(
			EnumActionResult.valueOf(
				this.itemImpl.startUsing( new ForgeWorld( world ), new ForgePlayer( user ), new ForgeItemStack( stack ) ).name()
			),
			stack
		);
	}

	@Override
	public @NotNull ItemStack onItemUseFinish( @NotNull ItemStack stack, @NotNull World world, @NotNull EntityLivingBase user ) {
		return (ItemStack) this.itemImpl.finishUsing(
			new ForgeItemStack( stack ),
			new ForgeWorld( world ),
			new ForgeLivingEntity( user )
		).getStack();
	}

	@Override
	public boolean hitEntity( @NotNull ItemStack stack, @NotNull EntityLivingBase target, @NotNull EntityLivingBase attacker ) {
		return this.itemImpl.postAttack(
			new ForgeItemStack( stack ),
			new ForgeLivingEntity( target ),
			new ForgeLivingEntity( attacker )
		);
	}

	@Override
	public boolean onBlockDestroyed( @NotNull ItemStack stack, @NotNull World world, @NotNull IBlockState state, @NotNull BlockPos pos, @NotNull EntityLivingBase miner ) {
		return this.itemImpl.postMine(
			new ForgeItemStack( stack ),
			new ForgeWorld( world ),
			new ForgeBlockstate( state ),
			BlockUtils.toPosition( pos ),
			new ForgeLivingEntity( miner )
		);
	}

	@Override
	public boolean canHarvestBlock( @NotNull IBlockState state ) {
		return this.itemImpl.canMineBlock( new ForgeBlockstate( state ) );
	}

	@Override
	public boolean itemInteractionForEntity( @NotNull ItemStack stack, @NotNull EntityPlayer user, @NotNull EntityLivingBase entity, @NotNull EnumHand hand ) {
		return this.itemImpl.useOnEntity(
			new ForgeItemStack( stack ),
			new ForgePlayer( user ),
			new ForgeLivingEntity( entity ),
			Hand.valueOf( hand.name() )
		);
	}

	@Override
	public void onUpdate( @NotNull ItemStack stack, @NotNull World world, @NotNull Entity entity, int slot, boolean selected ) {
		this.itemImpl.inventoryTick( new ForgeItemStack( stack ), new ForgeEntity( entity ), slot, selected );
	}

	@Override
	public void onCreated( @NotNull ItemStack stack, @NotNull World world, @NotNull EntityPlayer player ) {
		this.itemImpl.onCraft( new ForgeItemStack( stack ), new ForgePlayer( player ) );
	}

	@Override
	public void onPlayerStoppedUsing( @NotNull ItemStack stack, @NotNull World world, @NotNull EntityLivingBase user, int remainingUseTicks ) {
		this.itemImpl.onStoppedUsing(
			new ForgeItemStack( stack ),
			new ForgeWorld( world ),
			new ForgeLivingEntity( user ),
			remainingUseTicks
		);
		// TODO: Check if correct
		this.itemImpl.finishUsing(
			new ForgeItemStack( stack ),
			new ForgeWorld( world ),
			new ForgeLivingEntity( user )
		);
	}

	@Override
	protected boolean isInCreativeTab( @NotNull CreativeTabs group ) {
		CreativeTabs itemGroup = this.getCreativeTab();
		return itemGroup != null && (group == CreativeTabs.SEARCH || group == itemGroup);
	}

	// getter methods override


	@Override
	public float getDestroySpeed( @NotNull ItemStack stack, @NotNull IBlockState state ) {
		return this.itemImpl.miningSpeedMultiplier;
	}

	@SuppressWarnings( "deprecation" )
	@Override
	public int getItemStackLimit() {
		return this.itemImpl.maxCount;
	}

	@Override
	public int getMetadata( int damage ) {
		throw new RuntimeException( "Not implemented" );
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
	@SideOnly( Side.CLIENT )
	public boolean isFull3D() {
		return this.itemImpl.is3D;
	}

	@Override
	@SideOnly( Side.CLIENT )
	public boolean shouldRotateAroundWhenRendering() {
		return this.itemImpl.requiresRenderRotation;
	}

	@Override
	public @NotNull EnumAction getItemUseAction( @NotNull ItemStack stack ) {
		return EnumAction.valueOf( this.itemImpl.useAction.name() );
	}

	@Override
	public int getMaxItemUseDuration( @NotNull ItemStack stack ) {
		return this.itemImpl.maxUseTime;
	}

	@Override
	public int getItemEnchantability() {
		return this.itemImpl.enchantability;
	}

	@Override
	public @Nullable CreativeTabs getCreativeTab() {
		return ForgeRegistry.getOrCreateCreativeTab(
			this.itemImpl.creativeTab,
			ri( Objects.requireNonNull( this.getRegistryName() ).toString() )
		);
	}

	@Override
	public boolean getIsRepairable( @NotNull ItemStack stack, @NotNull ItemStack ingredient ) {
		String material = this.itemImpl.repairMaterial.toString();
		if ( material != null ) {
			//noinspection ConstantConditions
			return net.minecraft.item.Item.REGISTRY
				.getNameForObject( ingredient.getItem() )
				.toString()
				.equals( material );
		}
		return false;
	}

}
