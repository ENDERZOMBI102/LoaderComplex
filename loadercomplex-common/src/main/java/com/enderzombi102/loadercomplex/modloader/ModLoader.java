package com.enderzombi102.loadercomplex.modloader;

import com.enderzombi102.loadercomplex.abstraction.ContentMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ModLoader {

	private final Path MODS_PATH;
	private final Logger logger = LogManager.getLogger("LC-ModLoader");
	private final ArrayList<Mod> mods = new ArrayList<>();

	public ModLoader() {
		Path path;
		try {
			path = Paths.get(
					new File(
							ModLoader.class
									.getProtectionDomain()
									.getCodeSource()
									.getLocation()
									.toURI()
					).getParent()
			);
		} catch (URISyntaxException e) {
			System.out.println("Failed to detect mods folder.");
			e.printStackTrace();
			path = null;
		}
		MODS_PATH = path;
	}

	@SuppressWarnings("unchecked")
	public void loadMods() {
		final ArrayList<URL> urls = new ArrayList<>();

		this.logger.info("FINDING MODS");
		this.MODS_PATH.forEach(
			file -> {
				if( file.getFileName().endsWith(".lc.jar") ) {
					try {
						urls.add( file.toUri().toURL() );
						this.mods.add(
								new Mod(
										file,
										this.logger
								)
						);
					} catch (IOException e) {
						this.logger.error("Failed to load possible LC mod: " + file.getFileName() );
					}
				}
			}
		);

		URLClassLoader child = new URLClassLoader(
				(URL[]) urls.toArray(),
				this.getClass().getClassLoader()
		);

		this.logger.info("INSTANTIATING MODS");
		for ( Mod mod : this.mods ) {
			try {
				Class<?> classToLoad = Class.forName("", true, child);
				if (
						Arrays.stream( classToLoad.getInterfaces() )
							.anyMatch( iface -> iface.getSimpleName().equals("ContentMod") )
				) {
					Class<? extends ContentMod> modToLoad = (Class<? extends ContentMod>) classToLoad;
					mod.implementation = modToLoad.newInstance();
				}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				this.logger.error("can't load mod file: " + mod.file);
			}
		}

	}

}
