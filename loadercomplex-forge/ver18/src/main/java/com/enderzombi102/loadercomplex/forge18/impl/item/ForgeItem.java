package com.enderzombi102.loadercomplex.forge18.impl.item;


import com.enderzombi102.loadercomplex.api.minecraft.item.Item;
import com.enderzombi102.loadercomplex.api.minecraft.util.Direction;
import com.enderzombi102.loadercomplex.api.minecraft.util.Hand;
import com.enderzombi102.loadercomplex.forge18.impl.block.ForgeBlockstate;
import com.enderzombi102.loadercomplex.forge18.impl.entity.ForgeEntity;
import com.enderzombi102.loadercomplex.forge18.impl.entity.ForgeLivingEntity;
import com.enderzombi102.loadercomplex.forge18.impl.entity.ForgePlayer;
import com.enderzombi102.loadercomplex.forge18.impl.utils.BlockUtils;
import com.enderzombi102.loadercomplex.forge18.impl.world.ForgeWorld;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.RegistryManager;

public class ForgeItem extends net.minecraft.item.Item {
	private final Item itemImpl;

	public ForgeItem(Item item ) {
		super(
			new Settings()
				.maxCount( item.maxCount )
				.maxDamage( item.maxDamage )
		);
		this.itemImpl = item;
		item.implementationItem = this;
	}

	// logic methods override

	@Override
	public void postProcessNbt(NbtCompound nbt ) {
		this.itemImpl.postProcessTag( nbt );
	}

	@Override
	public ActionResult useOnBlock( ItemUsageContext ctx ) {
		return ActionResult.valueOf(
			this.itemImpl.useOnBlock(
				new ForgeWorld( ctx.getWorld() ),
				new ForgePlayer( ctx.getPlayer() ),
				BlockUtils.toPosition( ctx.getBlockPos() ),
				Hand.valueOf( ctx.getHand().name() ),
				Direction.valueOf( ctx.getPlayerFacing().name() )
			).name()
		);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, net.minecraft.util.Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		return new TypedActionResult<>(
			ActionResult.valueOf(
				this.itemImpl.use(
					new ForgeWorld( world ),
					new ForgePlayer( user ),
					new ForgeItemStack( stack )
				).name()
			),
			stack
		);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		return (
			(ForgeItemStack) this.itemImpl.finishUsing(
				new ForgeItemStack( stack ),
				new ForgeWorld( world ),
				new ForgeLivingEntity( user )
			)
		).getStack();
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		return this.itemImpl.postHit(
			new ForgeItemStack( stack ),
			new ForgeLivingEntity( target ),
			new ForgeLivingEntity( attacker )
		);
	}

	@Override
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		return this.itemImpl.postMine(
			new ForgeItemStack( stack ),
			new ForgeWorld( world ),
			new ForgeBlockstate( state ),
			BlockUtils.toPosition( pos ),
			new ForgeLivingEntity( miner )
		);
	}

	@Override
	public boolean isSuitableFor(BlockState state) {
		return this.itemImpl.isEffectiveOn( new ForgeBlockstate( state ) );
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, net.minecraft.util.Hand hand) {
		return this.itemImpl.useOnEntity(
			new ForgeItemStack(stack),
			new ForgePlayer( user ),
			new ForgeLivingEntity( entity ),
			Hand.valueOf( hand.name() )
		) ? ActionResult.SUCCESS : ActionResult.PASS;
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		this.itemImpl.inventoryTick(
			new ForgeItemStack(stack),
			new ForgeEntity( entity ),
			slot,
			selected
		);
	}

	@Override
	public void onCraft(ItemStack stack, World world, PlayerEntity player) {
		this.itemImpl.onCraft(
			new ForgeItemStack(stack),
			new ForgePlayer( player )
		);
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		this.itemImpl.onStoppedUsing(
			new ForgeItemStack(stack),
			new ForgeWorld( world ),
			new ForgeLivingEntity( user ),
			remainingUseTicks
		);
	}

	@Override
	protected boolean isIn(ItemGroup group) {
		ItemGroup itemGroup = this.getGroup();
		return itemGroup != null && ( group == ItemGroup.SEARCH || group == itemGroup );
	}

	// getter methods override

	// getMiningSpeedMultiplier

	@Override
	public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
		return this.itemImpl.miningSpeedMultiplier;
	}

	@Override
	public boolean isDamageable() {
		return this.itemImpl.maxDamage > 0 && (!this.itemImpl.hasVariants || this.itemImpl.maxCount == 1);
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
			return RegistryManager.ACTIVE.getRegistry( Registry.ITEM_KEY )
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
