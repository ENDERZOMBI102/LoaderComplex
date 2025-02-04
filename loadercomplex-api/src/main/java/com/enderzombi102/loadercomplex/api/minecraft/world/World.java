package com.enderzombi102.loadercomplex.api.minecraft.world;

import com.enderzombi102.loadercomplex.api.math.Direction;
import com.enderzombi102.loadercomplex.api.math.Vec3d;
import com.enderzombi102.loadercomplex.api.math.Vec3i;
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity;
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity;
import com.enderzombi102.loadercomplex.api.minecraft.block.BlockState;
import com.enderzombi102.loadercomplex.api.minecraft.server.Server;
import com.enderzombi102.loadercomplex.api.minecraft.util.*;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a minecraft world
 */
@ApiStatus.AvailableSince( "0.1.3" )
public interface World {
	void spawn( Entity entity, Vec3d pos );

	/**
	 * Returns true if this is a ClientWorld
	 */
	boolean isClient();

	boolean isHardcore();

	boolean isSunny();

	boolean isRaining();

	boolean isThundering();

	boolean isAir( Vec3i pos );

	boolean isPositionLoaded( Vec3i pos );

	boolean hasBlockEntity( Vec3i pos );

	boolean hasSkyAccess( Vec3i pos );

	boolean canSnowFall( Vec3i pos );

	BlockState getBlockState( Vec3i pos );

	boolean setBlockState( Vec3i pos, BlockState state );

	boolean breakBlock( Vec3i pos, boolean dropItems );

	boolean removeBlock( Vec3i pos );

	@Nullable Server getServer();

	Vec3i getSpawnLocation();

	Difficulty getDifficulty();

	int getRedstonePower( Vec3i pos, Direction direction );

	void playsound( PlayerEntity player, double x, double y, double z, ResourceIdentifier sound, float volume, float pitch );

	/**
	 * Getter for the raw World object
	 */
	Object getObject();
}
