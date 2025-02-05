package com.enderzombi102.loadercomplex.forge182;

import com.enderzombi102.loadercomplex.impl.LoaderComplex;
import com.enderzombi102.loadercomplex.forge182.impl.ForgeAddonContext;
import com.enderzombi102.loadercomplex.impl.addon.finder.FolderAddonFinder;
import net.minecraftforge.common.MinecraftForge;

import java.util.Arrays;

public class LoaderComplexForge extends LoaderComplex {
	// FIXME: Fix crash on start

	public LoaderComplexForge() {
		super( "Forge18", new ForgeAddonContext() );
		// register event handlers
		MinecraftForge.EVENT_BUS.register( EventHandler.class );
		this.loadAddons( Arrays.asList( new FolderAddonFinder( "addons", ".jar" ), new FolderAddonFinder( "mods", ".lc.jar" ) ) );
		// no initAddons() call because frozen registries, that call will be done from the RegistryHandler
	}
}
