package com.enderzombi102.loadercomplex.forge12.impl;

import com.enderzombi102.eventsystem.EventSystem;
import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.api.LoaderComplex;
import com.enderzombi102.loadercomplex.minecraft.util.FactoryWorld;
import com.enderzombi102.loadercomplex.minecraft.util.I18nSystem;
import com.enderzombi102.loadercomplex.minecraft.util.Platform;
import com.enderzombi102.loadercomplex.minecraft.util.Registry;
import com.enderzombi102.loadercomplex.forge12.LoaderComplexForge;
import com.enderzombi102.loadercomplex.forge12.impl.utils.ForgeFactoryWorld;
import com.enderzombi102.loadercomplex.forge12.impl.utils.ForgeI18nSystem;
import com.enderzombi102.loadercomplex.forge12.impl.utils.ForgeLegacyPlatform;
import com.enderzombi102.loadercomplex.forge12.impl.utils.ForgeRegistry;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

import static net.minecraftforge.fml.common.Loader.instance;

public class ForgeLoader implements Loader {
	private final EventSystem eventSystem;
	private final ForgeRegistry registry;
	private final I18nSystem i18nSystem;

	public ForgeLoader() {
		this.registry = new ForgeRegistry();
		this.eventSystem = new EventSystem();
		this.i18nSystem = new ForgeI18nSystem();
	}

	@Override
	public @NotNull Platform getPlatform() {
		return new ForgeLegacyPlatform();
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
	public boolean isAtLeastMinecraft(String version) {
		return true;
	}

	@Override
	public boolean isDedicatedServer() {
		return false;
	}

	@Override
	public boolean isModLoaded(String id) {
		return net.minecraftforge.fml.common.Loader.isModLoaded(id);
	}

	@Override
	public @NotNull LoaderComplex getLoaderComplex() {
		return LoaderComplexForge.INSTANCE;
	}

	@Override
	public @NotNull EventSystem getEventSystem() {
		return this.eventSystem;
	}

	@Override
	public @NotNull I18nSystem getI18nSystem() {
		return this.i18nSystem;
	}

	@Override
	public @NotNull Path getConfigDir() {
		return instance().getConfigDir().toPath();
	}
}
