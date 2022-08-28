package com.enderzombi102.loadercomplex.forge18;

import com.enderzombi102.loadercomplex.Utils;
import com.enderzombi102.loadercomplex.addonloader.AddonContainerImpl;
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
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ForgeResourcePack extends AbstractFileResourcePack {
	private static final Splitter TYPE_NAMESPACE_SPLITTER = Splitter.on('/').omitEmptyStrings().limit(3);
	private static final Logger LOGGER = LogManager.getLogger("LoaderComplex | ResourceManager");
	// https://minecraft.fandom.com/wiki/Tutorials/Creating_a_resource_pack#.22pack_format.22
	private static final int PACK_FORMAT_VERSION = 8; // format for 1.6.1 – 1.8.9
	private final List<AddonContainerImpl> containers;

	public ForgeResourcePack(List<AddonContainerImpl> containers) {
		super( null );
		this.containers = containers;
	}

	@Override
	protected InputStream openFile(String filename) throws IOException {
		LOGGER.info("Minecraft is opening \"" + filename + "\"");
		for ( var container : containers ) {
			// try to get the entry
			final JarEntry jarEntry = container.getAddonJar().getJarEntry(filename);
			if ( jarEntry == null ) {
				// no entry, maybe it's a fake file?
				if ( "pack.mcmeta".equals(filename) ) {
					// fake file, return a "custom" entry
					return IOUtils.toInputStream(
						Utils.format(
							"{\"pack\":{\"pack_format\":{},\"description\":\"Resources for {} addon{}\"}}",
							PACK_FORMAT_VERSION,
							containers.size(),
							containers.size() == 1 ? "" : "s"
						),
						Charsets.UTF_8
					);
				} else if (
					filename.contains("lang") &&
					filename.endsWith(".json") &&
					container.getAddonJar().getEntry( filename.replace("json", "lang") ) != null
				) {
					// converts a .lang file to .json
					var lines = IOUtils.readLines(
						new InputStreamReader(
							container.getAddonJar().getInputStream(
								container.getAddonJar().getJarEntry( filename.replace("json", "lang") )
							)
						)
					);
					var lang = new StringBuilder("{");
					for ( var line : lines ) {
						var parts = line.split("=", 2);
						lang.append( Utils.format(
							"\"{}\": \"{}\",",
							parts[0].replace("tile", "block")
									.replace(".name", ""),
							parts[1]
						));
					}
					var data = lang.substring( 0, lang.length() - 1 ) + "}";
					LOGGER.debug( Utils.format( "--- START {} LANG JSON ----", container.getId() ) );
					LOGGER.debug( data );
					LOGGER.debug( Utils.format( "--- END {} LANG JSON ----", container.getId() ) );

					return IOUtils.toInputStream( data, Charsets.UTF_8);
				}
			} else {
				return container.getAddonJar().getInputStream(jarEntry);
			}
		}
		throw new ResourceNotFoundException( Path.of("LoaderComplexResources.zip").toFile(), filename );
	}

	@Override
	public String getName() {
		return "LoaderComplex Resources";
	}

	@Override
	public void close() { }

	@Override
	protected boolean containsFile(String filename) {
		boolean hasFile =  filename.equals("pack.mcmeta") ||
			( filename.contains("lang") && filename.endsWith(".json") ) ||
			containers.stream().anyMatch( container -> container.getAddonJar().getEntry( filename ) != null );
		LOGGER.info( "Minecraft is searching for \"{}\" (found: {})", filename, hasFile );
		return hasFile;
	}

	@Override
	public Collection<Identifier> findResources( ResourceType type, String namespace, String prefix, int maxDepth, Predicate<String> pathFilter ) {
		List<Identifier> ids = new ArrayList<>();

		for ( var container : containers ) {
			Path namespacePath = container.getPath().resolve("assets").resolve(namespace).toAbsolutePath().normalize();

			if ( Files.exists( namespacePath ) ) {
				try {
					//noinspection resource
					Files.walk( namespacePath, maxDepth )
							.filter(Files::isRegularFile)
							.filter(p -> {
								String filename = p.getFileName().toString();
								return !filename.endsWith(".mcmeta") && pathFilter.test(filename);
							})
							.map(namespacePath::relativize)
							.map(Path::toString)
							.forEach(s -> {
								try {
									ids.add(new Identifier(namespace, s));
								} catch (InvalidIdentifierException e) {
									LOGGER.error(e.getMessage());
								}
							});
				} catch (IOException e) {
					LOGGER.warn("findResources at " + namespacePath + " in namespace " + namespace + ", addon " + container.getId() + " failed!", e);
				}
			}
		}
		return ids;
	}

	@Override
	public Set<String> getNamespaces(ResourceType type) {
		return containers.stream()
			.map( AddonContainerImpl::getAddonJar )
			.map( JarFile::entries )
			.flatMap( entries -> Collections.list( entries ).stream() )
			.filter( JarEntry::isDirectory )
			.map( JarEntry::getName )
			.filter( path -> path.startsWith("assets/") )
			.map( path -> {
				List<String> parts = Lists.newArrayList( TYPE_NAMESPACE_SPLITTER.split(path) );
				if ( parts.size() > 1 ) {
					String namespace = parts.get(1);
					if ( namespace.equals( namespace.toLowerCase(Locale.ROOT) ) ) {
						return namespace;
					} else {
						LOGGER.warn( "ResourcePack: ignored non-lowercase namespace: {}", namespace );
					}
				}
				return null;
			} )
			.filter( Objects::nonNull )
			.collect( Collectors.toSet() );
	}
}
