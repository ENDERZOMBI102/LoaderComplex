package com.enderzombi102.loadercomplex.impl;

import com.enderzombi102.loadercomplex.impl.addon.DynamicClassLoader;
import jdk.internal.loader.ClassLoaders;
import jdk.internal.loader.URLClassPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;


/**
 * Little helper class to bootstrap LoaderComplex with a classloader containing its dependencies, no matter what.
 */
public class EnvironmentLoader {
	private static final ClassLoader LOADER = ensureKotlin();

	private static void addLibraries( final List<URL> libraries ) {
		ClassLoader loader = EnvironmentLoader.class.getClassLoader();

		// if we're under fabric's knot, we need another type of patch
		if ( loader.getClass().getSimpleName().startsWith( "Knot" ) ) {
			// HACK: This is _very_ dirty
			Consumer<URL> consumer = Reflect.that( loader )
				.get( "urlLoader", "net.fabricmc.loader.impl.launch.knot.KnotClassLoader$DynamicURLClassLoader" )
				.invokerTyped( "addURL", void.class, Consumer.class, URL.class );
			// add all the libs
			libraries.forEach( consumer );
		} else {
			Reflect.openModule( "java.base", "jdk.internal.loader" );
			URLClassPath ucp = Reflect.that( ClassLoaders.appClassLoader() ).get( "ucp", URLClassPath.class ).unwrap();
			libraries.forEach( ucp::addURL );
		}
	}

	private static class Dependency {
		String file;
		long size;
		long hash;

		Dependency( @NotNull String line ) {
			String[] parts = line.split( "\t" );
			this.file = parts[0];
			this.size = Long.parseLong( parts[1] );
			this.hash = Long.parseLong( parts[2] );
		}

		public URL getURL() {
			return EnvironmentLoader.class.getResource( "/jars/" + this.file );
		}
	}

	/**
	 * Checks if the current {@link ClassLoader} has kotlin, if not, creates a new {@link DynamicClassLoader} with kotlin.
	 *
	 * @return a {@link ClassLoader} with kotlin on the classpath.
	 */
	private static @NotNull ClassLoader ensureKotlin() {
		// cannot yet use slf4j, as it might not be used by the current platform
		final Logger logger = LogManager.getLogger( "LoaderComplex|EnvironmentLoader" );
		ClassLoader loader = EnvironmentLoader.class.getClassLoader();

		logger.info( "Checking availability of dependencies on classpath..." );

		{
			logger.info( "Trying to load kotlin from embedded jars..." );
			//noinspection resource
			DynamicClassLoader dynamic = new DynamicClassLoader();

			// load jar names from the main jar
			List<Dependency> deps = null;
			InputStream stream = EnvironmentLoader.class.getResourceAsStream( "/jars/list" );
			if ( stream != null ) {
				try ( BufferedReader reader = new BufferedReader( new InputStreamReader( stream ) ) ) {
					deps = reader.lines()
						.filter( it -> !it.isEmpty() )
						.map( Dependency::new )
						.collect( Collectors.toList() );
				} catch ( IOException ex ) {
					throw new RuntimeException( ex );
				}
			}

			// it should have been initialized by now...
			assert deps != null;

			// create cache directory if missing
			Path cache = getGameDir().resolve( "cache/loadercomplex/lib" );
			try {
				Files.createDirectories( cache );
			} catch ( IOException e ) {
				throw new RuntimeException( e );
			}

			// extract jars
			List<URL> libraries = new ArrayList<>();
			for ( Dependency dep : deps ) {
				File file = cache.resolve( dep.file ).toFile();

				try {
					// add to known libs
					libraries.add( file.toURI().toURL() );

					// file already exists, check it
					if ( file.exists() && file.length() == dep.size ) {
						continue;
					}

					// file either doesn't exist, or failed to be verified
					Files.deleteIfExists( file.toPath() );
					Files.copy( dep.getURL().openStream(), file.toPath() );
				} catch ( IOException e ) {
					throw new RuntimeException( e );
				}
			}

			// add them to the classloader
			libraries.forEach( dynamic::addURL );
			addLibraries( libraries );

			// load version
			try {
				String version = dynamic.loadClass( "kotlin.KotlinVersion" )
					.getDeclaredField( "CURRENT" )
					.get( null )
					.toString();
				logger.info( "Found kotlin {}, continuing loading process", version );
			} catch ( Throwable throwable ) {
				throw new RuntimeException( throwable );
			}
			loader = dynamic;
		}
		return loader;
	}

	private static @NotNull Path getGameDir() {
		String gameDir;
		gameDir = System.getProperty( "minecraft.sharedDataDir" );
		if ( gameDir == null ) {
			gameDir = System.getenv( "MINECRAFT_SHARED_DATA_DIR" );
		}
		if ( gameDir != null ) {
			return Paths.get( gameDir ).toAbsolutePath();
		}
		return Paths.get( "" ).toAbsolutePath();
	}

	/**
	 * Creates an object of the specified class with the environment classloader.
	 *
	 * @param clazz path of the class to instantiate.
	 * @return an object of the given class.
	 */
	public static @NotNull Object bootstrap( @NotNull String clazz ) {
		try {
			return LOADER.loadClass( clazz ).getConstructor().newInstance();
		} catch ( Throwable e ) {
			throw new IllegalStateException( "Failed to bootstrap LoaderComplex with environment classloader!", e );
		}
	}
}
