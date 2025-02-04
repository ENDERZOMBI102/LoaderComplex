package com.enderzombi102.loadercomplex.api.minecraft.item;

import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier;

import static com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier.ri;

public abstract class CreativeTabs {
	private CreativeTabs() { }

	public static final ResourceIdentifier BREWING = ri( "tab.brewing" );
	public static final ResourceIdentifier BUILDING_BLOCKS = ri( "tab.building_blocks" );
	public static final ResourceIdentifier COMBAT = ri( "tab.combat" );
	public static final ResourceIdentifier DECORATIONS = ri( "tab.decorations" );
	public static final ResourceIdentifier FOOD = ri( "tab.food" );
	public static final ResourceIdentifier MATERIALS = ri( "tab.materials" );
	public static final ResourceIdentifier REDSTONE = ri( "tab.redstone" );
	public static final ResourceIdentifier TOOLS = ri( "tab.tools" );
	public static final ResourceIdentifier TRANSPORTATION = ri( "tab.transportation" );
	public static final ResourceIdentifier MISC = ri( "tab.misc" );
}
