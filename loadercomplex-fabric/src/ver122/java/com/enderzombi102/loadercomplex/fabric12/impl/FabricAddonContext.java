package com.enderzombi102.loadercomplex.fabric12.impl;


import com.enderzombi102.loadercomplex.api.AddonContext;
import com.enderzombi102.loadercomplex.api.LoaderComplex;
import com.enderzombi102.loadercomplex.api.platform.*;
import com.unascribed.flexver.FlexVerComparator;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class FabricAddonContext implements AddonContext {
	public static final String MINECRAFT_VERSION = "1.12.2";
	private final Registry registry;

	public FabricAddonContext() {
		this.registry = new FabricRegistry();
	}

	@Override
	public @NotNull PlatformInfo getPlatformInfo() {
		return new PlatformInfo() {
			@Override
			public @NotNull String name() {
				return "";
			}

			@Override
			public @NotNull String version() {
				return FabricLoader.getInstance()
					.getModContainer( "fabricloader" )
					.orElseThrow( IllegalStateException::new )
					.getMetadata()
					.getVersion()
					.getFriendlyString();
			}

			@Override
			public @NotNull String id() {
				return "";
			}

			@Override
			public @NotNull String minecraftVersion() {
				return MINECRAFT_VERSION;
			}

			@Override
			public @NotNull String getApiVersion() {
				return "1.3";
			}

			@Override
			public boolean isDeveloperEnvironment() {
				return FabricLoader.getInstance().isDevelopmentEnvironment();
			}
		};
	}

	@Override
	public @NotNull Registry getRegistry() {
		return this.registry;
	}

	public @NotNull FactoryWorld getFactoryWorld() {
		return new FabricFactoryWorld();
	}

	@Override
	public boolean isAtLeastMinecraft( String version ) {
		return FlexVerComparator.compare( version, MINECRAFT_VERSION ) >= 0;
	}

	@Override
	public boolean isDedicatedServer() {
		return FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;
	}

	@Override
	public @NotNull LoaderComplex getLoaderComplex() {
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
		return FabricLoader.getInstance().getConfigDir();
	}

	@Override
	public boolean isModLoaded( String id ) {
		return FabricLoader.getInstance().isModLoaded( id );
	}

//	@Override
//	public Version getApiVersion() {
//		return new Version( "0.1.3", LocalDateTime.now().format( DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss") ) );
//	}
}
