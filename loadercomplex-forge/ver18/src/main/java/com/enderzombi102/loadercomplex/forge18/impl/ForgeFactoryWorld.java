package com.enderzombi102.loadercomplex.forge18.impl;

import com.enderzombi102.loadercomplex.minecraft.block.Blockstate;
import com.enderzombi102.loadercomplex.minecraft.entity.Entity;
import com.enderzombi102.loadercomplex.minecraft.entity.ItemEntity;
import com.enderzombi102.loadercomplex.minecraft.item.ItemStack;
import com.enderzombi102.loadercomplex.minecraft.util.FactoryWorld;
import com.enderzombi102.loadercomplex.minecraft.util.ResourceIdentifier;
import com.enderzombi102.loadercomplex.minecraft.util.Server;
import com.enderzombi102.loadercomplex.minecraft.world.World;
import com.enderzombi102.loadercomplex.forge18.impl.block.ForgeBlockstate;
import com.enderzombi102.loadercomplex.forge18.impl.entity.ForgeEntity;
import com.enderzombi102.loadercomplex.forge18.impl.entity.ForgeItemEntity;
import com.enderzombi102.loadercomplex.forge18.impl.item.ForgeItemStack;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.RegistryManager;

public class ForgeFactoryWorld implements FactoryWorld {
	@Override
	public ItemStack createStack(ResourceIdentifier type) {
		Item item = RegistryManager.ACTIVE.getRegistry( Registry.ITEM_KEY )
				.getValue( new Identifier( type.getNamespace(), type.getPath() ) );
		if ( item == null )
			throw new IllegalArgumentException( String.format( "Item \"%s\" does not exist!", type ) );
		return new ForgeItemStack( new net.minecraft.item.ItemStack( item ) );
	}

	@Override
	public Entity createEntity(World world, ResourceIdentifier type) {
		var entityType = RegistryManager.ACTIVE.getRegistry( Registry.ENTITY_TYPE_KEY )
				.getValue( new Identifier( type.getNamespace(), type.getPath() ) );
		if ( entityType == null )
			throw new IllegalArgumentException( String.format( "Entity \"%s\" does not exist!", type ) );
		return new ForgeEntity( entityType.create( (net.minecraft.world.World) world.getObject() ) );
	}

	@Override
	public ItemEntity createItemEntity(World world, ItemStack stack) {
		var entity = EntityType.ITEM.create( (net.minecraft.world.World) world.getObject() );
		if ( entity == null )
			throw new IllegalArgumentException( "ItemEntity failed to spawn!" );
		entity.setStack( (net.minecraft.item.ItemStack) stack.getStack() );
		return new ForgeItemEntity( entity );
	}

	@Override
	public Blockstate createBlockstate(ResourceIdentifier type) {
		Block block = RegistryManager.ACTIVE.getRegistry( Registry.BLOCK_KEY )
				.getValue( new Identifier( type.getNamespace(), type.getPath() ) );
		if ( block == null )
			throw new IllegalArgumentException( String.format( "Block \"%s\" does not exist!", type ) );
		return new ForgeBlockstate( block.getDefaultState() );
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
