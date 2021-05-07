package com.enderzombi102.loaderComplex.fabric12;

import com.enderzombi102.loadercomplex.modloader.Mod;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.resource.AbstractFileResourcePack;
import net.minecraft.resource.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FabricResourcePack extends AbstractFileResourcePack {

	private static final Splitter TYPE_NAMESPACE_SPLITTER = Splitter.on('/').omitEmptyStrings().limit(3);
	private static final Logger LOGGER = LogManager.getLogger("LC-PackManager");
	private final Mod mod;

	public FabricResourcePack(Mod mod) {
		super( mod.getPath().toFile() );
		this.mod = mod;
	}

	public String getModID() {
		return this.mod.getID();
	}

	@Override
	protected InputStream openFile(String name) throws IOException {
		LOGGER.info("Minecraft is trying to access \"" + name + "\"");
		final JarEntry jarEntry = this.mod.getJarMod().getJarEntry(name);
		if (jarEntry == null) {
			throw new ResourceNotFoundException(this.base, name);
		} else {
			return this.mod.getJarMod().getInputStream(jarEntry);
		}
	}

	@Override
	protected boolean containsFile(String name) {
		return this.mod.getJarMod().getEntry(name) != null;
	}

	public Set<String> method_31465() {

		JarFile jarFile = this.mod.getJarMod();

		Enumeration<? extends JarEntry> enumeration = jarFile.entries();
		HashSet<String> set = Sets.newHashSet();

		while( enumeration.hasMoreElements() ) {
			JarEntry jarEntry = enumeration.nextElement();
			String path = jarEntry.getName();
			if ( path.startsWith("assets/") ) {
				List<String> parts = Lists.newArrayList( TYPE_NAMESPACE_SPLITTER.split(path) );
				if (parts.size() > 1) {
					String namespace = parts.get(1);
					if ( namespace.equals( namespace.toLowerCase(Locale.ROOT) ) ) {
						set.add(namespace);
					} else {
						this.warnNonLowercaseNamespace(namespace);
					}
				}
			}
		}

		return set;
	}

}
