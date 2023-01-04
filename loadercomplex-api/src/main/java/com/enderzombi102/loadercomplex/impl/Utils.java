package com.enderzombi102.loadercomplex.impl;

import blue.endless.jankson.Jankson;
import com.enderzombi102.loadercomplex.api.utils.Version;
import org.jetbrains.annotations.Nullable;

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
	private static @Nullable Version apiVersion = null;
	private Utils() { }

	public static final Jankson JANKSON = Jankson.builder().build();

	/**
	 * Tries to get the current API version of this LC implementation
	 * @return version of the currently implemented API
	 * @throws IllegalStateException if the LoaderComplex's manifest is somehow broken
	 */
	public static Version getApiVersion() {
		if ( apiVersion != null )
			return apiVersion;

		InputStream stream = Utils.class.getResourceAsStream( "/api-version" );
		if ( stream != null ) {
			try {
				BufferedReader reader = new BufferedReader( new InputStreamReader( stream ) );
				apiVersion = new Version( reader.readLine(), reader.readLine() );
			} catch ( IOException e ) {
				throw new IllegalStateException( "Failed to read version information from LoaderComplex's jar", e );
			} finally {
				try {
					stream.close();
				} catch ( IOException ignored ) { }
			}
		}
		if ( apiVersion == null ) {
			try {
				Attributes attr = new Manifest( Utils.class.getResourceAsStream( "/META-INF/MANIFEST.MF" ) ).getMainAttributes();
				apiVersion = new Version( attr.getValue( "Specification-Version" ), attr.getValue( "Implementation-Timestamp" ) );
			} catch ( IOException e ) {
				throw new IllegalStateException( "Failed to read version information from LoaderComplex's jar", e );
			}
		}
		return apiVersion;
	}

	/**
	 * Get the game directory
	 */
	public static Path getGameDir() {
		return Paths.get( System.getProperty("user.dir") );
	}
}
