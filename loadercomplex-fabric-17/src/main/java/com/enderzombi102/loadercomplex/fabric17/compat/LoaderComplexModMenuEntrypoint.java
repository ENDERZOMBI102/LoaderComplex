package com.enderzombi102.loadercomplex.fabric17.compat;

import com.enderzombi102.loadercomplex.fabric.FabricDispatcher;
import com.enderzombi102.loadercomplex.fabric17.LoaderComplexFabric;
import com.terraformersmc.modmenu.ModMenu;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class LoaderComplexModMenuEntrypoint implements ModMenuApi {
	static {
		if ( FabricDispatcher.getMinecraftVersion().equals( "1.17.1" ) )
			for ( var container : LoaderComplexFabric.INSTANCE.getAddonLoader().getAddons() )
				ModMenu.PARENT_MAP.put(
					ModMenu.MODS.get("loadercomplex"),
					ModMenu.MODS.put( container.getId(), new LoaderComplexModImpl( container ) )
				);
	}
}
