package com.enderzombi102.loadercomplex.fabric171.compat;

import com.enderzombi102.loadercomplex.api.LoaderComplex;
import com.enderzombi102.loadercomplex.api.addon.AddonContainer;
import com.terraformersmc.modmenu.ModMenu;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.terraformersmc.modmenu.util.mod.Mod;

public class LoaderComplexModMenuEntrypoint implements ModMenuApi {
	static {
		for ( AddonContainer container : LoaderComplex.getInstance().getAddonLoader().getAddons() ) {
			Mod mod = new LoaderComplexModImpl( container );
			ModMenu.MODS.put( container.getId(), mod );
			ModMenu.PARENT_MAP.put( ModMenu.MODS.get( "loadercomplex" ), mod );
		}
	}
}
