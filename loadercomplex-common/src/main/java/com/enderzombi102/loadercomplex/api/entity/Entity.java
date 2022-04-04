package com.enderzombi102.loadercomplex.api.entity;

import com.enderzombi102.loadercomplex.api.item.EquipmentSlot;
import com.enderzombi102.loadercomplex.api.item.ItemStack;
import com.enderzombi102.loadercomplex.api.utils.Json;
import com.enderzombi102.loadercomplex.api.utils.Position;
import com.enderzombi102.loadercomplex.api.world.World;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

import java.util.UUID;

/**
 * Basically a LivingEntity equivalent
 */
@ApiStatus.AvailableSince("0.1.3")
public interface Entity {
	@Json String getDisplayName();
	String getName();
	UUID getUuid();

	float getHealth();
	void setHealth(float health);

	boolean isPlayer();
	Player asPlayer();
	boolean isItem();
	ItemEntity asItem();

	boolean isChild();
	boolean canBreathUnderwater();
	boolean isAttachedToLadder();
	void kill();

	int getArmorValue();
	ItemStack getItemInMainHand();
	ItemStack getItemInOffHand();
	boolean hasItemInSlot(EquipmentSlot slot);
	ItemStack getStackInSlot(EquipmentSlot slot);
	void setStackInSlot(EquipmentSlot slot, ItemStack stack);

	Position getPosition();
	void setPosition(Position pos);
	float getPitch();
	float getYaw();
	World getWorld();
}
