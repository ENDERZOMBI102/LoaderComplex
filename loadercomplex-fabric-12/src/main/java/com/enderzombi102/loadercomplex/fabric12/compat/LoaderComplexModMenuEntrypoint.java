package com.enderzombi102.loadercomplex.fabric12.compat;

import com.enderzombi102.loadercomplex.fabric.FabricDispatcher;
import com.enderzombi102.loadercomplex.fabric12.LoaderComplexFabric;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import com.enderzombi102.modmenu.api.Badge;
import com.enderzombi102.modmenu.api.Mod;
import com.enderzombi102.modmenu.api.ModMenuApi;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class LoaderComplexModMenuEntrypoint implements ModMenuApi {
	private static final List<LoaderComplexModImpl> mods;

	@Override
	public @NotNull Collection<Mod> getAdditionalMods() {
		return Collections.unmodifiableList( mods ); // needed for some reason
	}

	@Override
	@SuppressWarnings("UnstableApiUsage")
	public @NotNull Multimap<String, String> getAdditionalParents() {
		return mods.stream().collect(
			Multimaps.toMultimap(
				val -> "loadercomplex",
				Mod::getId,
				MultimapBuilder.treeKeys().arrayListValues()::build
			)
		);
	}

	@Override
	public void onSetupBadges() {
		Badge.register( "LoaderComplex", 0xFF00FF, 0x00FF00, "loadercomplex" );
	}

	static {
		if ( FabricDispatcher.getMinecraftVersion().equals( "1.12.2" ) )
			mods = LoaderComplexFabric.INSTANCE.getAddonLoader()
				.getAddons()
				.stream()
				.map( LoaderComplexModImpl::new )
				.collect( Collectors.toList() );
		else
			mods = Collections.emptyList();

	}
}
