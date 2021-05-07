package com.enderzombi102.loadercomplex.modloader;

import com.enderzombi102.loadercomplex.api.ContentMod;

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
	public ContentMod implementation;

	public Mod(Path file) throws IOException {
		this.file = file;
		this.jarMod = new JarFile( file.toFile(), false );
		Attributes attributes = this.jarMod.getManifest().getMainAttributes();
		this.version = attributes.getValue("Implementation-Version");
		this.mainClass = attributes.getValue("LoaderComplex-Main");
		this.ID = attributes.getValue("LoaderComplex-Modid");
	}

	public String getName() {
		return this.file.getFileName().toString().replace(".lc.jar", "");
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
}
