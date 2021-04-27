package com.enderzombi102.loaderComplex.fabric12;

import com.enderzombi102.loaderComplex.fabric12.impl.FabricLoader;
import com.enderzombi102.loadercomplex.abstraction.ContentMod;
import com.enderzombi102.loadercomplex.testmod.TestMod;
import net.fabricmc.api.ModInitializer;
import net.minecraft.class_6055;
import net.minecraft.class_6057;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ZipResourcePack;
import net.minecraft.util.Identifier;

import javax.annotation.Nullable;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;

public class LoaderComplexFabric implements ModInitializer {

	private final ArrayList<ContentMod> mods = new ArrayList<>();
	public FabricLoader loader = new FabricLoader();

	@Override
	public void onInitialize() {
		mods.add( new TestMod() );
		for ( ContentMod mod : mods ) mod.init( this.loader );
	}
}
