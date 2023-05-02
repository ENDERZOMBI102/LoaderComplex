package com.enderzombi102.loadercomplex.forge18;

import com.enderzombi102.loadercomplex.impl.LoaderComplex;
import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.forge18.impl.ForgeLoader;
import net.minecraftforge.common.MinecraftForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoaderComplexForge extends LoaderComplex {
	// FIXME: Fix crash on start
	public static final Logger LOGGER = LoggerFactory.getLogger("LoaderComplex | Forge");

	public LoaderComplexForge() {
		super( new ForgeLoader() );
		// register event handlers
		MinecraftForge.EVENT_BUS.register(EventHandler.class);
		// no initAddons() call because frozen registries, that call will be done from the RegistryHandler
	}
}
