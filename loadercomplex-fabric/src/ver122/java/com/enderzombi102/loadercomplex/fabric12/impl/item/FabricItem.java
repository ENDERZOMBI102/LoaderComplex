package com.enderzombi102.loadercomplex.fabric12.impl.item;


import com.enderzombi102.loadercomplex.api.minecraft.item.Item;
import com.enderzombi102.loadercomplex.api.minecraft.util.Hand;
import com.enderzombi102.loadercomplex.fabric12.impl.block.FabricBlockstate;
import com.enderzombi102.loadercomplex.fabric12.impl.entity.FabricEntity;
import com.enderzombi102.loadercomplex.fabric12.impl.entity.FabricLivingEntity;
import com.enderzombi102.loadercomplex.fabric12.impl.entity.FabricPlayer;
import com.enderzombi102.loadercomplex.fabric12.impl.utils.ConversionKt;
import com.enderzombi102.loadercomplex.fabric12.impl.world.FabricWorld;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.World;

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
	public boolean validateNbt( NbtCompound nbt ) {
		return this.itemImpl.validateTag( nbt );
	}

	@Override
	public InteractionResult use( PlayerEntity player, World world, BlockPos pos, InteractionHand hand, Direction face, float faceX, float faceY, float faceZ ) {
		return InteractionResult.valueOf(
			this.itemImpl.useOnBlock(
				new FabricWorld( world ),
				new FabricPlayer( player ),
				ConversionKt.toLC( pos ),
				Hand.valueOf( hand.name() ),
				com.enderzombi102.loadercomplex.api.minecraft.util.Direction.valueOf( face.name() )
			).name()
		);
	}

	@Override
	public InteractionResultHolder<ItemStack> startUsing( World world, PlayerEntity player, InteractionHand hand ) {
		ItemStack stack = player.getItemInHand( hand );
		return new InteractionResultHolder<>(
			InteractionResult.valueOf(
				this.itemImpl.startUsing(
					new FabricWorld( world ),
					new FabricPlayer( player ),
					new FabricItemStack( stack )
				).name()
			),
			stack
		);
	}

	@Override
	public ItemStack finishUsing( ItemStack stack, World world, LivingEntity entity ) {
		return (
			(FabricItemStack) this.itemImpl.finishUsing(
				new FabricItemStack( stack ),
				new FabricWorld( world ),
				new FabricLivingEntity( entity )
			)
		).getStack();
	}

	@Override
	public boolean attack( ItemStack stack, LivingEntity target, LivingEntity attacker ) {
		return this.itemImpl.postAttack(
			new FabricItemStack( stack ),
			new FabricLivingEntity( target ),
			new FabricLivingEntity( attacker )
		);
	}

	@Override
	public boolean mineBlock( ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity entity ) {
		return this.itemImpl.postMine(
			new FabricItemStack( stack ),
			new FabricWorld( world ),
			new FabricBlockstate( state ),
			ConversionKt.toLC( pos ),
			new FabricLivingEntity( entity )
		);
	}

	@Override
	public boolean canMineBlock( BlockState state ) {
		return this.itemImpl.canMineBlock( new FabricBlockstate( state ) );
	}

	@Override
	public boolean interact( ItemStack stack, PlayerEntity player, LivingEntity entity, InteractionHand hand ) {
		return this.itemImpl.useOnEntity(
			new FabricItemStack( stack ),
			new FabricPlayer( player ),
			new FabricLivingEntity( entity ),
			Hand.valueOf( hand.name() )
		);
	}

	@Override
	public void tick( ItemStack stack, World world, Entity entity, int slot, boolean selected ) {
		this.itemImpl.inventoryTick(
			new FabricItemStack( stack ),
			new FabricEntity( entity ),
			slot,
			selected
		);
	}

	@Override
	public void onResult( ItemStack stack, World world, PlayerEntity player ) {
		this.itemImpl.onCraft(
			new FabricItemStack( stack ),
			new FabricPlayer( player )
		);
	}

	@Override
	public void stopUsing( ItemStack stack, World world, LivingEntity entity, int remainingUseTime ) {
		this.itemImpl.onStoppedUsing(
			new FabricItemStack( stack ),
			new FabricWorld( world ),
			new FabricLivingEntity( entity ),
			remainingUseTime
		);
	}

	// getter methods override


	@Override
	public float getMiningSpeed( ItemStack stack, BlockState state ) {
		return this.itemImpl.miningSpeedMultiplier;
	}

	@Override
	public int getMaxStackSize() {
		return this.itemImpl.maxCount;
	}

	@Override
	public int getMaxDamage() {
		return this.itemImpl.maxDamage;
	}

	@Override
	public boolean isDamageable() {
		return this.itemImpl.maxDamage > 0 && (!this.itemImpl.hasVariants || this.itemImpl.maxCount == 1);
	}

	@Environment( EnvType.CLIENT )
	@Override
	public boolean isHandheld() {
		return this.itemImpl.is3D;
	}

	@Environment( EnvType.CLIENT )
	@Override
	public boolean shouldRotate() {
		return this.itemImpl.requiresRenderRotation;
	}

	@Override
	public net.minecraft.item.UseAction getUseAction( ItemStack stack ) {
		return net.minecraft.item.UseAction.valueOf( this.itemImpl.useAction.name() );
	}

	@Override
	public int getEnchantability() {
		return this.itemImpl.enchantability;
	}

	@Override
	public boolean isRepairable( ItemStack stack, ItemStack ingredient ) {
		String material = this.itemImpl.repairMaterial.toString();
		if ( material != null ) {
			//noinspection ConstantConditions
			return net.minecraft.item.Item.REGISTRY
				.getKey( ingredient.getItem() )
				.toString()
				.equals( material );
		}
		return false;
	}

	public Item getItemImpl() {
		return this.itemImpl;
	}
}
