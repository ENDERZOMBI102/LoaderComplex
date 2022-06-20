package com.enderzombi102.loadercomplex.fabric173;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.event.entity.player.PlayerEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;

public class EventHandler {

	@EventListener
	public void onBlockRegister( BlockRegistryEvent evt ) {
		LoaderComplexFabric.LOGGER.error( "Blocks!" );
	}

	@EventListener
	public void onItemRegister( ItemRegistryEvent evt ) {
		LoaderComplexFabric.LOGGER.error( "Items!" );
	}

	@EventListener
	public void onPlayerHandlerRegister( PlayerEvent.HandlerRegister evt ) {
		LoaderComplexFabric.LOGGER.error( "Player handlers!" );
	}

	@EventListener
	public void onTextureRegister( TextureRegisterEvent evt ) {
		LoaderComplexFabric.LOGGER.error( "Textures!" );
	}
}
