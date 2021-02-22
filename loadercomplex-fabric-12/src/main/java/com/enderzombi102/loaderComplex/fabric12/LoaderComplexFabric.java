package com.enderzombi102.loaderComplex.fabric12;

import com.enderzombi102.loaderComplex.fabric12.impl.FabricLoader;
import com.enderzombi102.loadercomplex.common.Content;
import net.fabricmc.api.ModInitializer;

public class LoaderComplexFabric implements ModInitializer {

	public FabricLoader loader;

	@Override
	public void onInitialize() {
		this.loader = new FabricLoader();
		new Content(this.loader);
	}
}
