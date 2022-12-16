package com.enderzombi102.loadercomplex.forge12.impl.utils;

import com.enderzombi102.loadercomplex.minecraft.util.I18nSystem;
import net.minecraft.client.resources.I18n;
import org.jetbrains.annotations.NotNull;

public class ForgeI18nSystem implements I18nSystem {
	@Override
	public @NotNull String translate( @NotNull String key ) {
		return I18n.format( key );
	}

	@Override
	public @NotNull String translate( @NotNull String key, Object... args ) {
		return I18n.format( key );
	}

	@Override
	public boolean contains( @NotNull String key ) {
		return I18n.hasKey( key );
	}

	@Override
	public @NotNull String getCurrentLang() {
		return "en_us";
	}
}
