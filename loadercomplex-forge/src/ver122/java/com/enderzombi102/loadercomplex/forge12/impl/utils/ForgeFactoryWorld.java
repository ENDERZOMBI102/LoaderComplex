package com.enderzombi102.loadercomplex.forge12.impl.utils;

import com.enderzombi102.loadercomplex.api.minecraft.util.Position;
import com.enderzombi102.loadercomplex.api.platform.FactoryWorld;
import com.enderzombi102.loadercomplex.forge12.impl.block.ForgeBlockState;
import com.enderzombi102.loadercomplex.forge12.impl.entity.ForgeEntity;
import com.enderzombi102.loadercomplex.forge12.impl.entity.ForgeItemEntity;
import com.enderzombi102.loadercomplex.forge12.impl.item.ForgeItemStack;
import com.enderzombi102.loadercomplex.api.minecraft.block.BlockState;
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity;
import com.enderzombi102.loadercomplex.api.minecraft.entity.ItemEntity;
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack;
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier;
import com.enderzombi102.loadercomplex.api.minecraft.server.Server;
import com.enderzombi102.loadercomplex.api.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ForgeFactoryWorld implements FactoryWorld {
	@Override
	public ItemStack createStack( ResourceIdentifier type ) {
		Item item = ForgeRegistries.ITEMS.getValue( new ResourceLocation( type.getNamespace(), type.getPath() ) );
		if ( item == null ) {
			throw new IllegalArgumentException( String.format( "\"%s\" is not a valid item!", type ) );
		}
		return new ForgeItemStack( new net.minecraft.item.ItemStack( item ) );
	}

	@Override
	public Entity createEntity( World world, ResourceIdentifier type, Position pos ) {
		EntityEntry entity = ForgeRegistries.ENTITIES.getValue( new ResourceLocation( type.getNamespace(), type.getPath() ) );
		if ( entity == null ) {
			throw new IllegalArgumentException( String.format( "\"%s\" is not a valid entity!", type ) );
		}
		net.minecraft.entity.Entity it = entity.newInstance( (net.minecraft.world.World) world.getObject() );
		it.setPositionAndUpdate( pos.x, pos.y, pos.z );
		return new ForgeEntity( it );
	}

	@Override
	public ItemEntity createItemEntity( World world, ItemStack stack, Position pos ) {
		EntityItem it = new EntityItem( (net.minecraft.world.World) world.getObject() );
		it.setItem( (net.minecraft.item.ItemStack) stack.getStack() );
		it.setPositionAndUpdate( pos.x, pos.y, pos.z );

		return new ForgeItemEntity( it );
	}

	@Override
	public BlockState createBlockstate( ResourceIdentifier type ) {
		Block block = ForgeRegistries.BLOCKS.getValue( new ResourceLocation( type.getNamespace(), type.getPath() ) );
		if ( block == null ) {
			throw new IllegalArgumentException( String.format( "\"%s\" is not a valid block!", type ) );
		}
		return new ForgeBlockState( block.getDefaultState() );
	}

	@Override
	public BlockState airBlockstate() {
		return createBlockstate( new ResourceIdentifier( "minecraft", "air" ) );
	}

	@Override
	public World adaptWorld( Server server, int id ) {
		throw new RuntimeException( "Not implemented" );
	}
}
