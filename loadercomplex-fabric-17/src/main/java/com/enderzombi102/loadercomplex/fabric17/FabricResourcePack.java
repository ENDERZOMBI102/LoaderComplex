package com.enderzombi102.loadercomplex.fabric17;

import com.enderzombi102.loadercomplex.Utils;
import com.enderzombi102.loadercomplex.modloader.AddonContainer;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import net.minecraft.resource.AbstractFileResourcePack;
import net.minecraft.resource.ResourceNotFoundException;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.jar.JarEntry;

public class FabricResourcePack extends AbstractFileResourcePack {
	private static final Splitter TYPE_NAMESPACE_SPLITTER = Splitter.on('/').omitEmptyStrings().limit(3);
	private static final Logger LOGGER = LogManager.getLogger("LC-PackManager");
	// https://minecraft.fandom.com/wiki/Tutorials/Creating_a_resource_pack#.22pack_format.22
	private static final int PACK_FORMAT_VERSION = 1; // format for 1.6.1 – 1.8.9
	private final AddonContainer container;

	public FabricResourcePack(AddonContainer container) {
		super( container.getPath().toFile() );
		this.container = container;
	}

	public String getAddonID() {
		return container.getID();
	}

	@Override
	protected InputStream openFile(String filename) throws IOException {
		LOGGER.info("Minecraft is opening \"" + filename + "\"");
		// try to get the entry
		final JarEntry jarEntry = container.getAddonJar().getJarEntry(filename);
		if (jarEntry == null) {
			// no entry, maybe its a fake file?
			if ( "pack.mcmeta".equals(filename) ) {
				// fake file, return a "custom" entry
				return IOUtils.toInputStream(
					Utils.format(
						"{\"pack\":{\"pack_format\":{},\"description\":\"{}\"}}",
						PACK_FORMAT_VERSION,
						container.getName().replaceAll("\"", "\\\"")
					),
					Charsets.UTF_8
				);
			}
			throw new ResourceNotFoundException(base, filename);
		} else if ( filename.contains("lang") && filename.endsWith(".json") ) {
			var lang = new StringBuilder("{\n");
			for ( var line : IOUtils.readLines( new InputStreamReader( container.getAddonJar().getInputStream( container.getAddonJar().getJarEntry( filename.replace("json", "lang") ) ) ) ) ) {
				var parts = line.split("=", 1);
				lang.append( Utils.format( "\t\"{}\": \"{}\"\n", (Object[]) parts ) );
			}
			return IOUtils.toInputStream( lang + "}", Charsets.UTF_8 );
		} else {
			return container.getAddonJar().getInputStream(jarEntry);
		}
	}

	@Override
	public String getName() {
		return "LoaderComplex addon " + container.getName();
	}

	@Override
	public void close() { }

	@Override
	protected boolean containsFile(String filename) {
		boolean hasFile = container.getAddonJar().getEntry(filename) != null || filename.equals("pack.mcmeta");
		LOGGER.info("Minecraft is searching for \"{}\" (found: {})", filename, hasFile);
		return hasFile;
	}

	@Override
	public Collection<Identifier> findResources(ResourceType type, String namespace, String prefix, int maxDepth, Predicate<String> pathFilter) {
		List<Identifier> ids = new ArrayList<>();
		Path namespacePath = this.container.getPath().resolve("assets").resolve(namespace).toAbsolutePath().normalize();

		if ( Files.exists(namespacePath) ) {
			try {
				Files.walk( namespacePath, maxDepth )
					.filter( Files::isRegularFile )
					.filter( p -> {
						String filename = p.getFileName().toString();
						return !filename.endsWith(".mcmeta") && pathFilter.test(filename);
					})
					.map( namespacePath::relativize )
					.map( Path::toString )
					.forEach( s -> {
						try {
							ids.add( new Identifier( namespace, s ) );
						} catch (InvalidIdentifierException e) {
							LOGGER.error(e.getMessage());
						}
					});
			} catch (IOException e) {
				LOGGER.warn( "findResources at " + namespacePath + " in namespace " + namespace + ", addon " + container.getID() + " failed!", e );
			}
		}

		return ids;
	}

	@Override
	public Set<String> getNamespaces(ResourceType type) {
		Set<String> set = new HashSet<>();

		for ( JarEntry jarEntry : Collections.list( container.getAddonJar().entries() ) ) {
			// ignore files
			if (! jarEntry.isDirectory() )
				continue;

			String path = jarEntry.getName();
			if ( path.startsWith("assets/") ) {
				List<String> parts = Lists.newArrayList( TYPE_NAMESPACE_SPLITTER.split(path) );
				if (parts.size() > 1) {
					String namespace = parts.get(1);
					if ( namespace.equals( namespace.toLowerCase(Locale.ROOT) ) ) {
						set.add(namespace);
					} else {
						warnNonLowerCaseNamespace(namespace);
					}
				}
			}
		}

		return set;
	}
}
