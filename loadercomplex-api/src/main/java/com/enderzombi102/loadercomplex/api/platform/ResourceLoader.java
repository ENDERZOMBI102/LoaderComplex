package com.enderzombi102.loadercomplex.api.platform;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ResourceLoader {
	@NotNull List<Resource> getResources( @NotNull String path );
}
