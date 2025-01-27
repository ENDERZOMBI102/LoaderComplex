package com.enderzombi102.loadercomplex.api.minecraft.entity;

import com.enderzombi102.loadercomplex.api.minecraft.item.EquipmentSlot;
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a living entity, with health, inventory and stuff.
 */
@ApiStatus.AvailableSince("0.1.3")
public interface LivingEntity extends Entity {
	float getHealth();
	void setHealth(float health);

	boolean isPlayer();
	PlayerEntity asPlayer();

	boolean isChild();
	boolean isWaterMob();
	boolean isClimbing();

	int getArmorProtection();
	ItemStack getItemInMainHand();
	ItemStack getItemInOffHand();
	boolean hasEquipment( EquipmentSlot slot);
	ItemStack getEquipment( EquipmentSlot slot);
	void setEquipment( EquipmentSlot slot, ItemStack stack);
}
