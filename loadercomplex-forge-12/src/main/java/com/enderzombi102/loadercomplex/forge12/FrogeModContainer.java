package com.enderzombi102.loadercomplex.forge12;

import com.enderzombi102.loadercomplex.modloader.AddonContainer;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.eventbus.EventBus;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.MetadataCollection;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;
import net.minecraftforge.fml.common.versioning.VersionRange;
import org.jetbrains.annotations.Nullable;
import scala.actors.threadpool.Arrays;

import java.io.File;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FrogeModContainer implements ModContainer {
	private final AddonContainer backingAddon;
	private ModMetadata meta;
	private int classVersion;

	public FrogeModContainer(AddonContainer backingAddon) {
		this.backingAddon = backingAddon;
	}

	@Override
	public String getModId() {
		return backingAddon.getId();
	}

	@Override
	public String getName() {
		return backingAddon.getName();
	}

	@Override
	public String getVersion() {
		return backingAddon.getVersion();
	}

	@Override
	public File getSource() {
		return backingAddon.getPath().toFile();
	}

	@Override
	public ModMetadata getMetadata() {
		if ( meta == null ) {
			meta = new ModMetadata();
			meta.autogenerated = true;
			meta.modId = backingAddon.getId();
			meta.name = backingAddon.getName();
			//noinspection unchecked
			meta.authorList = Arrays.asList( backingAddon.getAuthors().split(",") );
			meta.parent = "loadercomplex";
			meta.parentMod = LoaderComplexForge.INSTANCE.getContainer();
		}
		return meta;
	}

	@Override
	public void bindMetadata(MetadataCollection mc) { }

	@Override
	public void setEnabledState(boolean enabled) { }

	@Override
	public Set<ArtifactVersion> getRequirements() {
		return Sets.newHashSet();
	}

	@Override
	public List<ArtifactVersion> getDependencies() {
		return Lists.newArrayList();
	}

	@Override
	public List<ArtifactVersion> getDependants() {
		return Lists.newArrayList();
	}

	@Override
	public String getSortingRules() {
		return "";
	}

	@Override
	public boolean registerBus( @SuppressWarnings("UnstableApiUsage") EventBus bus, LoadController controller ) {
		return false;
	}

	@Override
	public boolean matches(Object mod) {
		return backingAddon.getImplementation() == mod;
	}

	@Override
	public Object getMod() {
		return backingAddon.getImplementation();
	}

	@Override
	public ArtifactVersion getProcessedVersion() {
		return new DefaultArtifactVersion( backingAddon.getVersion() );
	}

	@Override
	public boolean isImmutable() {
		return true;
	}

	@Override
	public String getDisplayVersion() {
		return backingAddon.getVersion();
	}

	@Override
	public VersionRange acceptableMinecraftVersionRange() {
		return VersionRange.newRange( new DefaultArtifactVersion("1.12.2"), Lists.newArrayList() );
	}

	@Nullable
	@Override
	public Certificate getSigningCertificate() {
		return null;
	}

	@Override
	public Map<String, String> getCustomModProperties() {
		return Maps.newHashMap();
	}

	@Override
	public Class<?> getCustomResourcePackClass() {
		return ForgeResourcePack.class;
	}

	@Override
	public Map<String, String> getSharedModDescriptor() {
		return null;
	}

	@Override
	public Disableable canBeDisabled() {
		return Disableable.NEVER;
	}

	@Override
	public String getGuiClassName() {
		return null;
	}

	@Override
	public List<String> getOwnedPackages() {
		return null;
	}

	@Override
	public boolean shouldLoadInEnvironment() {
		return true;
	}

	@Override
	public URL getUpdateUrl() {
		return null;
	}

	@Override
	public void setClassVersion(int classVersion) {
		this.classVersion = classVersion;
	}

	@Override
	public int getClassVersion() {
		return classVersion;
	}

	public AddonContainer getAddon() {
		return backingAddon;
	}
}
