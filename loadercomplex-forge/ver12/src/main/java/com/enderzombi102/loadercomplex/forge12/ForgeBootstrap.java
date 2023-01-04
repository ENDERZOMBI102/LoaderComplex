package com.enderzombi102.loadercomplex.forge12;

import com.enderzombi102.loadercomplex.impl.KotlinLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
	modid = "loadercomplex",
	name = "LoaderComplex",
	version = "1.0.0",
	acceptedMinecraftVersions = "1.12.2",
	dependencies = "required:forge"
)
public class ForgeBootstrap {
	private final LoaderComplexForge impl = (LoaderComplexForge) KotlinLoader.bootstrap( "com.enderzombi102.loadercomplex.forge12.LoaderComplexForge" );

    /**
	 * This is the first initialization event. Register tile entities here.
	 * The registry events below will have fired prior to entry to this method.
	 */
	@Mod.EventHandler
	public void preinit( FMLPreInitializationEvent evt) {
		this.impl.preinit( evt );
	}

	/**
	 * This is the second initialization event. Register custom recipes
	 */
	@Mod.EventHandler
	public void init( FMLInitializationEvent evt) {
		this.impl.init( evt );
	}

	/**
	 * This is the final initialization event. Register actions from other mods here
	 */
	@Mod.EventHandler
	public void postinit( FMLPostInitializationEvent evt) {
		this.impl.postinit( evt );
	}
}
