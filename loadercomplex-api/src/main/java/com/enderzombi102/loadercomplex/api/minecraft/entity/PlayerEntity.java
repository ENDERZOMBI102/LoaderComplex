package com.enderzombi102.loadercomplex.api.minecraft.entity;

import com.enderzombi102.loadercomplex.api.minecraft.util.Gamemode;
import com.enderzombi102.loadercomplex.api.minecraft.util.Position;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a player entity
 */
@ApiStatus.AvailableSince( "0.1.3" )
public interface PlayerEntity extends LivingEntity {
	boolean isSleeping();

	Position getBedLocation();

	int getScore();

	void addScore( int score );

	void sendMessage( String msg );

	void setRespawnPoint( Position pos );

	int getFoodLevel();

	void setFoodLevel( int level );

	float getSaturationLevel();

	void setSaturationLevel( float level );

	Gamemode getGamemode();

	int getExperienceLevel();
}
