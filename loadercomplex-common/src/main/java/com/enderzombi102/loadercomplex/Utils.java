package com.enderzombi102.loadercomplex;

import com.enderzombi102.loadercomplex.api.utils.Version;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public final class Utils {
	private Utils() { }

	public static String format(String fmt, Object... objs) {
		for ( Object obj : objs ) {
			fmt = fmt.replaceFirst("\\{}", obj.toString() );
		}
		return fmt;
	}

	public static Version getApiVersion() {
		try {
			final Attributes fest = new Manifest(
					Utils.class.getResourceAsStream("META-INF/MANIFEST.MF")
			).getMainAttributes();
			return new Version(
					fest.getValue("ApiVersion"),
					fest.getValue("Implementation-Timestamp")
			);
		} catch (IOException ignored) {
			throw new IllegalStateException("Failed to read manifest from LoaderComplex's jar");
		}
	}
}
