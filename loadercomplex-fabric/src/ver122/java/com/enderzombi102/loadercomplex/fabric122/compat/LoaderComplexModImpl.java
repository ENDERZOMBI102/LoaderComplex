package com.enderzombi102.loadercomplex.fabric122.compat;

import com.enderzombi102.loadercomplex.api.addon.AddonContainer;
//import com.terraformersmc.modmenu.ModMenu;
//import com.terraformersmc.modmenu.util.mod.Mod;
//import com.terraformersmc.modmenu.util.mod.ModrinthData;
//import com.terraformersmc.modmenu.util.mod.fabric.FabricIconHandler;


public class LoaderComplexModImpl /*implements Mod*/ {
	private final AddonContainer container;

	public LoaderComplexModImpl( AddonContainer container ) {
		this.container = container;
	}

	/*@Override
	public @NotNull String getId() {
		return container.getId();
	}

	@Override
	public @NotNull String getName() {
		return container.getName();
	}

	@Override
	public @NotNull DynamicTexture getIcon( FabricIconHandler iconHandler, int i ) {
		if ( container.getIconPath() == null ) {
			LoaderComplexFabric.LOGGER.warn( "Addon {} has no icon! using default.", container.getId() );
			return getDefaultIcon( iconHandler );
		}

		JarEntry entry = container.getAddonJar().getJarEntry( container.getIconPath() );
		if ( entry == null ) {
			LoaderComplexFabric.LOGGER.warn( "Addon {} has an invalid icon! using default.", container.getId() );
			return getDefaultIcon( iconHandler );
		}

		try {
			InputStream inputStream = container.getAddonJar().getInputStream( entry );
			BufferedImage image = ImageIO.read( Objects.requireNonNull( inputStream ) );
			Validate.validState( image.getHeight() == image.getWidth(), "Must be square icon" );
			return new DynamicTexture( image );
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
		return container.getDescription().isEmpty() ? "No description provided" : container.getDescription();
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
		return Collections.singletonList( container.getAuthors() );
	}

	@Override
	public @NotNull List<String> getContributors() {
		return Collections.emptyList();
	}

	@Override
	public @NotNull List<String> getCredits() {
		return Collections.emptyList();
	}

	@Override
	public @NotNull Set<Badge> getBadges() {
		return Sets.newHashSet( Badge.MODPACK ); // Badge.of( "loadercomplex" );
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
		return Collections.emptySet();
	}

	@Override
	public @NotNull Map<String, String> getLinks() {
		return container.getLinks();
	}

	@Override
	public boolean isReal() {
		return false;
	}

	@Override
	public @Nullable ModrinthData getModrinthData() {
		return null;
	}

	@Override
	public boolean allowsUpdateChecks() {
		return false;
	}

	@Override
	public void setModrinthData( ModrinthData modrinthData ) {

	}

	@Override
	public void setChildHasUpdate() {

	}

	@Override
	public boolean getChildHasUpdate() {
		return false;
	}

	@Override
	public boolean isHidden() {
		return false;
	}

	private static DynamicTexture getDefaultIcon( FabricIconHandler iconHandler ) {
		return iconHandler.createIcon(
			FabricLoader.getInstance()
				.getModContainer( ModMenu.MOD_ID )
				.orElseThrow( () -> new RuntimeException( "Cannot get ModContainer for Fabric mod with id " + ModMenu.MOD_ID ) ),
			"assets/" + ModMenu.MOD_ID + "/unknown_icon.png"
		);
	}*/
}
