package com.enderzombi102.loadercomplex.bukkit.impl;

import com.enderzombi102.loadercomplex.api.Registry;
import com.enderzombi102.loadercomplex.api.block.Block;
import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.item.Item;
import com.enderzombi102.loadercomplex.api.utils.RegistryKey;
import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import com.enderzombi102.loadercomplex.bukkit.Utilities;
import org.jetbrains.annotations.Nullable;

import javax.naming.OperationNotSupportedException;

public class BukkitRegistry implements Registry {
	@Override
	public void register(Block block, ResourceIdentifier identifier) {

	}

	@Override
	public void register(Block block, boolean registerItem, ResourceIdentifier identifier) {

	}

	@Override
	public void register(Item Item, ResourceIdentifier identifier) {

	}

	@Override
	public void register(Entity entity, ResourceIdentifier identifier) {

	}

	@Override
	public boolean isRegistered(RegistryKey key, String identifier) throws OperationNotSupportedException {
		 return this.isRegistered( key, new ResourceIdentifier( identifier.split(":")[1], identifier.split(":")[0] ) );
	}

	@Override
	public boolean isRegistered(RegistryKey key, ResourceIdentifier identifier) throws OperationNotSupportedException {
		return identifier.getNamespace().equals("minecraft") && Utilities.getMaterialFor( identifier ) != null;
	}

	@Override
	public @Nullable Object getVanillaRegistry(RegistryKey key) {
		return null;
	}
}
