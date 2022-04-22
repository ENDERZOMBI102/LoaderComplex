package com.enderzombi102.loadercomplex.forge18.compat.modmenu;

import com.enderzombi102.loadercomplex.modloader.AddonContainer;
import cpw.mods.jarhandling.SecureJar;
import net.minecraftforge.forgespi.language.*;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.forgespi.locating.IModLocator;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class FrogeModFile implements IModFile {
	private final AddonContainer container;
	private final FrogeModFileInfo modFileInfo;

	FrogeModFile(AddonContainer container, FrogeModFileInfo modFileInfo) {
		this.container = container;
		this.modFileInfo = modFileInfo;
	}

	@Override
	public Supplier<Map<String, Object>> getSubstitutionMap() {
		return null;
	}

	@Override
	public Type getType() {
		return null;
	}

	@Override
	public Path getFilePath() {
		return this.container.getPath();
	}

	@Override
	public SecureJar getSecureJar() {
		return null;
	}

	@Override
	public List<IModInfo> getModInfos() {
		return this.modFileInfo.getMods();
	}

	@Override
	public ModFileScanData getScanResult() {
		return new ModFileScanData() {{
			this.addModFileInfo( FrogeModFile.this.modFileInfo );
		}};
	}

	@Override
	public List<IModLanguageProvider> getLoaders() {
		return null;
	}

	@Override
	public Path findResource(String... pathName) {
		return this.container.getPath().resolve( String.join( "/", pathName ) );
	}

	@Override
	public void setSecurityStatus(SecureJar.Status status) { }

	@Override
	public String getFileName() {
		return this.container.getAddonJar().getName();
	}

	@Override
	public IModLocator getLocator() {
		return null;
	}

	@Override
	public IModFileInfo getModFileInfo() {
		return this.modFileInfo;
	}
}
