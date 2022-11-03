package com.enderzombi102.loadercomplex.minecraft.entity;

import com.enderzombi102.loadercomplex.api.annotation.Json;
import com.enderzombi102.loadercomplex.minecraft.util.Position;
import com.enderzombi102.loadercomplex.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;

import java.util.UUID;

/**
 * Basically a LivingEntity equivalent
 */
@ApiStatus.AvailableSince("0.1.3")
public interface Entity {
	/**
	 * Getter for the JSON-based disaply name.
	 */
	@Json String getDisplayName();

	/**
	 * Getter for the string name of this entity
	 */
	String getName();

	/**
	 * Getter for the UUID of this entity
	 */
	UUID getUuid();


	/**
	 * Whether this entity is a {@link LivingEntity}
	 * @return true if it is
	 */
	boolean isLivingEntity();

	/**
	 * Converts this {@link Entity} into a {@link LivingEntity}
	 */
	LivingEntity asLivingEntity();

	/**
	 * Whether this entity is a {@link ItemEntity}
	 * @return true if it is
	 */
	boolean isItem();

	/**
	 * Converts this {@link Entity} into a {@link ItemEntity}
	 */
	ItemEntity asItem();


	/**
	 * Kills this entity without spawning his drop
	 */
	void kill();

	/**
	 * Whether this entity is dead
	 * @return true if it is
	 */
	boolean isDead();


	/**
	 * Getter for the entity position
	 */
	Position getPosition();

	/**
	 * Setter for the entity position
	 */
	void setPosition(Position pos);

	/**
	 * Getter for the entity pitch
	 */
	float getPitch();

	/**
	 * Getter for the entity yaw
	 */
	float getYaw();

	/**
	 * Getter for the entity world
	 * @return the world the entity is in
	 */
	World getWorld();


	/**
	 * Getter for the raw entity object
	 */
	Object getObject();
}
