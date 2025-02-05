package com.enderzombi102.loadercomplex.api.platform;

import com.enderzombi102.loadercomplex.api.math.Vec3d;
import com.enderzombi102.loadercomplex.api.minecraft.block.BlockState;
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity;
import com.enderzombi102.loadercomplex.api.minecraft.entity.ItemEntity;
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack;
import com.enderzombi102.loadercomplex.api.minecraft.server.Server;
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier;
import com.enderzombi102.loadercomplex.api.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus.AvailableSince;
import org.jetbrains.annotations.Nullable;

/**
 * A factory class for various loader-specific objects
 */
@AvailableSince( "0.1.3" )
public interface FactoryWorld {
	/**
	 * Creates an {@link ItemStack} of a specified type
	 *
	 * @param type type of the {@link ItemStack}
	 * @return the {@link ItemStack} instance
	 */
	ItemStack createStack( ResourceIdentifier type );

	/**
	 * Creates an entity of a specified type
	 *
	 * @param world world the spawn the entity in
	 * @param type  type of the entity to spawn
	 * @param pos   where to span the entity
	 * @return the spawned entity instance
	 */
	@Nullable Entity createEntity( World world, ResourceIdentifier type, Vec3d pos );

	/**
	 * Creates an item entity in the world
	 *
	 * @param world world to spawn the entity in
	 * @param stack stack the entity will holds
	 * @param pos   where to spawn the entity
	 * @return an {@link ItemEntity} object
	 */
	@Nullable ItemEntity createItemEntity( World world, ItemStack stack, Vec3d pos );

	/**
	 * Creates a {@link BlockState} of a specified type
	 *
	 * @param type type of the {@link BlockState}
	 */
	BlockState createBlockstate( ResourceIdentifier type );

	/**
	 * Returns an Air blockstate
	 */
	BlockState airBlockstate();

	/**
	 * Adapts a world in a server to a {@link World} object.
	 *
	 * @param server server the world is in
	 * @param id     id of the world
	 */
	@AvailableSince( "0.1.4" )
	World adaptWorld( Server server, int id );
}
