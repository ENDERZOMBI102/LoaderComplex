package com.enderzombi102.loadercomplex.api.minecraft.item;

import com.enderzombi102.loadercomplex.api.math.Direction;
import com.enderzombi102.loadercomplex.api.math.Vec3i;
import com.enderzombi102.loadercomplex.api.minecraft.block.BlockState;
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity;
import com.enderzombi102.loadercomplex.api.minecraft.entity.LivingEntity;
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity;
import com.enderzombi102.loadercomplex.api.minecraft.util.*;
import com.enderzombi102.loadercomplex.api.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents an extensible item.<br/>
 * <br/>
 * Any item implemented by addons should extend this class.
 */
public abstract class Item {
	public ResourceIdentifier creativeTab = null;
	public int maxCount = 64;
	public int maxDamage = 0;
	public boolean is3D = false;
	public boolean hasVariants = false;
	public int maxUseTime = 0;
	public int enchantability = 0;
	public ResourceIdentifier repairMaterial = null;
	public ResourceIdentifier placeholderItem = new ResourceIdentifier("minecraft", "stick");
	public boolean requiresRenderRotation = false;
	public UseAction useAction = UseAction.NONE;
	public float miningSpeedMultiplier = 1.0F;
	public String translationKey;

	/**
	 * Internal field, do not use.<br/>
	 * Holds the Loader-Specific wrapper instance for this item
	 */
	public Object implementationItem;

	// methods
	@ApiStatus.AvailableSince("0.1.3")
	public ActionResult useOnBlock( World world, PlayerEntity player, Vec3i pos, Hand hand, Direction facing ) { return ActionResult.PASS; }

	@ApiStatus.AvailableSince("0.1.3")
	public ActionResultHolder<ItemStack> startUsing( World world, PlayerEntity player, ItemStack stack ) { return ActionResultHolder.pass( stack ); }

	@ApiStatus.AvailableSince("0.1.3")
	public ItemStack finishUsing( ItemStack stack, World world, LivingEntity user ) { return stack; }

	@ApiStatus.AvailableSince("0.1.3")
	public boolean postAttack( ItemStack stack, LivingEntity target, LivingEntity attacker ) { return false; }

	@ApiStatus.AvailableSince("0.1.3")
	public boolean postMine( ItemStack stack, World world, BlockState state, Vec3i pos, LivingEntity miner ) { return false; }

	@ApiStatus.AvailableSince("0.1.3")
	public boolean canMineBlock( BlockState state ) { return false; }

	@ApiStatus.AvailableSince("0.1.3")
	public ActionResult useOnEntity( ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand ) { return ActionResult.PASS; }

	@ApiStatus.AvailableSince("0.1.3")
	public void inventoryTick( ItemStack stack, Entity entity, int slot, boolean selected ) { }

	@ApiStatus.AvailableSince("0.1.3")
	public void onCraft( ItemStack stack, PlayerEntity player ) { }

	@ApiStatus.AvailableSince("0.1.3")
	public void onStoppedUsing( ItemStack stack, World world, LivingEntity user, int remainingUseTicks ) { }

	public boolean validateTag( Object tag ) { return false; }
}
