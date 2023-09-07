package com.enderzombi102.loadercomplex.fabric17.impl;

import com.enderzombi102.eventsystem.EventSystem;
import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.api.LoaderComplex;
import com.enderzombi102.loadercomplex.api.platform.*;
import com.enderzombi102.loadercomplex.fabric17.LoaderComplexFabric;
import net.fabricmc.loader.api.VersionParsingException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class FabricLoader implements Loader {
	private static final String FABRIC_VERSION;
	public static final net.fabricmc.loader.api.Version MINECRAFT_VERSION;
	private static final EventSystem EVENT_SYSTEM = new EventSystem( LoggerFactory.getLogger("LoaderComplex | EventSystem") );

	static {
		try {
			MINECRAFT_VERSION = net.fabricmc.loader.api.Version.parse("1.17.1");
		} catch (VersionParsingException e) {
			throw new IllegalStateException("Something is REALLY wrong with versions!", e);
		}

		//noinspection OptionalGetWithoutIsPresent
		FABRIC_VERSION = net.fabricmc.loader.api.FabricLoader.getInstance()
				.getModContainer("fabricloader")
				.get()
				.getMetadata()
				.getVersion()
				.getFriendlyString();
	}
	private final Registry registry = new FabricRegistry();

	@Override
	public @NotNull Platform getPlatform() {
		return LoaderType.Fabric;
	}

	@Override
	public @NotNull Registry getRegistry() {
		return registry;
	}

	@Override
	public boolean isModLoaded(String id) {
		return net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded( id );
	}

	@Override
	public @NotNull FactoryWorld getFactoryWorld() {
		return new FabricFactoryWorld();
	}

	@Override
	public boolean isAtLeastMinecraft(String version) {
		try {
			return net.fabricmc.loader.api.Version.parse(version).compareTo( MINECRAFT_VERSION ) >= 0;
		} catch (VersionParsingException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public boolean isDedicatedServer() {
		return false;
	}

	@Override
	public @NotNull LoaderComplex getLoaderComplex() {
		return LoaderComplexFabric.instance;
	}

	@NotNull
	@Override
	public EventSystem getEventSystem() {
		return EVENT_SYSTEM;
	}

	@Override
	public @NotNull I18nSystem getI18nSystem() {
		return null;
	}

	@Override
	public @NotNull ResourceLoader getResourceLoader() {
		return null;
	}

	@Override
	public @NotNull Path getConfigDir() {
		return null;
	}
}
