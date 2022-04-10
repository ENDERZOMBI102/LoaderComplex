package com.enderzombi102.loadercomplex.fabric12.impl.item;


import com.enderzombi102.loadercomplex.api.item.Item;
import com.enderzombi102.loadercomplex.api.utils.Hand;
import com.enderzombi102.loadercomplex.fabric12.impl.block.FabricBlockstate;
import com.enderzombi102.loadercomplex.fabric12.impl.entity.FabricEntity;
import com.enderzombi102.loadercomplex.fabric12.impl.entity.FabricLivingEntity;
import com.enderzombi102.loadercomplex.fabric12.impl.entity.FabricPlayer;
import com.enderzombi102.loadercomplex.fabric12.impl.utils.BlockUtils;
import com.enderzombi102.loadercomplex.fabric12.impl.world.FabricWorld;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

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

	// FIXME: Fix this error in the mappings: this is use, the other is useOnBlock
	@Override
	public TypedActionResult<ItemStack> method_13649(World world, PlayerEntity user, net.minecraft.util.Hand hand) {
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

	// FIXME: Same as above but with `finishUsing`
	@Override
	public ItemStack method_3367(ItemStack stack, World world, LivingEntity user) {
		return (
			(FabricItemStack) this.itemImpl.finishUsing(
				new FabricItemStack( stack ),
				new FabricWorld( world ),
				new FabricLivingEntity( user )
			)
		).getStack();
	}

	// FIXME: Same as above but with `postHit`
	@Override
	public boolean onEntityHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		return this.itemImpl.postHit(
			new FabricItemStack( stack ),
			new FabricLivingEntity( target ),
			new FabricLivingEntity( attacker )
		);
	}

	// FIXME: Same as above but with `postMine`
	@Override
	public boolean method_3356(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		return this.itemImpl.postMine(
			new FabricItemStack( stack ),
			new FabricWorld( world ),
			new FabricBlockstate( state ),
			BlockUtils.toPosition( pos ),
			new FabricLivingEntity( miner )
		);
	}

	// FIXME: Same as above but with `isEffectiveOn`
	@Override
	public boolean method_3346(BlockState state) {
		return this.itemImpl.isEffectiveOn( new FabricBlockstate( state ) );
	}

	// FIXME: Same as above but with `useOnEntity`
	@Override
	public boolean method_3353(ItemStack stack, PlayerEntity user, LivingEntity entity, net.minecraft.util.@NotNull Hand hand) {
		return this.itemImpl.useOnEntity(
			new FabricItemStack(stack),
			new FabricPlayer( user ),
			new FabricLivingEntity( entity ),
			Hand.valueOf( hand.name() )
		);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		this.itemImpl.inventoryTick(
			new FabricItemStack(stack),
			new FabricEntity( entity ),
			slot,
			selected
		);
	}

	@Override
	public void onCraft(ItemStack stack, World world, PlayerEntity player) {
		this.itemImpl.onCraft(
			new FabricItemStack(stack),
			new FabricPlayer( player )
		);
	}

	// FIXME: Same as above but with `onStoppedUsing`
	@Override
	public void method_3359(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		this.itemImpl.onStoppedUsing(
			new FabricItemStack(stack),
			new FabricWorld( world ),
			new FabricLivingEntity( user ),
			remainingUseTicks
		);
	}

	// getter methods override

	// getMiningSpeedMultiplier
	@Override
	public float method_3351(ItemStack stack, BlockState state) {
		return this.itemImpl.miningSpeedMultiplier;
	}

	@Override
	public int getMaxCount() {
		return this.itemImpl.maxCount;
	}

	@Override
	public int getMeta(int damage) {
		return 0;
	}

	@Override
	public boolean hasSubTypes() {
		return this.itemImpl.hasVariants;
	}

	@Override
	public int getMaxDamage() {
		return this.itemImpl.maxDamage;
	}

	@Override
	public boolean isDamageable() {
		return this.itemImpl.maxDamage > 0 && (!this.itemImpl.hasVariants || this.itemImpl.maxCount == 1);
	}

	@Environment(EnvType.CLIENT)
	@Override
	public boolean isHandheld() {
		return this.itemImpl.is3D;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public boolean shouldRotate() {
		return this.itemImpl.requiresRenderRotation;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.valueOf( this.itemImpl.useAction.name() );
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return this.itemImpl.maxUseTime;
	}

	@Override
	public int getEnchantability() {
		return this.itemImpl.enchantability;
	}

	@Override
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

	public Item getItemImpl() {
		return this.itemImpl;
	}
}
