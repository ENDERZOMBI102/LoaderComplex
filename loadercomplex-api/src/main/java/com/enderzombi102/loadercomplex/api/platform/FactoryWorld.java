package com.enderzombi102.loadercomplex.api.platform;

import com.enderzombi102.loadercomplex.api.minecraft.util.Position;
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier;
import com.enderzombi102.loadercomplex.api.minecraft.util.Server;
import com.enderzombi102.loadercomplex.api.minecraft.command.CommandManager;
import com.enderzombi102.loadercomplex.api.minecraft.keybind.KeybindManager;
import com.enderzombi102.loadercomplex.api.minecraft.block.Blockstate;
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity;
import com.enderzombi102.loadercomplex.api.minecraft.entity.ItemEntity;
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack;
import com.enderzombi102.loadercomplex.api.minecraft.network.NetworkManager;
import com.enderzombi102.loadercomplex.api.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus.AvailableSince;

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
	Entity createEntity( World world, ResourceIdentifier type, Position pos );

	/**
	 * Creates an item entity in the world
	 *
	 * @param world world to spawn the entity in
	 * @param stack stack the entity will holds
	 * @param pos   where to spawn the entity
	 * @return an {@link ItemEntity} object
	 */
	ItemEntity createItemEntity( World world, ItemStack stack, Position pos );

	/**
	 * Creates a {@link Blockstate} of a specified type
	 *
	 * @param type type of the {@link Blockstate}
	 */
	Blockstate createBlockstate( ResourceIdentifier type );

	/**
	 * Returns an Air blockstate
	 */
	Blockstate airBlockstate();

	/**
	 * Adapts a world in a server to a {@link World} object.
	 *
	 * @param server server the world is in
	 * @param id     id of the world
	 */
	@AvailableSince( "0.1.4" )
	World adaptWorld( Server server, int id );

	/**
	 * Getter for the loader's {@link KeybindManager}, an object capable of:<br>
	 * - Registering keybindings<br>
	 * - Querying registered keybindings<br>
	 */
	@AvailableSince( "0.2.0" )
	default KeybindManager getKeybindManager() {
		throw new IllegalStateException( "This function has not been implemented in this impl!" );
	}

	/**
	 * Getter for the loader's {@link NetworkManager}, an object capable of creating network channels.<br>
	 * Channels can:<br>
	 * - Send data between client server ( full-duplex )<br>
	 * - Nothing else, what do you expect??
	 */
	@AvailableSince( "0.2.0" )
	default NetworkManager getNetworkManager() {
		throw new IllegalStateException( "This function has not been implemented in this impl!" );
	}

	/**
	 * Getter for the loader's {@link CommandManager}, an object capable of creating commands.
	 */
	@AvailableSince( "0.2.0" )
	default CommandManager getCommandManager() {
		throw new IllegalStateException( "This function has not been implemented in this impl!" );
	}
}
