package com.enderzombi102.loadercomplex.sponge;

import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Client;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.plugin.builtin.jvm.Plugin;

@Plugin( value = "loadercomplex-sponge" )
public class LoaderComplexSponge {

	@Inject
	private Logger logger;

	@Listener
	public void onServerStart( StartedEngineEvent<Server> event ) {
        logger.error("Server start");
    }

	@Listener
	public void onClientStart( StartedEngineEvent<Client> event ) {
		logger.error("Client start");
	}
}
