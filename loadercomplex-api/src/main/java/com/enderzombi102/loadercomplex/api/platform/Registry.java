package com.enderzombi102.loadercomplex.api.platform;


import com.enderzombi102.loadercomplex.api.minecraft.block.Block;
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity;
import com.enderzombi102.loadercomplex.api.minecraft.item.Item;
import com.enderzombi102.loadercomplex.api.minecraft.util.RegistryKey;
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.naming.OperationNotSupportedException;

/**
 * The Registry is the main way of registering stuff in the game.<br/>
 * It also provides a way to get the underlying game registry and check if a resource is registered.<br/>
 * <br/>
 * This may be used to register:<br/>
 * - Items<br/>
 * - Blocks<br/>
 * - ItemGroups<br/>
 */
public interface Registry {
	void register( @NotNull Block block, @NotNull ResourceIdentifier identifier );

	void register( @NotNull Block block, @NotNull ResourceIdentifier identifier, @Nullable ResourceIdentifier creativeTab );

	void register( @NotNull Item Item, @NotNull ResourceIdentifier identifier );

	void register( @NotNull Entity entity, @NotNull ResourceIdentifier identifier );

	ResourceIdentifier registerCreativeTab( @Nullable String name, @NotNull ResourceIdentifier icon );

	boolean isRegistered( @NotNull RegistryKey key, @NotNull String identifier ) throws OperationNotSupportedException;

	boolean isRegistered( @NotNull RegistryKey key, @NotNull ResourceIdentifier identifier ) throws OperationNotSupportedException;

	@Nullable Object getVanillaRegistry( @NotNull RegistryKey key );
}
