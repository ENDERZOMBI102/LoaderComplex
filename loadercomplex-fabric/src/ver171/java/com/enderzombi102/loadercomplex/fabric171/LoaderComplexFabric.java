package com.enderzombi102.loadercomplex.fabric171;

import com.enderzombi102.loadercomplex.fabric171.impl.FabricAddonContext;
import com.enderzombi102.loadercomplex.impl.LoaderComplex;
import com.enderzombi102.loadercomplex.impl.addon.finder.FolderAddonFinder;
import net.fabricmc.api.ModInitializer;

import java.util.ArrayList;
import java.util.Arrays;


public class LoaderComplexFabric extends LoaderComplex implements ModInitializer {
	public static final ArrayList<AddonResourcePackImpl> packs = new ArrayList<>();
	
	public LoaderComplexFabric() {
		super( "Fabric171", new FabricAddonContext() );
		this.setResourceHelper( mod -> packs.add( new AddonResourcePackImpl( mod ) ) );

		loadAddons( Arrays.asList( new FolderAddonFinder( "addons", ".jar" ), new FolderAddonFinder( "mods", ".lc.jar" ) ) );
	}

	@Override
	public void onInitialize() {
		initAddons();
	}
}
