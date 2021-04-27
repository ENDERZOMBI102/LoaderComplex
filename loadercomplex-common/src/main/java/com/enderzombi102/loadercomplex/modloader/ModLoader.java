package com.enderzombi102.loadercomplex.modloader;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;

public class ModLoader {

	private final Path MODS_PATH;

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

	public void loadMods() {
		final ArrayList<Path> mods = new ArrayList<>();
		this.MODS_PATH.forEach(
			file -> {
				if( file.getFileName().endsWith(".lc.jar") ) mods.add( file );
			}
		);

		for ( Path mod : mods ) {
			try {
				FileSystem jarFS = getJarFileSystem( mod.toUri() );
				final Path mainClass = jarFS.getPath("main.class");
			} catch (IOException e) {
				System.out.println("something happened");
				e.printStackTrace();
			}
		}

	}

	private static FileSystem getJarFileSystem(URI uri) throws IOException {
		URI jarUri;
		try {
			jarUri = new URI("jar:" + uri.getScheme(), uri.getHost(), uri.getPath(), uri.getFragment());
		} catch (URISyntaxException e) {
			throw new IOException(e);
		}

		try {
			return FileSystems.newFileSystem( jarUri, Collections.emptyMap() );
		} catch (FileSystemAlreadyExistsException e) {
			return FileSystems.getFileSystem(jarUri);
		}
	}

}
