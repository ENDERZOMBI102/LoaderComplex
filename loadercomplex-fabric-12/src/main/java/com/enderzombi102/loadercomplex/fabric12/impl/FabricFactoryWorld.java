package com.enderzombi102.loadercomplex.fabric12.impl;

import com.enderzombi102.loadercomplex.fabric12.impl.block.FabricBlockstate;
import com.enderzombi102.loadercomplex.fabric12.impl.entity.FabricEntity;
import com.enderzombi102.loadercomplex.fabric12.impl.entity.FabricItemEntity;
import com.enderzombi102.loadercomplex.fabric12.impl.item.FabricItemStack;
import com.enderzombi102.loadercomplex.Utils;
import com.enderzombi102.loadercomplex.api.block.Blockstate;
import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.entity.ItemEntity;
import com.enderzombi102.loadercomplex.api.item.ItemStack;
import com.enderzombi102.loadercomplex.api.utils.FactoryWorld;
import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import com.enderzombi102.loadercomplex.api.utils.Server;
import com.enderzombi102.loadercomplex.api.world.World;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.lang.reflect.InvocationTargetException;

public class FabricFactoryWorld implements FactoryWorld {
	@Override
	public ItemStack createStack(ResourceIdentifier type) {
		Item item = Item.REGISTRY.get( new Identifier( type.getNamespace(), type.getPath() ) );
		if ( item == null )
			throw new IllegalArgumentException( Utils.format( "Item \"{}\" does not exist!", type ) );
		return new FabricItemStack( new net.minecraft.item.ItemStack( item ) );
	}

	@Override
	public Entity createEntity(World world, ResourceIdentifier type) {
		Class<? extends net.minecraft.entity.Entity> entity = EntityType.getEntityType( type.toString() );
		if ( entity == null )
			throw new IllegalArgumentException( Utils.format( "Entity \"{}\" does not exist!", type ) );
		try {
			//noinspection RedundantCast
			return new FabricEntity(
				entity
						.getDeclaredConstructor(net.minecraft.world.World.class)
						.newInstance( (net.minecraft.world.World) world.getObject() )
			);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException( e );
		}
	}

	@Override
	public ItemEntity createItemEntity(World world, ItemStack stack) {
		net.minecraft.entity.ItemEntity entity = new net.minecraft.entity.ItemEntity(
			(net.minecraft.world.World) world.getObject()
		);
		entity.setItemStack( (net.minecraft.item.ItemStack) stack.getStack() );
		return new FabricItemEntity( entity );
	}

	@Override
	public Blockstate createBlockstate(ResourceIdentifier type) {
		Block block = Block.REGISTRY.get( new Identifier( type.getNamespace(), type.getPath() ) );
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
