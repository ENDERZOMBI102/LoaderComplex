package com.enderzombi102.loadercomplex.fabric17.compat;

import com.enderzombi102.loadercomplex.api.LoaderComplex;
import com.enderzombi102.loadercomplex.api.addon.AddonContainer;
import com.terraformersmc.modmenu.ModMenu;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class LoaderComplexModMenuEntrypoint implements ModMenuApi {
	static {
		for ( AddonContainer container : LoaderComplex.getInstance().getAddonLoader().getAddons() ) {
			ModMenu.PARENT_MAP.put(
				ModMenu.MODS.get( "loadercomplex" ),
				ModMenu.MODS.put( container.getId(), new LoaderComplexModImpl( container ) )
			);
		}
	}
}
