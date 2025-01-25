package com.enderzombi102.loadercomplex.api.platform;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface Resource {
	@NotNull ByteArrayInputStream open() throws IOException;
}
