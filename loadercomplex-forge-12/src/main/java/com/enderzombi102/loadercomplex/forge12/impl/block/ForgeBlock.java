package com.enderzombi102.loadercomplex.forge12.impl.block;


import com.enderzombi102.loadercomplex.abstraction.block.Block;
import com.enderzombi102.loadercomplex.abstraction.utils.ResourceIdentifier;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ForgeBlock extends net.minecraft.block.Block implements Block {

	public ForgeBlock() {
		super( Material.CLOTH );
	}

	public void setIdentifier(ResourceIdentifier id) {
		this.setUnlocalizedName( id.toString() );
	}

	public void setIdentifier(String id) {
		this.setUnlocalizedName( id );
	}

	@Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
		this.OnBlockInteracted(player);
	}

	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entity) {
		this.OnWalkOn(entity);
	}
}
