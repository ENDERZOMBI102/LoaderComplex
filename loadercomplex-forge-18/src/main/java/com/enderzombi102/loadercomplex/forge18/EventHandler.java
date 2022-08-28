package com.enderzombi102.loadercomplex.forge18;

import com.enderzombi102.loadercomplex.api.event.client.ClientChatEventData;
import com.enderzombi102.loadercomplex.api.event.server.ServerChatEventData;
import com.enderzombi102.loadercomplex.forge18.impl.ForgeRegistry;
import com.enderzombi102.loadercomplex.forge18.impl.entity.ForgePlayer;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.ServerChatEvent;
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

	@SubscribeEvent
	public static void onClientSendChatMessage( ClientChatEvent evt ) {
		ClientChatEventData data = LoaderComplexForge.INSTANCE
			.getLoader()
			.getEventSystem()
			.dispatch(
				"lc.client.chat.send",
				new ClientChatEventData(
					evt.getMessage(),
					new ForgePlayer( MinecraftClient.getInstance().player )
				)
			);
		evt.setCanceled( data.isCancelled() );
		evt.setMessage( data.getMessage() );
	}

	@SubscribeEvent
	public static void onServerReceiveChatMessage( ServerChatEvent evt ) {
		ServerChatEventData data = LoaderComplexForge.INSTANCE
			.getLoader()
			.getEventSystem()
			.dispatch(
				"lc.server.chat.receive",
				new ServerChatEventData(
					Text.Serializer.toJson( evt.getComponent() ),
					new ForgePlayer( evt.getPlayer() )
				)
			);
		evt.setCanceled( data.isCancelled() );
		evt.setComponent( Text.Serializer.fromJson( data.getJsonMessage() ) );
	}

	private static ForgeRegistry getRegistry() {
		return (ForgeRegistry) LoaderComplexForge.INSTANCE.getLoader().getRegistry();
	}
}
