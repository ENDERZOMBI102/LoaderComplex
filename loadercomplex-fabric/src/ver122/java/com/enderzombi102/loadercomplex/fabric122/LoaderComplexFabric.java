package com.enderzombi102.loadercomplex.fabric122;

import com.enderzombi102.loadercomplex.fabric122.impl.FabricAddonContext;
import com.enderzombi102.loadercomplex.impl.LoaderComplex;
import com.enderzombi102.loadercomplex.impl.addon.finder.FolderAddonFinder;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;

public class LoaderComplexFabric extends LoaderComplex implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger( "LoaderComplex|Fabric12" );
	public static final ArrayList<AddonResourcePackImpl> packs = new ArrayList<>();
	public static LoaderComplexFabric INSTANCE;

	public LoaderComplexFabric() {
		super( "Fabric122", new FabricAddonContext() );
		this.setResourceHelper( mod -> packs.add( new AddonResourcePackImpl( mod ) ) );
		INSTANCE = this;

		loadAddons( Arrays.asList( new FolderAddonFinder( "addons", ".jar" ), new FolderAddonFinder( "mods", ".lc.jar" ) ) );
	}

	@Override
	public void onInitialize() {
		initAddons();
	}
}
