package com.enderzombi102.loadercomplex.api.utils;

import com.enderzombi102.loadercomplex.api.block.Blockstate;
import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.entity.ItemEntity;
import com.enderzombi102.loadercomplex.api.item.ItemStack;
import com.enderzombi102.loadercomplex.api.world.World;
import org.jetbrains.annotations.ApiStatus;

/**
 * A factory class for various loader-specific objects
 */
@ApiStatus.AvailableSince("0.1.3")
public interface FactoryWorld {
	/**
	 * Creates an {@link ItemStack} of a specified type
	 * @param type type of the {@link ItemStack}
	 * @return the {@link ItemStack} instance
	 */
	ItemStack createStack(ResourceIdentifier type);

	/**
	 * Creates an entity of a specified type
	 * @param world world the spawn the entity in
	 * @param type type of the entity to spawn
	 * @return the spawned entity instance
	 */
	Entity createEntity(World world, ResourceIdentifier type);

	/**
	 * Creates an item entity in the world
	 * @param world world to spawn the entity in
	 * @param stack stack the entity will holds
	 * @return an {@link ItemEntity} object
	 */
	ItemEntity createItemEntity(World world, ItemStack stack);

	/**
	 * Creates a {@link Blockstate} of a specified type
	 * @param type type of the {@link Blockstate}
	 */
	Blockstate createBlockstate(ResourceIdentifier type);

	/**
	 * Returns an Air blockstate
	 */
	Blockstate airBlockstate();

	/**
	 * Adapts a world in a server to a {@link World} object.
	 * @param server server the world is in
	 * @param id id of the world
	 */
	@ApiStatus.AvailableSince("0.1.4")
	World adaptWorld(Server server, int id);
}
