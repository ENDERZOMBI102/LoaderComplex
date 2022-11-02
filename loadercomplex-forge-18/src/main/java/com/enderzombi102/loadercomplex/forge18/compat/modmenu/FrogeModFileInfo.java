package com.enderzombi102.loadercomplex.forge18.compat.modmenu;

import com.enderzombi102.loadercomplex.impl.addon.AddonContainerImpl;
import net.minecraftforge.forgespi.language.IConfigurable;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.locating.IModFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FrogeModFileInfo implements IModFileInfo {
	private final AddonContainerImpl backingAddon;
	private final FrogeModInfo modInfo;
	private final FrogeModFile modFile;

	public FrogeModFileInfo( AddonContainerImpl backingAddon) {
		this.backingAddon = backingAddon;
		this.modInfo = new FrogeModInfo( backingAddon, this );
		this.modFile = new FrogeModFile( backingAddon, this );
	}

	@Override
	public List<IModInfo> getMods() {
		return List.of( this.modInfo );
	}

	@Override
	public List<LanguageSpec> requiredLanguageLoaders() {
		return List.of();
	}

	@Override
	public boolean showAsResourcePack() {
		return false;
	}

	@Override
	public Map<String, Object> getFileProperties() {
		return Map.of();
	}

	@Override
	public String getLicense() {
		return "";
	}

	@Override
	public IModFile getFile() {
		return this.modFile;
	}

	@Override
	public IConfigurable getConfig() {
		return new IConfigurable() {
			public <T> Optional<T> getConfigElement(String... key) {
				return Optional.empty();
			}

			public List<? extends IConfigurable> getConfigList(String... key) {
				return List.of();
			}
		};
	}

	@Override
	public String moduleName() {
		return this.backingAddon.getAddonJar().getName();
	}

	@Override
	public String versionString() {
		return this.backingAddon.getVersion();
	}

	@Override
	public List<String> usesServices() {
		return null;
	}
}
