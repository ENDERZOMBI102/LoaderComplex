package com.enderzombi102.loaderComplex.fabric12.impl;


import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.api.Registry;
import com.enderzombi102.loadercomplex.api.utils.FactoryWorld;
import com.enderzombi102.loadercomplex.api.utils.LoaderType;
import com.enderzombi102.loadercomplex.api.utils.Version;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FabricLoader implements Loader {

	private final Registry registry;

	private static final String FABRIC_VERSION;
	public static final String MINECRAFT_VERSION = "1.12.2";
	static {
		//noinspection OptionalGetWithoutIsPresent
		FABRIC_VERSION = net.fabricmc.loader.api.FabricLoader.getInstance()
				.getModContainer("fabricloader")
				.get()
				.getMetadata()
				.getVersion()
				.getFriendlyString();
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
	public boolean isDeveloperEnvironment() {
		return net.fabricmc.loader.api.FabricLoader.getInstance().isDevelopmentEnvironment();
	}

	@Override
	public FactoryWorld getFactoryWorld() {
		return new FabricFactoryWorld();
	}

	@Override
	public boolean isAtLeastMinecraft(String version) {
		return true;
	}

	@Override
	public boolean isModLoaded(String id) {
		return net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded(id);
	}

	@Override
	public Version getApiVersion() {
		return new Version( "0.1.3", LocalDateTime.now().format( DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss") ) );
	}
}
