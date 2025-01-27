package com.enderzombi102.loadercomplex.fabric12;

import com.enderzombi102.loadercomplex.api.addon.AddonResourcePack;
import com.enderzombi102.loadercomplex.impl.addon.AddonContainerImpl;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import net.minecraft.client.resource.ResourceNotFoundException;
import net.minecraft.client.resource.pack.CustomResourcePack;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;


// FIXME: for some reason only block textures and translations are loaded from addons, no item textures
public class FabricResourcePack extends CustomResourcePack implements AddonResourcePack {
	private static final Splitter TYPE_NAMESPACE_SPLITTER = Splitter.on( '/' ).omitEmptyStrings().limit( 3 );
	private static final Logger LOGGER = LoggerFactory.getLogger( "LoaderComplex|ResourceManager" );
	// https://minecraft.fandom.com/wiki/Tutorials/Creating_a_resource_pack#.22pack_format.22
	private static final int PACK_FORMAT_VERSION = 1; // format for 1.6.1 – 1.8.9
	private final AddonContainerImpl container;

	public FabricResourcePack( AddonContainerImpl container ) {
		super( container.getPath().toFile() );
		this.container = container;
	}

	public String getAddonID() {
		return container.getId();
	}

	@Override
	protected InputStream openResource( String filename ) throws IOException {
		LOGGER.info( "Minecraft is opening \"{}\"", filename );
		// try to get the entry
		final JarEntry jarEntry = this.container.getAddonJar().getJarEntry( filename );
		if ( jarEntry == null ) {
			// no entry, maybe it's a fake file?
			if ( "pack.mcmeta".equals( filename ) ) {
				// fake file, return a "custom" entry
				return IOUtils.toInputStream(
					String.format(
						"{\"pack\":{\"pack_format\":%s,\"description\":\"%s\"}}",
						PACK_FORMAT_VERSION,
						this.container.getName().replaceAll( "\"", "\\\"" )
					),
					Charsets.UTF_8
				);
			}
			throw new ResourceNotFoundException( this.container.getPath().toFile(), filename );
		} else {
			return this.container.getAddonJar().getInputStream( jarEntry );
		}
	}

	@Override
	public String getName() {
		return "LoaderComplex addon " + this.container.getName();
	}

	@Override
	protected boolean hasResource( String filename ) {
		boolean hasFile = this.container.getAddonJar().getEntry( filename ) != null || filename.equals( "pack.mcmeta" );
		LOGGER.info( "Minecraft is searching for \"{}\" (found: {})", filename, hasFile );
		return hasFile;
	}

	@Override
	public Set<String> getNamespaces() {
		Set<String> set = new HashSet<>();

		for ( JarEntry jarEntry : Collections.list( this.container.getAddonJar().entries() ) ) {
			// ignore files
			if ( !jarEntry.isDirectory() )
				continue;

			String path = jarEntry.getName();
			if ( path.startsWith( "assets/" ) ) {
				List<String> parts = Lists.newArrayList( TYPE_NAMESPACE_SPLITTER.split( path ) );
				if ( parts.size() > 1 ) {
					String namespace = parts.get( 1 );
					if ( namespace.equals( namespace.toLowerCase( Locale.ROOT ) ) ) {
						set.add( namespace );
					} else {
						warnNonLowercaseNamespace( namespace );
					}
				}
			}
		}

		return set;
	}
}
