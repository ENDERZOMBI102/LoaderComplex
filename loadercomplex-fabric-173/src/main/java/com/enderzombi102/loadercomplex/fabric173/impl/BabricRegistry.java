package com.enderzombi102.loadercomplex.fabric173.impl;

import com.enderzombi102.loadercomplex.api.Registry;
import com.enderzombi102.loadercomplex.api.block.Block;
import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.item.Item;
import com.enderzombi102.loadercomplex.api.utils.RegistryKey;
import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.naming.OperationNotSupportedException;

public class BabricRegistry implements Registry {
	@Override
	public void register(@NotNull Block block, @NotNull ResourceIdentifier identifier) {

	}

	@Override
	public void register(@NotNull Block block, @NotNull ResourceIdentifier identifier, @Nullable ResourceIdentifier itemGroup) {

	}

	@Override
	public void register(@NotNull Item Item, @NotNull ResourceIdentifier identifier) {

	}

	@Override
	public void register(@NotNull Entity entity, @NotNull ResourceIdentifier identifier) {

	}

	@Override
	public ResourceIdentifier registerItemGroup(@Nullable String name, @NotNull ResourceIdentifier icon) {
		return null;
	}

	@Override
	public boolean isRegistered(@NotNull RegistryKey key, @NotNull String identifier) throws OperationNotSupportedException {
		return false;
	}

	@Override
	public boolean isRegistered(@NotNull RegistryKey key, @NotNull ResourceIdentifier identifier) throws OperationNotSupportedException {
		return false;
	}

	@Override
	public @Nullable Object getVanillaRegistry(@NotNull RegistryKey key) {
		return null;
	}
}
