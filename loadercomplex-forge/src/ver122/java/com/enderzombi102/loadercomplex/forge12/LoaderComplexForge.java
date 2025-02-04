package com.enderzombi102.loadercomplex.forge12;

import com.enderzombi102.loadercomplex.forge12.impl.ForgeAddonContext;
import com.enderzombi102.loadercomplex.impl.LoaderComplex;
import com.enderzombi102.loadercomplex.impl.Reflect;
import com.enderzombi102.loadercomplex.impl.addon.finder.FolderAddonFinder;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LoaderComplexForge extends LoaderComplex {
	public static final Logger LOGGER = LoggerFactory.getLogger( "LoaderComplex|Forge12" );
	private final ModContainer container;
	@SuppressWarnings( "MismatchedQueryAndUpdateOfCollection" )
	private final List<FrogeModContainer> containers = new ArrayList<>();

	public LoaderComplexForge() {
		super( "Forge122", new ForgeAddonContext() );
		this.setResourceHelper( container -> {
			// wrap AddonContainer in a forge-compatible one
			FrogeModContainer frogeModContainer = new FrogeModContainer( container );
			this.containers.add( frogeModContainer );
			injectContainer( frogeModContainer );
			FMLClientHandler.instance().addModAsResource( frogeModContainer );
		} );
		this.container = Loader.instance().activeModContainer();
		this.loadAddons( Arrays.asList( new FolderAddonFinder( "addons", ".jar" ), new FolderAddonFinder( "mods", ".lc.jar" ) ) );
		this.initAddons();
	}

	public void injectContainer( FrogeModContainer frogeModContainer ) {
		LOGGER.info( "Injecting ModContainer for \"{}\" into mods list", frogeModContainer.getModId() );
		Reflect<Loader> mirror = Reflect.that( Loader.instance() );
		{	// inject into "loaded mods"
			@SuppressWarnings( "unchecked" )
			List<ModContainer> mods = mirror.get( "mods", List.class ).unwrap();
			if ( mods instanceof ImmutableList ) {
				LOGGER.info( "Mod list is immutable! making mutable..." );
				// make it mutable again
				mirror.set( "mods", new ArrayList<>( mods ), List.class );
				LOGGER.info( "Mod list is now mutable!" );
			}
			//noinspection unchecked
			((List<ModContainer>) mirror.get( "mods", List.class ).unwrap()).add( frogeModContainer );
			LOGGER.info( " - Container injected into 'loaded mods' list" );
		}
//		{
//			mirror
//				.get( "modController", LoadController.class )
//				.unwrap()
//				.getActiveModList()
//				.add( frogeModContainer );
//			LOGGER.info( " - Container injected into active mod list" );
//		}
	}

	/**
	 * This is the first initialization event. Register tile entities here.
	 * The registry events will have fired prior to entry to this method.
	 */
	@Mod.EventHandler
	public void preinit( FMLPreInitializationEvent evt ) {
		// add child mods
		evt.getModMetadata().childMods.addAll( containers );
	}

	/**
	 * This is the second initialization event. Register custom recipes
	 */
	@Mod.EventHandler
	public void init( FMLInitializationEvent evt ) { }

	/**
	 * This is the final initialization event. Register actions from other mods here
	 */
	@Mod.EventHandler
	public void postinit( FMLPostInitializationEvent evt ) { }

	public ModContainer getContainer() {
		return this.container;
	}
}
