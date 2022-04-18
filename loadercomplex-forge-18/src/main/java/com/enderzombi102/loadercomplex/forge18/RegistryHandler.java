package com.enderzombi102.loadercomplex.forge18;

import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber( bus = Mod.EventBusSubscriber.Bus.MOD )
public class RegistryHandler {
	@SubscribeEvent
	public static void onBlocksRegistry( final RegistryEvent.Register<Block> blockRegistryEvent ) {

	}

	@SubscribeEvent
	public static void onItemsRegistry( final RegistryEvent.Register<Item> itemRegistryEvent ) {

	}
}
