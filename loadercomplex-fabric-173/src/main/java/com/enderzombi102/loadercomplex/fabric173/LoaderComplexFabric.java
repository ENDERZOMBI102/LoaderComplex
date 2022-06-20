package com.enderzombi102.loadercomplex.fabric173;

import com.enderzombi102.loadercomplex.LoaderComplex;
import com.enderzombi102.loadercomplex.fabric173.impl.BabricLoader;
import net.fabricmc.api.ModInitializer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class LoaderComplexFabric extends LoaderComplex implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("LoaderComplex | Fabric173");

	@Override
	public void onInitialize() {
		this.loader = new BabricLoader();
		this.initAddons();
	}
}
