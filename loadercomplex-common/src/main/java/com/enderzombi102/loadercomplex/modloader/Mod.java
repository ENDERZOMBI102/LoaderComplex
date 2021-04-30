package com.enderzombi102.loadercomplex.modloader;

import com.enderzombi102.loadercomplex.api.ContentMod;

import java.io.IOException;
import java.nio.file.Path;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

public class Mod {

	public final String mainClass;
	public final Path file;
	public ContentMod implementation;

	public Mod(Path file) throws IOException {
		this.file = file;
		JarFile jarMod = new JarFile( file.toFile(), false );
		Attributes attributes = jarMod.getManifest().getMainAttributes();
		this.mainClass = attributes.getValue("LoaderComplex-Main");
	}
}
