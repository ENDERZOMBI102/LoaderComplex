package com.enderzombi102.loadercomplex.quilt.compat;

import com.enderzombi102.loadercomplex.quilt.LoaderComplexQuilt;
import com.terraformersmc.modmenu.ModMenu;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class LoaderComplexModMenuEntrypoint implements ModMenuApi {
	static {
		for ( var container : LoaderComplexQuilt.INSTANCE.getAddonLoader().getAddons() )
			ModMenu.PARENT_MAP.put(
				ModMenu.MODS.get("loadercomplex"),
				ModMenu.MODS.put( container.getID(), new LoaderComplexModImpl( container ) )
			);
	}
}
