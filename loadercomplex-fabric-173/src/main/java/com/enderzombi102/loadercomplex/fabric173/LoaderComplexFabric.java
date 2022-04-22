package com.enderzombi102.loadercomplex.fabric173;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;

public class LoaderComplexFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		LogManager.getLogger("Minecraft").error("Hello World!");
	}
}
