package com.enderzombi102.loadercomplex.quilt.impl;

import com.enderzombi102.loadercomplex.api.minecraft.block.Blockstate;
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity;
import com.enderzombi102.loadercomplex.api.minecraft.entity.ItemEntity;
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack;
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier;
import com.enderzombi102.loadercomplex.api.minecraft.util.Server;
import com.enderzombi102.loadercomplex.api.minecraft.world.World;
import com.enderzombi102.loadercomplex.api.platform.FactoryWorld;
import com.enderzombi102.loadercomplex.quilt.impl.block.QuiltBlockstate;
import com.enderzombi102.loadercomplex.quilt.impl.entity.QuiltEntity;
import com.enderzombi102.loadercomplex.quilt.impl.entity.QuiltItemEntity;
import com.enderzombi102.loadercomplex.quilt.impl.item.QuiltItemStack;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class QuiltFactoryWorld implements FactoryWorld {
	@Override
	public ItemStack createStack( ResourceIdentifier type ) {
		Item item = Registry.ITEM.get( new Identifier( type.getNamespace(), type.getPath() ) );
		//noinspection ConstantConditions
		if ( item == null )
			throw new IllegalArgumentException( String.format( "Item \"%s\" does not exist!", type ) );
		return new QuiltItemStack( new net.minecraft.item.ItemStack( item ) );
	}

	@Override
	public Entity createEntity( World world, ResourceIdentifier type ) {
		var entityType = Registry.ENTITY_TYPE.get( new Identifier( type.getNamespace(), type.getPath() ) );
		//noinspection ConstantConditions
		if ( entityType == null )
			throw new IllegalArgumentException( String.format( "Entity \"%s\" does not exist!", type ) );
		return new QuiltEntity( entityType.create( (net.minecraft.world.World) world.getObject() ) );
	}

	@Override
	public ItemEntity createItemEntity( World world, ItemStack stack ) {
		var entity = EntityType.ITEM.create( (net.minecraft.world.World) world.getObject() );
		if ( entity == null )
			throw new IllegalArgumentException( "ItemEntity failed to spawn!" );
		entity.setStack( (net.minecraft.item.ItemStack) stack.getStack() );
		return new QuiltItemEntity( entity );
	}

	@Override
	public Blockstate createBlockstate( ResourceIdentifier type ) {
		Block block = Registry.BLOCK.get( new Identifier( type.getNamespace(), type.getPath() ) );
		//noinspection ConstantConditions
		if ( block == null )
			throw new IllegalArgumentException( String.format( "Block \"%s\" does not exist!", type ) );
		return new QuiltBlockstate( block.getDefaultState() );
	}

	@Override
	public Blockstate airBlockstate() {
		return createBlockstate( new ResourceIdentifier( "minecraft", "air" ) );
	}

	@Override
	public World adaptWorld( Server server, int id ) {
		return null;
	}
}
