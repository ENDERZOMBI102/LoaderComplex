package com.enderzombi102.loadercomplex.forge18.impl.utils;

import com.enderzombi102.loadercomplex.minecraft.util.Position;
import net.minecraft.util.math.BlockPos;

public class BlockUtils {

	public static Position toPosition(BlockPos pos) {
		return new Position( pos.getX(), pos.getY(), pos.getZ() );
	}

//	public static BlockMaterial getBLockMat(Material mat) {
//		if ( Material.AIR.equals(mat) ) {
//			return BlockMaterial.AIR;
//		} else if ( Material.ICE.equals(mat) ) {
//			return BlockMaterial.ICE;
//		} else if ( Material.TNT.equals(mat) ) {
//			return BlockMaterial.TNT;
//		} else if ( Material.COBWEB.equals(mat) ) {
//			return BlockMaterial.WEB;
//		} else if ( Material.CAKE.equals(mat) ) {
//			return BlockMaterial.CAKE;
//		} else if ( Material.ORGANIC_PRODUCT.equals(mat) ) {
//			return BlockMaterial.CLAY;
//		} else if ( Material.FIRE.equals(mat) ) {
//			return BlockMaterial.FIRE;
//		} else if ( Material.METAL.equals(mat) ) {
//			return BlockMaterial.IRON;
//		} else if ( Material.LAVA.equals(mat) ) {
//			return BlockMaterial.LAVA;
//		} else if ( Material.AGGREGATE.equals(mat) ) {
//			return BlockMaterial.SAND;
//		} else if ( Material.SNOW_LAYER.equals(mat) ) {
//			return BlockMaterial.SNOW;
//		} else if ( Material.REPLACEABLE_PLANT.equals(mat) ) {
//			return BlockMaterial.VINE;
//		} else if ( Material.WOOD.equals(mat) ) {
//			return BlockMaterial.WOOD;
//		} else if ( Material.REPAIR_STATION.equals(mat) ) {
//			return BlockMaterial.ANVIL;
//		} else if ( Material.WOOL.equals(mat) ) {
//			return BlockMaterial.CLOTH;
//		} else if ( Material.UNUSED_PLANT.equals(mat) ) {
//			return BlockMaterial.CORAL;
//		} else if ( Material.GLASS.equals(mat) ) {
//			return BlockMaterial.GLASS;
//		} else if ( Material.GOURD.equals(mat) ) {
//			return BlockMaterial.GOURD;
//		} else if ( Material.SOLID_ORGANIC.equals(mat) ) {
//			return BlockMaterial.GRASS;
//		} else if ( Material.WATER.equals(mat) ) {
//			return BlockMaterial.WATER;
//		} else if ( Material.CACTUS.equals(mat) ) {
//			return BlockMaterial.CACTUS;
//		} else if ( Material.CARPET.equals(mat) ) {
//			return BlockMaterial.CARPET;
//		} else if ( Material.SOIL.equals(mat) ) {
//			return BlockMaterial.GROUND;
//		} else if ( Material.LEAVES.equals(mat) ) {
//			return BlockMaterial.LEAVES;
//		} else if ( Material.PISTON.equals(mat) ) {
//			return BlockMaterial.PISTON;
//		} else if ( Material.PLANT.equals(mat) ) {
//			return BlockMaterial.PLANTS;
//		} else if ( Material.PORTAL.equals(mat) ) {
//			return BlockMaterial.PORTAL;
//		} else if ( Material.SPONGE.equals(mat) ) {
//			return BlockMaterial.SPONGE;
//		} else if ( Material.BARRIER.equals(mat) ) {
//			return BlockMaterial.BARRIER;
//		} else if ( Material.SUPPORTED.equals(mat) ) {
//			return BlockMaterial.CIRCUITS;
//		} else if ( Material.EGG.equals(mat) ) {
//			return BlockMaterial.DRAGON_EGG;
//		} else if ( Material.DENSE_ICE.equals(mat) ) {
//			return BlockMaterial.PACKED_ICE;
//		} else if ( Material.SNOW_BLOCK.equals(mat) ) {
//			return BlockMaterial.CRAFTED_SNOW;
//		} else if ( Material.REDSTONE_LAMP.equals(mat) ) {
//			return BlockMaterial.REDSTONE_LIGHT;
//		} else if ( Material.STRUCTURE_VOID.equals(mat) ) {
//			return BlockMaterial.STRUCTURE_VOID;
//		}
//		return BlockMaterial.ROCK;
//	}
//
//	public static Material getMat(BlockMaterial mat) {
//		switch (mat) {
//			case AIR:
//				return Material.AIR;
//			case ICE:
//				return Material.ICE;
//			case TNT:
//				return Material.TNT;
//			case WEB:
//				return Material.COBWEB;
//			case CAKE:
//				return Material.CAKE;
//			case CLAY:
//				return Material.ORGANIC_PRODUCT;
//			case FIRE:
//				return Material.FIRE;
//			case IRON:
//				return Material.METAL;
//			case LAVA:
//				return Material.LAVA;
//			case SAND:
//				return Material.AGGREGATE;
//			case SNOW:
//				return Material.SNOW_LAYER;
//			case VINE:
//				return Material.REPLACEABLE_PLANT;
//			case WOOD:
//				return Material.WOOD;
//			case ANVIL:
//				return Material.REPAIR_STATION;
//			case CLOTH:
//				return Material.WOOL;
//			case CORAL:
//				return Material.UNUSED_PLANT;
//			case GLASS:
//				return Material.GLASS;
//			case GOURD:
//				return Material.GOURD;
//			case GRASS:
//				return Material.SOLID_ORGANIC;
//			case WATER:
//				return Material.WATER;
//			case CACTUS:
//				return Material.CACTUS;
//			case CARPET:
//				return Material.CARPET;
//			case GROUND:
//				return Material.SOIL;
//			case LEAVES:
//				return Material.LEAVES;
//			case PISTON:
//				return Material.PISTON;
//			case PLANTS:
//				return Material.PLANT;
//			case PORTAL:
//				return Material.PORTAL;
//			case SPONGE:
//				return Material.SPONGE;
//			case BARRIER:
//				return Material.BARRIER;
//			case CIRCUITS:
//				return Material.SUPPORTED;
//			case DRAGON_EGG:
//				return Material.EGG;
//			case PACKED_ICE:
//				return Material.DENSE_ICE;
//			case CRAFTED_SNOW:
//				return Material.SNOW_BLOCK;
//			case REDSTONE_LIGHT:
//				return Material.REDSTONE_LAMP;
//			case STRUCTURE_VOID:
//				return Material.STRUCTURE_VOID;
//			default:
//				return Material.STONE;
//		}
//	}

}
