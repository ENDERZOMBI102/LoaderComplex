package com.enderzombi102.loadercomplex.forge18;

import com.enderzombi102.loadercomplex.impl.LoaderComplex;
import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.forge18.impl.ForgeLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("loadercomplex")
public class LoaderComplexForge extends LoaderComplex {
	// FIXME: Fix crash on start
	public static final Logger LOGGER = LogManager.getLogger("LoaderComplex | Forge");
	public static LoaderComplexForge INSTANCE;

	public LoaderComplexForge() {
		INSTANCE = this;
		this.loader = new ForgeLoader();
		// register event handlers
		MinecraftForge.EVENT_BUS.register(EventHandler.class);
		// no initAddons() call because frozen registries, that call will be done from the RegistryHandler
	}

	public Loader getLoader() {
		return this.loader;
	}

	@Override
	public void initAddons() {
		super.initAddons();
	}
}
