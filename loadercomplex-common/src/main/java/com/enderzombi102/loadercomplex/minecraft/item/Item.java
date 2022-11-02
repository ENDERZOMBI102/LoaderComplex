package com.enderzombi102.loadercomplex.minecraft.item;

import com.enderzombi102.loadercomplex.minecraft.block.Blockstate;
import com.enderzombi102.loadercomplex.minecraft.entity.Entity;
import com.enderzombi102.loadercomplex.minecraft.entity.LivingEntity;
import com.enderzombi102.loadercomplex.minecraft.entity.Player;
import com.enderzombi102.loadercomplex.minecraft.util.*;
import com.enderzombi102.loadercomplex.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents an extensible item.<br/>
 * <br/>
 * Any item implemented by addons should extend this class.
 */
public abstract class Item {
	public ResourceIdentifier group = null;
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
	public ActionResult useOnBlock( World world, Player player, Position pos, Hand hand, Direction facing ) { return ActionResult.PASS; }

	@ApiStatus.AvailableSince("0.1.3")
	public ActionResult use( World world, Player player, ItemStack stack ) { return ActionResult.PASS; }

	@ApiStatus.AvailableSince("0.1.3")
	public ItemStack finishUsing( ItemStack stack, World world, LivingEntity user ) { return stack; }

	@ApiStatus.AvailableSince("0.1.3")
	public boolean postHit( ItemStack stack, LivingEntity target, LivingEntity attacker ) { return false; }

	@ApiStatus.AvailableSince("0.1.3")
	public boolean postMine( ItemStack stack, World world, Blockstate state, Position pos, LivingEntity miner ) { return false; }

	@ApiStatus.AvailableSince("0.1.3")
	public boolean isEffectiveOn( Blockstate state ) { return false; }

	@ApiStatus.AvailableSince("0.1.3")
	public boolean useOnEntity( ItemStack stack, Player user, LivingEntity entity, Hand hand ) { return false; }

	@ApiStatus.AvailableSince("0.1.3")
	public void inventoryTick( ItemStack stack, Entity entity, int slot, boolean selected ) { }

	@ApiStatus.AvailableSince("0.1.3")
	public void onCraft( ItemStack stack, Player player ) { }

	@ApiStatus.AvailableSince("0.1.3")
	public void onStoppedUsing( ItemStack stack, World world, LivingEntity user, int remainingUseTicks ) { }

	public boolean postProcesstag( Object tag ) { return false; }
}
