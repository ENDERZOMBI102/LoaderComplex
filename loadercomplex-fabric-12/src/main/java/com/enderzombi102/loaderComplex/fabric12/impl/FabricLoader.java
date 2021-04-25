package com.enderzombi102.loaderComplex.fabric12.impl;


import com.enderzombi102.loadercomplex.abstraction.Loader;
import com.enderzombi102.loadercomplex.abstraction.Registry;
import com.enderzombi102.loadercomplex.abstraction.utils.LoaderType;

public class FabricLoader implements Loader {

	private final Registry registry;
	private static final String FABRIC_VERSION;
	public static final String MINECRAFT_VERSION = "1.12.2";
	static {
		FABRIC_VERSION = net.fabricmc.loader.api.FabricLoader.getInstance().getModContainer("fabricloader")
				.get().getMetadata().getVersion().getFriendlyString();
	}

	public FabricLoader() {
		this.registry = new FabricRegistry();
	}

	@Override
	public LoaderType getLoaderType() {
		return LoaderType.FabricLegacy;
	}

	@Override
	public Registry getRegistry() {
		return this.registry;
	}

	@Override
	public String getMinecraftVersion() {
		return MINECRAFT_VERSION;
	}

	@Override
	public String getLoaderVersion() {
		return FABRIC_VERSION;
	}

	@Override
	public boolean isModLoaded(String id) {
		return net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded(id);
	}
}
