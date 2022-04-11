package com.enderzombi102.loadercomplex.forge18;

import com.enderzombi102.loadercomplex.LoaderComplex;
import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.forge18.impl.ForgeLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("loadercomplex")
public class LoaderComplexForge extends LoaderComplex {
	public static LoaderComplexForge INSTANCE;
    private static final Logger LOGGER = LogManager.getLogger();

    public LoaderComplexForge() {
		// register event handlers
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onPreInit);
		MinecraftForge.EVENT_BUS.register(RegistryHandler.class);
		INSTANCE = this;
		this.loader = new ForgeLoader();
		this.resourceHelper = container -> { };

		// init LoaderComplex
		this.initAddons();
    }

	@SubscribeEvent
    private void onPreInit(final FMLCommonSetupEvent event) {

    }

	public Loader getLoader() {
		return this.loader;
	}
}
