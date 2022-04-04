package com.enderzombi102.loadercomplex.forge12.impl;

import com.enderzombi102.loadercomplex.Utils;
import com.enderzombi102.loadercomplex.api.block.Blockstate;
import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.entity.ItemEntity;
import com.enderzombi102.loadercomplex.api.item.ItemStack;
import com.enderzombi102.loadercomplex.api.utils.FactoryWorld;
import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import com.enderzombi102.loadercomplex.api.utils.Server;
import com.enderzombi102.loadercomplex.api.world.World;
import com.enderzombi102.loadercomplex.forge12.impl.block.ForgeBlockstate;
import com.enderzombi102.loadercomplex.forge12.impl.entity.ForgeEntity;
import com.enderzombi102.loadercomplex.forge12.impl.entity.ForgeItemEntity;
import com.enderzombi102.loadercomplex.forge12.impl.item.ForgeItemStack;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ForgeFactoryWorld implements FactoryWorld {
	@Override
	public ItemStack createStack(ResourceIdentifier type) {
		Item item = ForgeRegistries.ITEMS.getValue( new ResourceLocation( type.getNamespace(), type.getPath() ) );
		if ( item == null )
			throw new IllegalArgumentException(	Utils.format( "\"{}\" is not a valid item!", type ) );
		return new ForgeItemStack( new net.minecraft.item.ItemStack( item ) );
	}

	@Override
	public Entity createEntity(World world, ResourceIdentifier type) {
		EntityEntry entity = ForgeRegistries.ENTITIES.getValue( new ResourceLocation( type.getNamespace(), type.getPath() ) );
		if ( entity == null )
			throw new IllegalArgumentException(	Utils.format( "\"{}\" is not a valid entity!", type ) );
		return new ForgeEntity( entity.newInstance( (net.minecraft.world.World) world.getObject() ) );
	}

	@Override
	public ItemEntity createItemEntity(World world, ItemStack stack) {
		//noinspection EntityConstructor
		return new ForgeItemEntity(
			new EntityItem( (net.minecraft.world.World) world.getObject() ) {{
				setItem( (net.minecraft.item.ItemStack) stack.getStack() );
			}}
		);
	}

	@Override
	public Blockstate createBlockstate(ResourceIdentifier type) {
		Block block = ForgeRegistries.BLOCKS.getValue( new ResourceLocation( type.getNamespace(), type.getPath() ) );
		if ( block == null )
			throw new IllegalArgumentException(	Utils.format( "\"{}\" is not a valid block!", type ) );
		return new ForgeBlockstate( block.getDefaultState() );
	}

	@Override
	public Blockstate airBlockstate() {
		return createBlockstate( new ResourceIdentifier("minecraft", "air") );
	}

	@Override
	public World adaptWorld(Server server, int id) {
		throw new RuntimeException("Not implemented");
	}
}
