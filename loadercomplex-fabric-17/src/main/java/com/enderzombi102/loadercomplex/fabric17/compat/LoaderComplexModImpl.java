package com.enderzombi102.loadercomplex.fabric17.compat;

import com.enderzombi102.loadercomplex.Utils;
import com.enderzombi102.loadercomplex.fabric17.LoaderComplexFabric;
import com.enderzombi102.loadercomplex.modloader.AddonContainer;
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
import java.util.*;

public class LoaderComplexModImpl implements Mod {
	private final AddonContainer container;

	public LoaderComplexModImpl( AddonContainer container ) {
		this.container = container;
	}

	@Override
	public @NotNull String getId() {
		return container.getId();
	}

	@Override
	public @NotNull String getName() {
		return container.getName();
	}

	@Override
	public @NotNull NativeImageBackedTexture getIcon(ModIconHandler iconHandler, int i) {
		if ( container.getIconPath() == null ) {
			LoaderComplexFabric.LOGGER.warn( Utils.format( "Addon {} has no icon! using default.", container.getId() ) );
			return getDefaultIcon(iconHandler);
		}

		var entry = container.getAddonJar().getJarEntry( container.getIconPath() );
		if ( entry == null ) {
			LoaderComplexFabric.LOGGER.warn( Utils.format( "Addon {} has an invalid icon! using default.", container.getId() ) );
			return getDefaultIcon(iconHandler);
		}

		try {
			var inputStream = container.getAddonJar().getInputStream( entry );
			NativeImage image = NativeImage.read( Objects.requireNonNull( inputStream ) );
			Validate.validState(image.getHeight() == image.getWidth(), "Must be square icon");
			return new NativeImageBackedTexture(image);
		} catch (IOException e) { throw new IllegalStateException(e); }
	}

	@Override
	public @NotNull String getSummary() {
		return "A LoaderComplex addon";
	}

	@Override
	public @NotNull String getDescription() {
		return "".equals( container.getDescription() ) ? "No description provided" : container.getDescription();
	}

	@Override
	public @NotNull String getVersion() {
		return container.getVersion();
	}

	@Override
	public @NotNull String getPrefixedVersion() {
		return container.getVersion();
	}

	@Override
	public @NotNull List<String> getAuthors() {
		return List.of( container.getAuthors() );
	}

	@Override
	public @NotNull List<String> getContributors() {
		return List.of();
	}

	@Override
	public @NotNull Set<Badge> getBadges() {
		return Set.of();
	}

	@Override
	public @Nullable String getWebsite() {
		return container.getLinks().get( "website" );
	}

	@Override
	public @Nullable String getIssueTracker() {
		return container.getLinks().get( "issues" );
	}

	@Override
	public @Nullable String getSource() {
		return container.getLinks().get( "source" );
	}

	@Override
	public @Nullable String getParent() {
		return "loadercomplex";
	}

	@Override
	public @NotNull Set<String> getLicense() {
		return Set.of();
	}

	@Override
	public @NotNull Map<String, String> getLinks() {
		return container.getLinks();
	}

	@Override
	public boolean isReal() {
		return false;
	}

	private static NativeImageBackedTexture getDefaultIcon(ModIconHandler iconHandler) {
		return iconHandler.createIcon(
			FabricLoader.getInstance()
				.getModContainer(ModMenu.MOD_ID)
				.orElseThrow( () -> new RuntimeException( "Cannot get ModContainer for Fabric mod with id " + ModMenu.MOD_ID ) ),
			"assets/" + ModMenu.MOD_ID + "/unknown_icon.png"
		);
	}
}
