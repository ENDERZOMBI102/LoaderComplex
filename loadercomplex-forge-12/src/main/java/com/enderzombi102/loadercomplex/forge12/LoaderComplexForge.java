package com.enderzombi102.loadercomplex.forge12;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
		modid = LoaderComplexForge.MOD_ID,
		name = LoaderComplexForge.MOD_NAME,
		version = LoaderComplexForge.VERSION,
		acceptedMinecraftVersions =  "",
		dependencies = ""

)
public class LoaderComplexForge {

	public static final String MOD_ID = "loadercomplex-forge";
	public static final String MOD_NAME = "Loader Complex";
	public static final String VERSION = "1.0-SNAPSHOT";

	/**
	 * This is the instance of your mod as created by Forge. It will never be null.
	 */
	@Mod.Instance(MOD_ID)
	public static LoaderComplexForge INSTANCE;

	/**
	 * This is the first initialization event. Register tile entities here.
	 * The registry events below will have fired prior to entry to this method.
	 */
	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent event) {

	}

	/**
	 * This is the second initialization event. Register custom recipes
	 */
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

	}

	/**
	 * This is the final initialization event. Register actions from other mods here
	 */
	@Mod.EventHandler
	public void postinit(FMLPostInitializationEvent event) {

	}
}
