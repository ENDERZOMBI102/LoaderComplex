package com.enderzombi102.loadercomplex.forge18.compat.modmenu;

import com.enderzombi102.loadercomplex.impl.addonloader.AddonContainerImpl;
import net.minecraftforge.forgespi.language.IConfigurable;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.IModInfo;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FrogeModInfo implements IModInfo {
	private final AddonContainerImpl container;
	private final FrogeModFileInfo modFileInfo;

	FrogeModInfo( AddonContainerImpl container, FrogeModFileInfo modFileInfo) {
		this.container = container;
		this.modFileInfo = modFileInfo;
	}

	@Override
	public IModFileInfo getOwningFile() {
		return this.modFileInfo;
	}

	@Override
	public String getModId() {
		return this.container.getId();
	}

	@Override
	public String getDisplayName() {
		return this.container.getName();
	}

	@Override
	public String getDescription() {
		return this.container.getDescription();
	}

	@Override
	public ArtifactVersion getVersion() {
		return new DefaultArtifactVersion( this.container.getVersion() );
	}

	@Override
	public List<? extends ModVersion> getDependencies() {
		return List.of();
	}

	@Override
	public String getNamespace() {
		return this.container.getId();
	}

	@Override
	public Map<String, Object> getModProperties() {
		return Map.of();
	}

	@Override
	public Optional<URL> getUpdateURL() {
		return Optional.empty();
	}

	@Override
	public Optional<String> getLogoFile() {
		return Optional.ofNullable( this.container.getIconPath() );
	}

	@Override
	public boolean getLogoBlur() {
		return false;
	}

	@Override
	public IConfigurable getConfig() {
		return this.modFileInfo.getConfig();
	}
}
