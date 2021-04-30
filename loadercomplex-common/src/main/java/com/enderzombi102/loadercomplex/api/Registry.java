package com.enderzombi102.loadercomplex.api;


import com.enderzombi102.loadercomplex.api.block.Block;
import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.item.Item;
import com.enderzombi102.loadercomplex.api.utils.RegistryKey;
import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import org.jetbrains.annotations.Nullable;

import javax.naming.OperationNotSupportedException;

public interface Registry {

	void register(Block block, ResourceIdentifier identifier);
	void register(Block block, boolean registerItem, ResourceIdentifier identifier);
	void register(Item Item, ResourceIdentifier identifier);
	void register(Entity entity, ResourceIdentifier identifier);

	boolean isRegistered(RegistryKey key, String identifier ) throws OperationNotSupportedException;
	boolean isRegistered(RegistryKey key,ResourceIdentifier identifier ) throws OperationNotSupportedException;

	@Nullable Object getVanillaRegistry(RegistryKey key);


}
