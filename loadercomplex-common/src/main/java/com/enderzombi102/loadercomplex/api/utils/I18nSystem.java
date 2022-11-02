package com.enderzombi102.loadercomplex.api.utils;

import org.jetbrains.annotations.NotNull;

public interface I18nSystem {
	@NotNull String translate( @NotNull String key );
	@NotNull String translate( @NotNull String key, Object... args );
}
