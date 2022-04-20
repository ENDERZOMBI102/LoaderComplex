package com.enderzombi102.loadercomplex.forge18;

import com.enderzombi102.loadercomplex.LoaderComplex;
import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.forge18.impl.ForgeLoader;
import com.enderzombi102.loadercomplex.modloader.AddonContainer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("loadercomplex")
public class LoaderComplexForge extends LoaderComplex {
	public static LoaderComplexForge INSTANCE;
    public static final Logger LOGGER = LogManager.getLogger();


	public LoaderComplexForge() {
		INSTANCE = this;
		this.loader = new ForgeLoader();
		this.resourceHelper = FMLLoader.getDist().isClient() ? this::registerPack : container -> { };
		// register event handlers
		MinecraftForge.EVENT_BUS.register(RegistryHandler.class);
		// no initAddons() call because frozen registries, that call will be done from the RegistryHandler
	}

	private void registerPack( AddonContainer container ) {

	}

	public Loader getLoader() {
		return this.loader;
	}

	@Override
	public void initAddons() {
		super.initAddons();
	}
}
