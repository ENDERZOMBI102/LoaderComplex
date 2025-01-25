package com.enderzombi102.loadercomplex.api.minecraft.world;

import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity;
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity;
import com.enderzombi102.loadercomplex.api.minecraft.block.Blockstate;
import com.enderzombi102.loadercomplex.api.minecraft.util.*;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a minecraft world
 */
@ApiStatus.AvailableSince( "0.1.3" )
public interface World {
	void spawn( Entity entity, Position pos );

	/**
	 * Returns true if this is a ClientWorld
	 */
	boolean isClient();

	boolean isHardcore();

	boolean isSunny();

	boolean isRaining();

	boolean isThundering();

	boolean isAir( Position pos );

	boolean isPositionLoaded( Position pos );

	boolean hasBlockEntity( Position pos );

	boolean hasSkyAccess( Position pos );

	boolean canSnowFall( Position pos );

	Blockstate getBlockState( Position pos );

	void setBlockState( Position pos, Blockstate state );

	void breakBlock( Position pos, boolean dropItems );

	void removeBlock( Position pos );

	Server getServer();

	Position getSpawnLocation();

	Difficulty getDifficulty();

	int getRedstonePower( Position pos, Direction direction );

	void playsound( PlayerEntity player, double x, double y, double z, ResourceIdentifier sound, float volume, float pitch );

	/**
	 * Getter for the raw World object
	 */
	Object getObject();
}
