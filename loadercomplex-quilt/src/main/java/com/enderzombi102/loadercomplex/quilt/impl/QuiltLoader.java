package com.enderzombi102.loadercomplex.quilt.impl;

import com.enderzombi102.eventsystem.EventSystem;
import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.api.LoaderComplex;
import com.enderzombi102.loadercomplex.api.platform.*;
import net.fabricmc.api.EnvType;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;


public class QuiltLoader implements Loader {
	@SuppressWarnings( "OptionalGetWithoutIsPresent" )
	private static final String QUILT_VERSION = org.quiltmc.loader.api.QuiltLoader.getModContainer( "quilt_loader" )
		.get().metadata().version().raw();
	public static final org.quiltmc.loader.api.Version MINECRAFT_VERSION = org.quiltmc.loader.api.Version.of( "1.18.2" );
	private final Registry registry = new QuiltRegistry();

	@Override
	public @NotNull Platform getPlatform() {
		return LoaderType.Quilt;
	}

	@Override
	public @NotNull Registry getRegistry() {
		return registry;
	}

	@Override
	public boolean isModLoaded( String id ) {
		return org.quiltmc.loader.api.QuiltLoader.isModLoaded( id );
	}

	@Override
	public @NotNull FactoryWorld getFactoryWorld() {
		return new QuiltFactoryWorld();
	}

	@Override
	public boolean isAtLeastMinecraft( String version ) {
		return org.quiltmc.loader.api.Version.of( version ).semantic().compareTo( MINECRAFT_VERSION.semantic() ) >= 0;
	}

	@Override
	public boolean isDedicatedServer() {
		return org.quiltmc.loader.api.minecraft.MinecraftQuiltLoader.getEnvironmentType() == EnvType.SERVER;
	}

	@Override
	public @NotNull LoaderComplex getLoaderComplex() {
		return com.enderzombi102.loadercomplex.impl.LoaderComplex.instance;
	}

	@Override
	public @NotNull EventSystem getEventSystem() {
		return null;
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
