package com.enderzombi102.loadercomplex.api.item;

import com.enderzombi102.loadercomplex.api.block.Blockstate;
import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.entity.LivingEntity;
import com.enderzombi102.loadercomplex.api.entity.Player;
import com.enderzombi102.loadercomplex.api.utils.*;
import com.enderzombi102.loadercomplex.api.world.World;
import org.jetbrains.annotations.ApiStatus;

public abstract class Item {

	public String group = null;
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

	// IMPLEMENTATION ATTRIBUTE
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
