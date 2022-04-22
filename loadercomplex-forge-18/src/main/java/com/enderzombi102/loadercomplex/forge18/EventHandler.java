package com.enderzombi102.loadercomplex.forge18;

import com.enderzombi102.loadercomplex.forge18.impl.ForgeRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ResourceType;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.enderzombi102.loadercomplex.forge18.LoaderComplexForge.LOGGER;

@Mod.EventBusSubscriber( bus = Mod.EventBusSubscriber.Bus.MOD )
public class EventHandler {
	@SubscribeEvent
	public static void onBlocksRegistry( final RegistryEvent.Register<Block> evt ) {
		// init LoaderComplex
		LoaderComplexForge.INSTANCE.initAddons();
		LOGGER.info( "Registering {} blocks!", getRegistry().blocks.size() );
		getRegistry().blocks.forEach( evt.getRegistry()::register );
	}

	@SubscribeEvent
	public static void onItemsRegistry( final RegistryEvent.Register<Item> evt ) {
		// init LoaderComplex
		LoaderComplexForge.INSTANCE.initAddons();
		LOGGER.info( "Registering {} items!", getRegistry().items.size() );
		getRegistry().items.forEach( evt.getRegistry()::register );
	}

	@SubscribeEvent
	public static void onModelRegistry( final ModelRegistryEvent evt ) {

	}

	@SubscribeEvent
	public static void onRegisterResourcePacks( final AddPackFindersEvent evt ) {
		if ( evt.getPackType() == ResourceType.CLIENT_RESOURCES ) {
			evt.addRepositorySource( (adder, factory) -> adder.accept(
				ResourcePackProfile.of(
					"LoaderComplex Resources",
					true,
					() -> new ForgeResourcePack( LoaderComplexForge.INSTANCE.getAddonLoader().getAddons() ),
					factory,
					ResourcePackProfile.InsertionPosition.BOTTOM,
					ResourcePackSource.PACK_SOURCE_BUILTIN
				)
			));
		}
	}

	private static ForgeRegistry getRegistry() {
		return (ForgeRegistry) LoaderComplexForge.INSTANCE.getLoader().getRegistry();
	}
}
