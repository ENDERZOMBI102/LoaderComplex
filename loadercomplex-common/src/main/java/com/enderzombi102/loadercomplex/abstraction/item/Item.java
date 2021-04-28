package com.enderzombi102.loadercomplex.abstraction.item;


import com.enderzombi102.loadercomplex.abstraction.utils.ActionResult;
import com.enderzombi102.loadercomplex.abstraction.utils.Hand;
import com.enderzombi102.loadercomplex.abstraction.utils.ResourceIdentifier;
import com.enderzombi102.loadercomplex.abstraction.utils.UseAction;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class Item {

	public String group = null;
	public int maxCount = 64;
	public int maxDamage = 0;
	public boolean is3D = false;
	public boolean hasVariants = false;
	public int maxUseTime = 0;
	public int enchantability = 0;
	public ResourceIdentifier repairMaterial = null;
	public boolean requiresRenderRotation = false;
	public UseAction useAction = UseAction.NONE;
	public float miningSpeedMultiplier = 1.0F;
	public String translationKey;

	// IMPLEMENTATION ATTRIBUTE
	public Object implementationItem;

	// methods
	public ActionResult useOnBlock() { return ActionResult.PASS; }
	public ActionResult use() { return ActionResult.PASS; }
	public ItemStack finishUsing(ItemStack stack, Object user) { return stack; }
	public boolean postHit(ItemStack stack, Object target, Object attacker) { return false; }
	public boolean postMine(ItemStack stack, Object miner) { return false; }
	public boolean isEffectiveOn(Object state) { return false; }
	public boolean useOnEntity(ItemStack stack, Object user, Object entity, Hand hand) { return false; }
	public void inventoryTick(ItemStack stack, Object entity, int slot, boolean selected) { }
	public void onCraft(ItemStack stack, Object player) { }
	public void onStoppedUsing(ItemStack stack, Object user, int remainingUseTicks) { }

}
