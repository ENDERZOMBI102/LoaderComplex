package com.enderzombi102.loadercomplex.forge122;

import com.enderzombi102.loadercomplex.forge122.impl.platform.ForgeRegistry;
import com.enderzombi102.loadercomplex.impl.LoaderComplex;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


/**
 * This listens to registry events, to allow creation of mod blocks and items at the proper time.
 */
@Mod.EventBusSubscriber
public class RegistryHandler {
	/**
	 * Listen for the register event for creating custom items
	 */
	@SubscribeEvent
	public static void addItems( RegistryEvent.Register<Item> evt ) {
		((ForgeRegistry) LoaderComplex.get().getContext().getRegistry()).onItemRegistry( evt );
	}

	/**
	 * Listen for the register event for creating custom blocks
	 */
	@SubscribeEvent
	public static void addBlocks( RegistryEvent.Register<Block> evt ) {
		((ForgeRegistry) LoaderComplex.get().getContext().getRegistry()).onBlockRegistry( evt );
	}

	@SubscribeEvent
	public static void addModels( ModelRegistryEvent evt ) {
		((ForgeRegistry) LoaderComplex.get().getContext().getRegistry()).onModelRegistry();
	}
}
