package com.enderzombi102.loadercomplex.impl;

import blue.endless.jankson.Jankson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * Some common utilities used in many parts of LoaderComplex
 */
public final class Utils {
	private static @Nullable String apiVersion;

	private Utils() {
	}

	public static final Jankson JANKSON = Jankson.builder().build();

	/**
	 * Tries to get the current API version of this LC implementation
	 *
	 * @return version of the currently implemented API
	 * @throws IllegalStateException if the LoaderComplex's manifest is somehow broken
	 */
	public static @Nullable String getApiVersion() {
		if ( apiVersion == null ) {
			@Nullable String version = null;
			InputStream stream = Utils.class.getResourceAsStream( "/api-version" );
			if ( stream != null ) {
				try {
					version = new BufferedReader( new InputStreamReader( stream ) ).readLine();
				} catch ( IOException e ) {
					LoggerFactory.getLogger( "LoaderComplex" ).warn( "Failed to read version information from LoaderComplex's jar", e );
				}
				try {
					stream.close();
				} catch ( IOException ignored ) {
				}
			}
			if ( version == null ) {
				try {
					Attributes attr = new Manifest( Utils.class.getResourceAsStream( "/META-INF/MANIFEST.MF" ) ).getMainAttributes();
					version = String.format(
						"%s+%s",
						attr.getValue( "Specification-Version" ),
						String.join( "", attr.getValue( "Implementation-Timestamp" ).split( "T" )[0].split( "-" ) )
					);
				} catch ( IOException e ) {
					throw new IllegalStateException( "Failed to read version information from LoaderComplex's manifest.", e );
				}
			}
			apiVersion = version;
		}

		return apiVersion;
	}

	/**
	 * Get the game directory
	 */
	public static Path getGameDir() {
		return Paths.get( System.getProperty( "user.dir" ) );
	}
}
