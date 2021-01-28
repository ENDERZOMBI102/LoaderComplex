package com.enderzombi102.loadercomplex.forge12.impl.utils;

import com.enderzombi102.loadercomplex.common.abstraction.block.BlockMaterial;
import net.minecraft.block.material.Material;

public class BlockUtils {

	public static BlockMaterial getBLockMat(Material mat) {
		if ( Material.AIR.equals(mat) ) {
			return BlockMaterial.AIR;
		} else if ( Material.ICE.equals(mat) ) {
			return BlockMaterial.ICE;
		} else if ( Material.TNT.equals(mat) ) {
			return BlockMaterial.TNT;
		} else if ( Material.WEB.equals(mat) ) {
			return BlockMaterial.WEB;
		} else if ( Material.CAKE.equals(mat) ) {
			return BlockMaterial.CAKE;
		} else if ( Material.CLAY.equals(mat) ) {
			return BlockMaterial.CLAY;
		} else if ( Material.FIRE.equals(mat) ) {
			return BlockMaterial.FIRE;
		} else if ( Material.IRON.equals(mat) ) {
			return BlockMaterial.IRON;
		} else if ( Material.LAVA.equals(mat) ) {
			return BlockMaterial.LAVA;
		} else if ( Material.SAND.equals(mat) ) {
			return BlockMaterial.SAND;
		} else if ( Material.SNOW.equals(mat) ) {
			return BlockMaterial.SNOW;
		} else if ( Material.VINE.equals(mat) ) {
			return BlockMaterial.VINE;
		} else if ( Material.WOOD.equals(mat) ) {
			return BlockMaterial.WOOD;
		} else if ( Material.ANVIL.equals(mat) ) {
			return BlockMaterial.ANVIL;
		} else if ( Material.CLOTH.equals(mat) ) {
			return BlockMaterial.CLOTH;
		} else if ( Material.CORAL.equals(mat) ) {
			return BlockMaterial.CORAL;
		} else if ( Material.GLASS.equals(mat) ) {
			return BlockMaterial.GLASS;
		} else if ( Material.GOURD.equals(mat) ) {
			return BlockMaterial.GOURD;
		} else if ( Material.GRASS.equals(mat) ) {
			return BlockMaterial.GRASS;
		} else if ( Material.WATER.equals(mat) ) {
			return BlockMaterial.WATER;
		} else if ( Material.CACTUS.equals(mat) ) {
			return BlockMaterial.CACTUS;
		} else if ( Material.CARPET.equals(mat) ) {
			return BlockMaterial.CARPET;
		} else if ( Material.GROUND.equals(mat) ) {
			return BlockMaterial.GROUND;
		} else if ( Material.LEAVES.equals(mat) ) {
			return BlockMaterial.LEAVES;
		} else if ( Material.PISTON.equals(mat) ) {
			return BlockMaterial.PISTON;
		} else if ( Material.PLANTS.equals(mat) ) {
			return BlockMaterial.PLANTS;
		} else if ( Material.PORTAL.equals(mat) ) {
			return BlockMaterial.PORTAL;
		} else if ( Material.SPONGE.equals(mat) ) {
			return BlockMaterial.SPONGE;
		} else if ( Material.BARRIER.equals(mat) ) {
			return BlockMaterial.BARRIER;
		} else if ( Material.CIRCUITS.equals(mat) ) {
			return BlockMaterial.CIRCUITS;
		} else if ( Material.DRAGON_EGG.equals(mat) ) {
			return BlockMaterial.DRAGON_EGG;
		} else if ( Material.PACKED_ICE.equals(mat) ) {
			return BlockMaterial.PACKED_ICE;
		} else if ( Material.CRAFTED_SNOW.equals(mat) ) {
			return BlockMaterial.CRAFTED_SNOW;
		} else if ( Material.REDSTONE_LIGHT.equals(mat) ) {
			return BlockMaterial.REDSTONE_LIGHT;
		} else if ( Material.STRUCTURE_VOID.equals(mat) ) {
			return BlockMaterial.STRUCTURE_VOID;
		}
		return BlockMaterial.ROCK;
	}

	public static Material getMat(BlockMaterial mat) {
		switch (mat) {
			case AIR:
				return Material.AIR;
			case ICE:
				return Material.ICE;
			case TNT:
				return Material.TNT;
			case WEB:
				return Material.WEB;
			case CAKE:
				return Material.CAKE;
			case CLAY:
				return Material.CLAY;
			case FIRE:
				return Material.FIRE;
			case IRON:
				return Material.IRON;
			case LAVA:
				return Material.LAVA;
			case SAND:
				return Material.SAND;
			case SNOW:
				return Material.SNOW;
			case VINE:
				return Material.VINE;
			case WOOD:
				return Material.WOOD;
			case ANVIL:
				return Material.ANVIL;
			case CLOTH:
				return Material.CLOTH;
			case CORAL:
				return Material.CORAL;
			case GLASS:
				return Material.GLASS;
			case GOURD:
				return Material.GOURD;
			case GRASS:
				return Material.GRASS;
			case WATER:
				return Material.WATER;
			case CACTUS:
				return Material.CACTUS;
			case CARPET:
				return Material.CARPET;
			case GROUND:
				return Material.GROUND;
			case LEAVES:
				return Material.LEAVES;
			case PISTON:
				return Material.PISTON;
			case PLANTS:
				return Material.PLANTS;
			case PORTAL:
				return Material.PORTAL;
			case SPONGE:
				return Material.SPONGE;
			case BARRIER:
				return Material.BARRIER;
			case CIRCUITS:
				return Material.CIRCUITS;
			case DRAGON_EGG:
				return Material.DRAGON_EGG;
			case PACKED_ICE:
				return Material.PACKED_ICE;
			case CRAFTED_SNOW:
				return Material.CRAFTED_SNOW;
			case REDSTONE_LIGHT:
				return Material.REDSTONE_LIGHT;
			case STRUCTURE_VOID:
				return Material.STRUCTURE_VOID;
			default:
				return Material.ROCK;
		}
	}


}
