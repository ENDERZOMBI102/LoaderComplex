package com.enderzombi102.loaderComplex.fabric12.impl.block;

import com.enderzombi102.loadercomplex.abstraction.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class FabricBlock extends net.minecraft.block.Block implements Block {

	public FabricBlock(Material material, MaterialColor color) {
		super(material, color);
	}

	protected FabricBlock(Material material) {
		super(material);
	}

	@Override
	public boolean onUse(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
		this.OnBlockInteracted(player);
		return false;
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, Entity entity) {
		this.OnWalkOn(entity);
	}
}
