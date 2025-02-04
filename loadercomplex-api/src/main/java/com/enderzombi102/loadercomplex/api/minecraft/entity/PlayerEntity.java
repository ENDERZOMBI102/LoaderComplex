package com.enderzombi102.loadercomplex.api.minecraft.entity;

import com.enderzombi102.loadercomplex.api.math.Vec3i;
import com.enderzombi102.loadercomplex.api.minecraft.util.Gamemode;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;

/**
 * Represents a player entity
 */
@ApiStatus.AvailableSince( "0.1.3" )
public interface PlayerEntity extends LivingEntity {
	boolean isSleeping();

	Optional<Vec3i> getBedLocation();

	int getScore();

	void addScore( int score );

	void sendMessage( String msg );

	void setRespawnPoint( Vec3i pos );

	int getFoodLevel();

	void setFoodLevel( int level );

	float getSaturationLevel();

	void setSaturationLevel( float level );

	Gamemode getGamemode();

	int getExperienceLevel();
}
