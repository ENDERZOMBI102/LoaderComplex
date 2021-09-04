package com.enderzombi102.loadercomplex.modloader;

import com.enderzombi102.loadercomplex.api.Addon;

import java.io.IOException;
import java.nio.file.Path;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

public class Mod {

	private final String mainClass;
	private final Path file;
	private final JarFile jarMod;
	private final String ID;
	private final String version;
	Addon implementation;

	public Mod(Path file) throws IOException {
		this.file = file;
		jarMod = new JarFile( file.toFile(), false );
		Attributes attributes = jarMod.getManifest().getMainAttributes();
		mainClass = attributes.getValue("LoaderComplex-Main");
		version = attributes.getValue("Implementation-Version");
		ID = attributes.getValue("LoaderComplex-Modid");
	}

	public String getName() {
		return implementation.getName() != null ?
				implementation.getName() :
				file.getFileName().toString().replace(".lc.jar", "");
	}

	public String getVersion() {
		return version;
	}

	public String getID() {
		return ID;
	}

	public JarFile getJarMod() {
		return jarMod;
	}

	public Path getPath() {
		return file;
	}

	public String getMainClass() {
		return mainClass;
	}

	public String getAuthors() {
		return implementation.getAuthors();
	}

	public Addon getImplementation() {
		return implementation;
	}
}
