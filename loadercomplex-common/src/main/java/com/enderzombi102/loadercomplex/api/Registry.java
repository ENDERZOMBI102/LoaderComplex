package com.enderzombi102.loadercomplex.api;


import com.enderzombi102.loadercomplex.api.block.Block;
import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.item.Item;
import com.enderzombi102.loadercomplex.api.utils.RegistryKey;
import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.naming.OperationNotSupportedException;

/**
 * The Registry is the main way of registering stuff in the game.
 * It also provides a way to get the underlying game registry and check if a resource is registered.
 *
 * This may be used to register:
 *  - Items
 *  - Blocks
 *  - ItemGroups
 */
public interface Registry {

	void register( @NotNull Block block, @NotNull ResourceIdentifier identifier );
	void register( @NotNull Block block, @NotNull ResourceIdentifier identifier, @Nullable ResourceIdentifier itemGroup );
	void register( @NotNull Item Item, @NotNull ResourceIdentifier identifier );
	void register( @NotNull Entity entity, @NotNull ResourceIdentifier identifier );
	ResourceIdentifier registerItemGroup( @Nullable String name, @NotNull ResourceIdentifier icon );

	boolean isRegistered( @NotNull RegistryKey key, @NotNull String identifier ) throws OperationNotSupportedException;
	boolean isRegistered( @NotNull RegistryKey key, @NotNull ResourceIdentifier identifier ) throws OperationNotSupportedException;

	@Nullable Object getVanillaRegistry( @NotNull RegistryKey key );
}
