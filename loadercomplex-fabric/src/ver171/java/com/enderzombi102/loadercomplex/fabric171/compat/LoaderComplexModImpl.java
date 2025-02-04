package com.enderzombi102.loadercomplex.fabric171.compat;

import com.enderzombi102.loadercomplex.api.addon.AddonContainer;
import com.enderzombi102.loadercomplex.impl.LoaderComplex;
import com.terraformersmc.modmenu.ModMenu;
import com.terraformersmc.modmenu.util.mod.Mod;
import com.terraformersmc.modmenu.util.mod.ModIconHandler;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;

public class LoaderComplexModImpl implements Mod {
	private final AddonContainer container;

	public LoaderComplexModImpl( AddonContainer container ) {
		this.container = container;
	}

	@Override
	public @NotNull String getId() {
		return this.container.getId();
	}

	@Override
	public @NotNull String getName() {
		return this.container.getName();
	}

	@Override
	public @NotNull NativeImageBackedTexture getIcon( ModIconHandler iconHandler, int i ) {
		if ( this.container.getIconPath() == null ) {
			LoaderComplex.get().getLogger().warn( "Addon {} has no icon! using default.", this.container.getId() );
			return getDefaultIcon( iconHandler );
		}

		JarEntry entry = this.container.getAddonJar().getJarEntry( this.container.getIconPath() );
		if ( entry == null ) {
			LoaderComplex.get().getLogger().warn( "Addon {} has an invalid icon! using default.", this.container.getId() );
			return getDefaultIcon( iconHandler );
		}

		try {
			InputStream inputStream = this.container.getAddonJar().getInputStream( entry );
			NativeImage image = NativeImage.read( Objects.requireNonNull( inputStream ) );
			Validate.validState( image.getHeight() == image.getWidth(), "Must be square icon" );
			return new NativeImageBackedTexture( image );
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
	}

	@Override
	public @NotNull String getSummary() {
		return "A LoaderComplex addon";
	}

	@Override
	public @NotNull String getDescription() {
		return this.container.getDescription().isEmpty() ? "No description provided" : this.container.getDescription();
	}

	@Override
	public @NotNull String getVersion() {
		return this.container.getVersion();
	}

	@Override
	public @NotNull String getPrefixedVersion() {
		return this.container.getVersion();
	}

	@Override
	public @NotNull List<String> getAuthors() {
		return Collections.singletonList( this.container.getAuthors() );
	}

	@Override
	public @NotNull List<String> getContributors() {
		return Collections.emptyList();
	}

	@Override
	public @NotNull Set<Badge> getBadges() {
		return Collections.emptySet();
	}

	@Override
	public @Nullable String getWebsite() {
		return this.container.getLinks().get( "website" );
	}

	@Override
	public @Nullable String getIssueTracker() {
		return this.container.getLinks().get( "issues" );
	}

	@Override
	public @Nullable String getSource() {
		return this.container.getLinks().get( "source" );
	}

	@Override
	public @Nullable String getParent() {
		return "loadercomplex";
	}

	@Override
	public @NotNull Set<String> getLicense() {
		return Collections.emptySet();
	}

	@Override
	public @NotNull Map<String, String> getLinks() {
		return this.container.getLinks();
	}

	@Override
	public boolean isReal() {
		return false;
	}

	private static NativeImageBackedTexture getDefaultIcon( ModIconHandler iconHandler ) {
		return iconHandler.createIcon(
			FabricLoader.getInstance().getModContainer( ModMenu.MOD_ID ).orElseThrow( IllegalStateException::new ),
			String.format( "assets/%s/unknown_icon.png", ModMenu.MOD_ID )
		);
	}
}
