package com.enderzombi102.loadercomplex.abstraction;


import com.enderzombi102.loadercomplex.abstraction.block.Block;
import com.enderzombi102.loadercomplex.abstraction.entity.Entity;
import com.enderzombi102.loadercomplex.abstraction.item.Item;
import com.enderzombi102.loadercomplex.abstraction.utils.RegistryKey;
import com.enderzombi102.loadercomplex.abstraction.utils.ResourceIdentifier;
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
