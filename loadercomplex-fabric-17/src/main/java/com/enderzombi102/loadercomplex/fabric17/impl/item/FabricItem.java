package com.enderzombi102.loadercomplex.fabric17.impl.item;


import com.enderzombi102.loadercomplex.api.item.Item;
import com.enderzombi102.loadercomplex.api.utils.Hand;
import com.enderzombi102.loadercomplex.fabric17.impl.block.FabricBlockstate;
import com.enderzombi102.loadercomplex.fabric17.impl.entity.FabricEntity;
import com.enderzombi102.loadercomplex.fabric17.impl.entity.FabricLivingEntity;
import com.enderzombi102.loadercomplex.fabric17.impl.entity.FabricPlayer;
import com.enderzombi102.loadercomplex.fabric17.impl.utils.BlockUtils;
import com.enderzombi102.loadercomplex.Callable;
import com.enderzombi102.loadercomplex.fabric17.impl.world.FabricWorld;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
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

public class FabricItem extends net.minecraft.item.Item {
	private final Item itemImpl;

	public FabricItem( Item item ) {
		super(
			( (Callable<FabricItemSettings>) () -> {
				var settings = new FabricItemSettings()
						.maxCount( item.maxCount )
						.maxDamage( item.maxDamage );

				if ( getGroup( item ) != null )
					settings.group( getGroup( item ) );

				return settings;
			}).call()
		);
		this.itemImpl = item;
		item.implementationItem = this;
	}

	// logic methods override


	@Override
	public void postProcessNbt(NbtCompound nbt ) {
		this.itemImpl.postProcesstag( nbt );
	}

	@Override
	public ActionResult useOnBlock( ItemUsageContext ctx ) {
		return ActionResult.valueOf(
			this.itemImpl.useOnBlock(
				new FabricWorld( ctx.getWorld() ),
				new FabricPlayer( ctx.getPlayer() ),
				BlockUtils.toPosition( ctx.getBlockPos() ),
				Hand.valueOf( ctx.getHand().name() ),
				com.enderzombi102.loadercomplex.api.utils.Direction.valueOf( ctx.getPlayerFacing().name() )
			).name()
		);
	}

	@Override
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

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		return (
			(FabricItemStack) this.itemImpl.finishUsing(
				new FabricItemStack( stack ),
				new FabricWorld( world ),
				new FabricLivingEntity( user )
			)
		).getStack();
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		return this.itemImpl.postHit(
			new FabricItemStack( stack ),
			new FabricLivingEntity( target ),
			new FabricLivingEntity( attacker )
		);
	}

	@Override
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		return this.itemImpl.postMine(
			new FabricItemStack( stack ),
			new FabricWorld( world ),
			new FabricBlockstate( state ),
			BlockUtils.toPosition( pos ),
			new FabricLivingEntity( miner )
		);
	}

	@Override
	public boolean isSuitableFor(BlockState state) {
		return this.itemImpl.isEffectiveOn( new FabricBlockstate( state ) );
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, net.minecraft.util.Hand hand) {
		return this.itemImpl.useOnEntity(
			new FabricItemStack(stack),
			new FabricPlayer( user ),
			new FabricLivingEntity( entity ),
			Hand.valueOf( hand.name() )
		) ? ActionResult.SUCCESS : ActionResult.PASS;
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

	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		this.itemImpl.onStoppedUsing(
			new FabricItemStack(stack),
			new FabricWorld( world ),
			new FabricLivingEntity( user ),
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

	private static ItemGroup getGroup( Item item ) {
		if ( item.group == null )
			return null;
		return switch (item.group) {
			case "minecraft:itemgroup.brewing" -> ItemGroup.BREWING;
			case "minecraft:itemgroup.building_blocks" -> ItemGroup.BUILDING_BLOCKS;
			case "minecraft:itemgroup.combat" -> ItemGroup.COMBAT;
			case "minecraft:itemgroup.decorations" -> ItemGroup.DECORATIONS;
			case "minecraft:itemgroup.food" -> ItemGroup.FOOD;
			case "minecraft:itemgroup.materials" -> ItemGroup.MATERIALS;
			case "minecraft:itemgroup.redstone" -> ItemGroup.REDSTONE;
			case "minecraft:itemgroup.tools" -> ItemGroup.TOOLS;
			case "minecraft:itemgroup.transportation" -> ItemGroup.TRANSPORTATION;
			case "minecraft:itemgroup.misc" -> ItemGroup.MISC;
			default -> ItemGroup.MISC;
		};
	}

	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		String material = this.itemImpl.repairMaterial.toString();
		if ( material != null ) {
			return Registry.ITEM
					.getId( ingredient.getItem() )
					.toString()
					.equals( material );
		}
		return false;
	}
}
