package com.enderzombi102.loadercomplex.fabric17.impl;

import com.enderzombi102.loadercomplex.Utils;
import com.enderzombi102.loadercomplex.api.block.Blockstate;
import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.entity.ItemEntity;
import com.enderzombi102.loadercomplex.api.item.ItemStack;
import com.enderzombi102.loadercomplex.api.utils.FactoryWorld;
import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import com.enderzombi102.loadercomplex.api.utils.Server;
import com.enderzombi102.loadercomplex.api.world.World;
import com.enderzombi102.loadercomplex.fabric17.impl.block.FabricBlockstate;
import com.enderzombi102.loadercomplex.fabric17.impl.entity.FabricEntity;
import com.enderzombi102.loadercomplex.fabric17.impl.entity.FabricItemEntity;
import com.enderzombi102.loadercomplex.fabric17.impl.item.FabricItemStack;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FabricFactoryWorld implements FactoryWorld {
	@Override
	public ItemStack createStack(ResourceIdentifier type) {
		Item item = Registry.ITEM.get( new Identifier( type.getNamespace(), type.getPath() ) );
		//noinspection ConstantConditions
		if ( item == null )
			throw new IllegalArgumentException( Utils.format( "Item \"{}\" does not exist!", type ) );
		return new FabricItemStack( new net.minecraft.item.ItemStack( item ) );
	}

	@Override
	public Entity createEntity(World world, ResourceIdentifier type) {
		var entityType = Registry.ENTITY_TYPE.get( new Identifier( type.getNamespace(), type.getPath() ) );
		//noinspection ConstantConditions
		if ( entityType == null )
			throw new IllegalArgumentException( Utils.format( "Entity \"{}\" does not exist!", type ) );
		return new FabricEntity( entityType.create( (net.minecraft.world.World) world.getObject() ) );
	}

	@Override
	public ItemEntity createItemEntity(World world, ItemStack stack) {
		var entity = EntityType.ITEM.create( (net.minecraft.world.World) world.getObject() );
		if ( entity == null )
			throw new IllegalArgumentException( "ItemEntity failed to spawn!" );
		entity.setStack( (net.minecraft.item.ItemStack) stack.getStack() );
		return new FabricItemEntity( entity );
	}

	@Override
	public Blockstate createBlockstate(ResourceIdentifier type) {
		Block block = Registry.BLOCK.get( new Identifier( type.getNamespace(), type.getPath() ) );
		//noinspection ConstantConditions
		if ( block == null )
			throw new IllegalArgumentException( Utils.format( "Block \"{}\" does not exist!", type ) );
		return new FabricBlockstate( block.getDefaultState() );
	}

	@Override
	public Blockstate airBlockstate() {
		return createBlockstate( new ResourceIdentifier( "minecraft", "air" ) );
	}

	@Override
	public World adaptWorld(Server server, int id) {
		return null;
	}
}
