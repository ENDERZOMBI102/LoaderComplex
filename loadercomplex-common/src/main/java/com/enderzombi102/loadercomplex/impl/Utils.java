package com.enderzombi102.loadercomplex.impl;

import blue.endless.jankson.Jankson;
import com.enderzombi102.loadercomplex.api.utils.Version;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * Some common utilities used in many parts of LoaderComplex
 */
public final class Utils {
	private Utils() { }

	public static final Jankson JANKSON = Jankson.builder().build();

	/**
	 * Tries to get the current API version of this LC implementation
	 * @param isDevEnv whether this is a developer environment
	 * @return version of the currently implemented API
	 * @throws IllegalStateException if the LoaderComplex's manifest is somehow broken
	 */
	public static Version getApiVersion( boolean isDevEnv ) {
		if ( isDevEnv )
			return new Version(
				"1.0.0",
				LocalDateTime.now().format( DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss") )
			);
		// TODO: only works on prod?
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

	/**
	 * Get the game directory
	 */
	public static Path getGameDir() {
		return Paths.get( System.getProperty("user.dir") );
	}
}
