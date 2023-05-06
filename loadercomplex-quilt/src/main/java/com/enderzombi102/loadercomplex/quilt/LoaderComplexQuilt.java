package com.enderzombi102.loadercomplex.quilt;

import com.enderzombi102.loadercomplex.impl.LoaderComplex;
import com.enderzombi102.loadercomplex.quilt.impl.QuiltLoader;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import java.util.ArrayList;
import java.util.List;

public class LoaderComplexQuilt extends LoaderComplex implements ModInitializer {
	public final List<QuiltResourcePack> packs = new ArrayList<>();

	public LoaderComplexQuilt() {
		super( "Quilt", new QuiltLoader() );
		this.setResourceHelper( mod -> packs.add( new QuiltResourcePack( mod ) ) );
	}

	@Override
	public void onInitialize( ModContainer mod ) {
		initAddons();
	}
}
