package com.enderzombi102.loadercomplex.forge12;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.ResourcePackFileNotFoundException;
import net.minecraftforge.fml.common.ModContainer;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;

public class ForgeResourcePack extends AbstractResourcePack {
	private static final Splitter TYPE_NAMESPACE_SPLITTER = Splitter.on( '/' ).omitEmptyStrings().limit( 3 );
	private static final Logger LOGGER = LogManager.getLogger( "LoaderComplex|ResourceManager" );
	private static final int PACK_FORMAT_VERSION = 1;
	private final FrogeModContainer container;

	public ForgeResourcePack( ModContainer container ) {
		super( ((FrogeModContainer) container).getAddon().getPath().toFile() );
		this.container = (FrogeModContainer) container;
	}

	public String getAddonID() {
		return container.getModId();
	}

	@Override
	protected @NotNull InputStream getInputStreamByName( @NotNull String filename ) throws IOException {
		LOGGER.info( "Minecraft is opening \"{}\"", filename );
		// try to get the entry
		final JarEntry jarEntry = container.getAddon().getAddonJar().getJarEntry( filename );
		if ( jarEntry == null ) {
			// no entry, maybe is it a fake file?
			if ( "pack.mcmeta".equals( filename ) ) {
				// fake file, return a "custom" entry
				return IOUtils.toInputStream(
					String.format(
						"{\"pack\":{\"pack_format\":%s,\"description\":\"%s\"}}",
						PACK_FORMAT_VERSION,
						container.getName().replaceAll( "\"", "\\\"" )
					),
					Charsets.UTF_8
				);
			}
			throw new ResourcePackFileNotFoundException( resourcePackFile, filename );
		} else {
			return container.getAddon().getAddonJar().getInputStream( jarEntry );
		}
	}

	@Override
	public @NotNull String getPackName() {
		return "LoaderComplex addon " + container.getName();
	}

	@Override
	protected boolean hasResourceName( @NotNull String filename ) {
		boolean hasFile = container.getAddon().getAddonJar().getEntry( filename ) != null || filename.equals( "pack.mcmeta" );
		LOGGER.info( "Minecraft is searching for \"{}\" (found: {})", filename, hasFile );
		return hasFile;
	}

	@Override
	public @NotNull Set<String> getResourceDomains() {
		HashSet<String> set = new HashSet<>();

		for ( JarEntry jarEntry : Collections.list( container.getAddon().getAddonJar().entries() ) ) {
			// ignore files
			if (! jarEntry.isDirectory() ) {
				continue;
			}

			String path = jarEntry.getName();
			if ( path.startsWith( "assets/" ) ) {
				List<String> parts = Lists.newArrayList( TYPE_NAMESPACE_SPLITTER.split( path ) );
				if ( parts.size() > 1 ) {
					String namespace = parts.get( 1 );
					if ( namespace.equals( namespace.toLowerCase( Locale.ROOT ) ) ) {
						set.add( namespace );
					} else {
						this.logNameNotLowercase( namespace );
					}
				}
			}
		}

		return set;
	}
}
