package com.enderzombi102.loadercomplex.fabric12.impl;

import com.enderzombi102.loadercomplex.api.minecraft.command.CommandManager;
import com.enderzombi102.loadercomplex.api.minecraft.keybind.KeybindManager;
import com.enderzombi102.loadercomplex.api.minecraft.network.NetworkManager;
import com.enderzombi102.loadercomplex.api.minecraft.util.Position;
import com.enderzombi102.loadercomplex.api.platform.FactoryWorld;
import com.enderzombi102.loadercomplex.fabric12.impl.block.FabricBlockstate;
import com.enderzombi102.loadercomplex.fabric12.impl.entity.FabricEntity;
import com.enderzombi102.loadercomplex.fabric12.impl.entity.FabricItemEntity;
import com.enderzombi102.loadercomplex.fabric12.impl.item.FabricItemStack;
import com.enderzombi102.loadercomplex.api.minecraft.block.Blockstate;
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity;
import com.enderzombi102.loadercomplex.api.minecraft.entity.ItemEntity;
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack;
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier;
import com.enderzombi102.loadercomplex.api.minecraft.util.Server;
import com.enderzombi102.loadercomplex.api.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.resource.Identifier;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.InvocationTargetException;

public class FabricFactoryWorld implements FactoryWorld {
	@Override
	public ItemStack createStack( ResourceIdentifier type ) {
		Item item = Item.REGISTRY.get( new Identifier( type.getNamespace(), type.getPath() ) );
		if ( item == null ) {
			throw new IllegalArgumentException( String.format( "Item \"%s\" does not exist!", type ) );
		}
		return new FabricItemStack( new net.minecraft.item.ItemStack( item ) );
	}

	@Override
	public Entity createEntity( World world, ResourceIdentifier type, Position pos ) {
		Class<? extends net.minecraft.entity.Entity> entity = null; // EntityType.getEntityType( type.toString() );
		if ( entity == null )
			throw new IllegalArgumentException( String.format( "Entity \"%s\" does not exist!", type ) );
		try {
			//noinspection RedundantCast
			return new FabricEntity(
				entity
					.getDeclaredConstructor( net.minecraft.world.World.class )
					.newInstance( (net.minecraft.world.World) world.getObject() )
			);
		} catch ( InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e ) {
			throw new RuntimeException( e );
		}
	}

	@Override
	public ItemEntity createItemEntity( World world, ItemStack stack, Position pos ) {
		net.minecraft.entity.ItemEntity entity = new net.minecraft.entity.ItemEntity(
			(net.minecraft.world.World) world.getObject()
		);
		entity.setItem( (net.minecraft.item.ItemStack) stack.getStack() );
		return new FabricItemEntity( entity );
	}

	@Override
	public Blockstate createBlockstate( ResourceIdentifier type ) {
		Block block = Block.REGISTRY.get( new Identifier( type.getNamespace(), type.getPath() ) );
		//noinspection ConstantConditions
		if ( block == null )
			throw new IllegalArgumentException( String.format( "Block \"%s\" does not exist!", type ) );
		return new FabricBlockstate( block.defaultState() );
	}

	@Override
	public Blockstate airBlockstate() {
		return createBlockstate( new ResourceIdentifier( "minecraft", "air" ) );
	}

	@Override
	public World adaptWorld( Server server, int id ) {
		return null;
	}

	@Override
	public KeybindManager getKeybindManager() {
		return FactoryWorld.super.getKeybindManager();
	}

	@Override
	public NetworkManager getNetworkManager() {
		return FactoryWorld.super.getNetworkManager();
	}

	@Override
	public CommandManager getCommandManager() {
		return FactoryWorld.super.getCommandManager();
	}
}
