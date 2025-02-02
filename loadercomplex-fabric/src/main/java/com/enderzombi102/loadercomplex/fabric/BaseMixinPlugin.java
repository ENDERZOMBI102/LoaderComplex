package com.enderzombi102.loadercomplex.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.enderzombi102.loadercomplex.fabric.FabricDispatcher.getMinecraftVersion;

public abstract class BaseMixinPlugin implements IMixinConfigPlugin {
	protected final List<String> clientMixins = new ArrayList<>();
	protected final List<String> commonMixins = new ArrayList<>();
	private final String mcVersion;

	protected BaseMixinPlugin( String mcVersion ) {
		this.mcVersion = mcVersion;
	}

	@Override
	public void onLoad( String mixinPackage ) {
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public void acceptTargets( Set<String> myTargets, Set<String> otherTargets ) {
	}

	@Override
	public void preApply( String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo ) {
	}

	@Override
	public void postApply( String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo ) {
	}

	@Override
	public boolean shouldApplyMixin( String targetClassName, String mixinClassName ) {
		return true;
	}

	@Override
	public List<String> getMixins() {
		if ( this.mcVersion.equals( getMinecraftVersion() ) ) {
			List<String> mixins = new ArrayList<>( this.commonMixins );
			if ( FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT ) {
				mixins.addAll( this.clientMixins );
			}
			return mixins;
		}

		return null;
	}
}
