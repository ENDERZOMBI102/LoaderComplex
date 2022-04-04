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

	boolean isLivingEntity();
	LivingEntity asLivingEntity();
	boolean isItem();
	ItemEntity asItem();

	void kill();
	boolean isDead();

	Position getPosition();
	void setPosition(Position pos);
	float getPitch();
	float getYaw();
	World getWorld();

	Object getObject();
}
