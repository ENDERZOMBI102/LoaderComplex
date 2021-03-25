package com.enderzombi102.loadercomplex.sponge;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;

@Plugin(
		id = "loadercomplex-sponge",
		version = "1.0.0",
		name = "Loader Complex",
		description = "A very simple mod that aims to be compatible with almost every mod/plugin loader",
		authors = {
				"ENDERZOMBI102"
		}
)
public class LoaderComplexSponge {

	@Inject
	private Logger logger;

	@Listener
	public void onServerStart(GameStartedServerEvent event) {
        logger.error("Works!");
    }
}
