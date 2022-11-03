package com.enderzombi102.loadercomplex.forge12;

import com.enderzombi102.loadercomplex.forge12.impl.ForgeLoader;
import com.enderzombi102.loadercomplex.impl.LoaderComplexBase;
import com.google.common.collect.ImmutableList;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Mod(
	modid = "loadercomplex",
	name = "LoaderComplex",
	version = "1.0.0",
	acceptedMinecraftVersions = "1.12.2",
	dependencies = "required:forge"
)
public class LoaderComplexForge extends LoaderComplexBase {
	public static final Logger LOGGER = LogManager.getLogger("LoaderComplex | Forge12");

	public static LoaderComplexForge INSTANCE;
	private final ModContainer container;
	@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
	private final List<FrogeModContainer> containers = new ArrayList<>();

	public LoaderComplexForge() {
		super();
		INSTANCE = this;
		this.loader = new ForgeLoader();
		this.resourceHelper = container -> {
			// wrap AddonContainer in a forge-compatible one
			FrogeModContainer frogeModContainer = new FrogeModContainer( container );
			containers.add( frogeModContainer );
			injectContainer( frogeModContainer );
			FMLClientHandler.instance().addModAsResource( frogeModContainer );
		};
		this.container = Loader.instance().activeModContainer();
		this.initAddons();
	}

	public void injectContainer(FrogeModContainer frogeModContainer) {
		LOGGER.info( String.format("Injecting ModContainer for \"%s\" into mods list!", frogeModContainer.getModId() ) );
		Loader loader = Loader.instance();
		// inject into modlist
		try {
			Field field = loader.getClass().getDeclaredField("mods");
			field.setAccessible(true);
			//noinspection unchecked
			List<ModContainer> imods = (List<ModContainer>) field.get( loader );
			if ( imods instanceof ImmutableList ) {
				LOGGER.info("Mod list is immutable! making mutable...");
				// make it mutable again
				field.set( loader, new ArrayList<>( imods ) );
				LOGGER.info("Mod list is now mutable!");
			}
			//noinspection unchecked
			( (List<ModContainer>) field.get( loader ) ).add( frogeModContainer );
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		LOGGER.info( "Container injected!" );
	}

	public void injectActiveContainer(FrogeModContainer frogeModContainer) {
		LOGGER.info( String.format("Injecting ModContainer for \"%s\" into active mods list!", frogeModContainer.getModId() ) );
		try {
			Field field = loader.getClass().getDeclaredField("modController");
			field.setAccessible(true);
			LoadController controller = (LoadController) field.get( loader );
			controller.getActiveModList().add( frogeModContainer );
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		LOGGER.info( "Container injected!" );
	}

	public ForgeLoader getLoader() {
		return (ForgeLoader) this.loader;
	}

	/**
	 * This is the first initialization event. Register tile entities here.
	 * The registry events below will have fired prior to entry to this method.
	 */
	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent evt) { }

	/**
	 * This is the second initialization event. Register custom recipes
	 */
	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) { }

	/**
	 * This is the final initialization event. Register actions from other mods here
	 */
	@Mod.EventHandler
	public void postinit(FMLPostInitializationEvent evt) { }

	public ModContainer getContainer() {
		return container;
	}
}
