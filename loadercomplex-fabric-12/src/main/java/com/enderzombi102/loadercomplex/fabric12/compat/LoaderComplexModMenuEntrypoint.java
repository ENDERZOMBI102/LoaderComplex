package com.enderzombi102.loadercomplex.fabric12.compat;

import com.enderzombi102.loadercomplex.fabric.FabricDispatcher;
import com.enderzombi102.loadercomplex.fabric12.LoaderComplexFabric;
import com.enderzombi102.loadercomplex.addonloader.AddonContainer;
import io.github.prospector.modmenu.ModMenu;
import io.github.prospector.modmenu.api.ModMenuApi;

public class LoaderComplexModMenuEntrypoint implements ModMenuApi {
	static {
		if ( FabricDispatcher.getMinecraftVersion().equals( "1.12.2" ) )
			for ( AddonContainer container : LoaderComplexFabric.INSTANCE.getAddonLoader().getAddons() )
				ModMenu.PARENT_MAP.put(
					ModMenu.MODS.get("loadercomplex"),
					ModMenu.MODS.put( container.getId(), new LoaderComplexModImpl( container ) )
				);
	}
}
