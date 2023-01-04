package com.enderzombi102.loadercomplex.impl;

import com.enderzombi102.loadercomplex.impl.addon.DynamicClassLoader;
import kotlin.KotlinVersion;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import static com.enderzombi102.enderlib.collections.ListUtil.listOf;
import static com.enderzombi102.enderlib.reflection.Getters.getStatic;

/**
 * Little helper class to bootstrap LoaderComplex with a classloader containing kotlin, no matter what.
 */
public class KotlinLoader {
    private static final ClassLoader LOADER = ensureKotlin();

    /**
     * Checks if the current {@link ClassLoader} has kotlin, if not, creates a new {@link DynamicClassLoader} with kotlin.
     * @return a {@link ClassLoader} with kotlin on the classpath.
     */
	public static @NotNull ClassLoader ensureKotlin() {
		Logger logger = LoggerFactory.getLogger( "LoaderComplex | KotlinLoader" );
		ClassLoader loader = KotlinLoader.class.getClassLoader();
		String version;
		logger.info( "Checking for kotlin..." );
		try {
			Class.forName( "kotlin.String", false, loader );
			version = KotlinVersion.CURRENT.toString();
		} catch ( ClassNotFoundException e ) {
			logger.info( "Trying to load kotlin from embedded jars..." );
			DynamicClassLoader dynamic = new DynamicClassLoader();

			// load jar names from the main jar
			List<String> jars = listOf();
			try {
				InputStream stream = KotlinLoader.class.getResourceAsStream( "/jars/list" );
				if ( stream != null ) {
					BufferedReader reader = new BufferedReader( new InputStreamReader( stream ) );
					jars = reader.lines().collect( Collectors.toList() );
					stream.close();
				}
			} catch ( IOException ex ) {
				throw new RuntimeException( ex );
			}

			// add them to the classloader
			for ( String jar : jars )
				dynamic.addURL( KotlinLoader.class.getResource( "/jars/" + jar + ".jar" ) );

			// load version
			try {
				Class<?> KotlinVersion = dynamic.loadClass( "kotlin.KotlinVersion" );
				version = getStatic( KotlinVersion, "CURRENT", KotlinVersion ).toString();
			} catch ( Throwable throwable ) {
				throw new RuntimeException( throwable );
			}
			loader = dynamic;
		}
		logger.info( "Found kotlin {}, continuing loading process", version );
		return loader;
	}

    /**
     * Creates an object of the specified class with the kotlin classloader.
     * @param clazz path of the class to instantiate.
     * @return an object of the given class.
     */
    public static @NotNull Object bootstrap( @NotNull String clazz ) {
        try {
            return LOADER.loadClass( clazz ).getConstructor().newInstance();
        } catch ( Throwable e ) {
            throw new IllegalStateException( "Failed to bootstrap kotlin classloader!", e );
        }
    }
}
