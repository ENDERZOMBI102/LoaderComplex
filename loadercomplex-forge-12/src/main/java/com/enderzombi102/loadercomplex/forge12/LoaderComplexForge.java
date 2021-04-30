package com.enderzombi102.loadercomplex.forge12;

import com.enderzombi102.loadercomplex.LoaderComplex;
import com.enderzombi102.loadercomplex.forge12.impl.ForgeLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
		modid = LoaderComplexForge.MOD_ID,
		name = LoaderComplexForge.MOD_NAME,
		version = LoaderComplexForge.VERSION,
		acceptedMinecraftVersions =  "",
		dependencies = ""

)
public class LoaderComplexForge extends LoaderComplex {

	public static final String MOD_ID = "loadercomplex";
	public static final String MOD_NAME = "Loader Complex (Forge)";
	public static final String VERSION = "1.0.0";

	final ForgeLoader loader = new ForgeLoader();
	public static final Logger LOGGER = LogManager.getLogger("[LoaderComplex-Forge]");

	/**
	 * This is the instance of your mod as created by Forge. It will never be null.
	 */
	@Mod.Instance(MOD_ID)
	public static LoaderComplexForge INSTANCE;

	public LoaderComplexForge() {
		super();
		this.initMods();
	}

	/**
	 * This is the first initialization event. Register tile entities here.
	 * The registry events below will have fired prior to entry to this method.
	 */
	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent evt) {}

	/**
	 * This is the second initialization event. Register custom recipes
	 */
	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {}

	/**
	 * This is the final initialization event. Register actions from other mods here
	 */
	@Mod.EventHandler
	public void postinit(FMLPostInitializationEvent evt) {}
}
