package com.enderzombi102.loadercomplex.forge12.impl;

import com.enderzombi102.loadercomplex.api.AddonContext;
import com.enderzombi102.loadercomplex.api.platform.*;
import com.enderzombi102.loadercomplex.forge12.impl.utils.ForgeFactoryWorld;
import com.enderzombi102.loadercomplex.forge12.impl.utils.ForgeI18nSystem;
import com.enderzombi102.loadercomplex.forge12.impl.utils.ForgeRegistry;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class ForgeAddonContext implements AddonContext {
	private final ForgeRegistry registry;
	private final I18nSystem i18nSystem;

	public ForgeAddonContext() {
		this.registry = new ForgeRegistry();
		this.i18nSystem = new ForgeI18nSystem();
	}

	@Override
	public @NotNull PlatformInfo getPlatformInfo() {
		return new PlatformInfo() {
			@Override
			public @NotNull String name() {
				return "Forge";
			}

			@Override
			public @NotNull String version() {
				return ForgeVersion.getVersion();
			}

			@Override
			public @NotNull String id() {
				return "forge-legacy";
			}

			@Override
			public @NotNull String minecraftVersion() {
				return "1.12.2";
			}

			@Override
			public @NotNull String getApiVersion() {
				return "0.1.3";
			}

			@Override
			public boolean isDeveloperEnvironment() {
				return (boolean) Launch.blackboard.getOrDefault( "fml.deobfuscatedEnvironment", false );
			}
		};
	}

	@Override
	public @NotNull Registry getRegistry() {
		return this.registry;
	}

	@Override
	public @NotNull FactoryWorld getFactoryWorld() {
		return new ForgeFactoryWorld();
	}

	@Override
	public boolean isAtLeastMinecraft( String version ) {
		throw new RuntimeException( "Not implemented" );
	}

	@Override
	public boolean isDedicatedServer() {
		return FMLCommonHandler.instance().getSide().isServer();
	}

	@Override
	public boolean isModLoaded( String id ) {
		return Loader.isModLoaded( id );
	}

	@Override
	public @NotNull I18nSystem getI18nSystem() {
		return this.i18nSystem;
	}

	@Override
	public @NotNull ResourceLoader getResourceLoader() {
		throw new RuntimeException( "Not implemented" );
	}

	@Override
	public @NotNull Path getConfigDir() {
		return Loader.instance().getConfigDir().toPath();
	}
}
