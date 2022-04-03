package com.enderzombi102.loadercomplex.forge12;

import com.enderzombi102.loadercomplex.forge12.impl.ForgeRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * This is a special class that listens to registry events, to allow creation of mod blocks and items at the proper time.
 */
@Mod.EventBusSubscriber
public class RegistryHandler {
	/**
	 * Listen for the register event for creating custom items
	 */
	@SubscribeEvent
	public static void addItems(RegistryEvent.Register<Item> evt) {
		/*
		 event.getRegistry().register(new ItemBlock(Blocks.myBlock).setRegistryName(MOD_ID, "myBlock"));
		 event.getRegistry().register(new MySpecialItem().setRegistryName(MOD_ID, "mySpecialItem"));
		*/
		( (ForgeRegistry) LoaderComplexForge.INSTANCE.getLoader().getRegistry() ).onItemRegistry(evt);
	}

	/**
	 * Listen for the register event for creating custom blocks
	 */
	@SubscribeEvent
	public static void addBlocks(RegistryEvent.Register<Block> evt) {
	   /*
		 event.getRegistry().register(new MySpecialBlock().setRegistryName(MOD_ID, "mySpecialBlock"));
		*/
		( (ForgeRegistry) LoaderComplexForge.INSTANCE.getLoader().getRegistry() ).onBlockRegistry(evt);
	}
}
