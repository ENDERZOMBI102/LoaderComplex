import com.enderzombi102.loadercomplex.api.addon.AddonContainer;
import com.enderzombi102.loadercomplex.impl.LoaderComplex;
import com.terraformersmc.modmenu.ModMenu;
import com.terraformersmc.modmenu.util.mod.Mod;
import com.terraformersmc.modmenu.util.mod.ModrinthData;
import com.terraformersmc.modmenu.util.mod.fabric.FabricIconHandler;
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
	private @Nullable ModrinthData modrinthData = null;
	private boolean childHasUpdate = false;

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
	public @NotNull NativeImageBackedTexture getIcon( FabricIconHandler iconHandler, int i ) {
		if ( container.getIconPath() == null ) {
			LoaderComplex.get().getLogger().warn( "Addon {} has no icon! using default.", container.getId() );
			return getDefaultIcon( iconHandler );
		}

		java.util.jar.JarEntry entry = container.getAddonJar().getJarEntry( container.getIconPath() );
		if ( entry == null ) {
			LoaderComplex.get().getLogger().warn( "Addon {} has an invalid icon! using default.", container.getId() );
			return getDefaultIcon( iconHandler );
		}

		try {
			java.io.InputStream inputStream = container.getAddonJar().getInputStream( entry );
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
		return Collections.emptySet();
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
		return this.modrinthData;
	}

	@Override
	public boolean allowsUpdateChecks() {
		return false;
	}

	@Override
	public void setModrinthData( @Nullable ModrinthData modrinthData ) {
		this.modrinthData = modrinthData;
	}

	@Override
	public void setChildHasUpdate() {
		this.childHasUpdate = true;
	}

	@Override
	public boolean getChildHasUpdate() {
		return this.childHasUpdate;
	}

	private static NativeImageBackedTexture getDefaultIcon( FabricIconHandler iconHandler ) {
		return iconHandler.createIcon(
			FabricLoader.getInstance().getModContainer( ModMenu.MOD_ID ).orElseThrow( IllegalStateException::new ),
			String.format( "assets/%s/unknown_icon.png", ModMenu.MOD_ID )
		);
	}
}
