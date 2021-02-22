package com.enderzombi102.loaderComplex.fabric12.impl;

import com.enderzombi102.loadercomplex.common.abstraction.Loader;
import com.enderzombi102.loadercomplex.common.abstraction.Registry;
import com.enderzombi102.loadercomplex.common.abstraction.utils.LoaderType;

public class FabricLoader implements Loader {

	final FabricRegistry registry = new FabricRegistry();
	final String fabricVersion = net.fabricmc.loader.api.FabricLoader.getInstance().getModContainer("fabricloader").get().getMetadata().getVersion().getFriendlyString();

	@Override
	public LoaderType getLoaderType() {
		return LoaderType.Fabric12;
	}

	@Override
	public Registry getRegistry() {
		return this.registry;
	}

	@Override
	public String getMinecraftVersion() {
		return "1.12.2";
	}

	@Override
	public String getLoaderVersion() {
		return this.fabricVersion;
	}

	@Override
	public boolean isModLoaded(String id) {
		return net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded(id);
	}
}
