package com.enderzombi102.loadercomplex.fabric17.impl;

import com.enderzombi102.eventsystem.EventSystem;
import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.api.Registry;
import com.enderzombi102.loadercomplex.minecraft.util.FactoryWorld;
import com.enderzombi102.loadercomplex.minecraft.util.LoaderType;
import com.enderzombi102.loadercomplex.minecraft.util.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.minecraft.client.option.KeyBinding;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
	public LoaderType getLoaderType() {
		return LoaderType.Fabric;
	}

	@Override
	public @NotNull Registry getRegistry() {
		return registry;
	}

	@Override
	public String getMinecraftVersion() {
		return MINECRAFT_VERSION.getFriendlyString();
	}

	@Override
	public String getLoaderVersion() {
		return FABRIC_VERSION;
	}

	@Override
	public boolean isModLoaded(String id) {
		return net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded( id );
	}

	@Override
	public boolean isDeveloperEnvironment() {
		return net.fabricmc.loader.api.FabricLoader.getInstance().isDevelopmentEnvironment();
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
	public Version getApiVersion() {
		return new Version( "0.1.3", LocalDateTime.now().format( DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss") ) );
	}

	@NotNull
	@Override
	public EventSystem getEventSystem() {
		return EVENT_SYSTEM;
	}
}
