package com.enderzombi102.loadercomplex.forge12.impl.world;

import com.enderzombi102.loadercomplex.api.minecraft.block.Blockstate;
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity;
import com.enderzombi102.loadercomplex.api.minecraft.entity.Player;
import com.enderzombi102.loadercomplex.api.minecraft.util.*;
import com.enderzombi102.loadercomplex.api.minecraft.world.World;
import com.enderzombi102.loadercomplex.forge12.impl.utils.ForgeServer;
import com.enderzombi102.loadercomplex.forge12.impl.block.ForgeBlockstate;
import com.enderzombi102.loadercomplex.forge12.impl.utils.BlockUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ForgeWorld implements World {
	private final net.minecraft.world.World backingWorld;

	public ForgeWorld(net.minecraft.world.World world) {
		this.backingWorld = world;
	}

	@Override
	public void spawn(Entity entity, Position pos) {
		entity.setPosition( pos );
		this.backingWorld.spawnEntity( (net.minecraft.entity.Entity) entity.getObject() );
	}

	@Override
	public boolean isClient() {
		return this.backingWorld.isRemote;
	}

	@Override
	public boolean isHardcore() {
		return false;
	}

	@Override
	public boolean isDay() {
		return this.backingWorld.isDaytime();
	}

	@Override
	public boolean isRaining() {
		return this.backingWorld.isRaining();
	}

	@Override
	public boolean isThundering() {
		return this.backingWorld.isThundering();
	}

	@Override
	public boolean isAir(Position pos) {
		return this.backingWorld.isAirBlock( new BlockPos( pos.x, pos.y, pos.z ) );
	}

	@Override
	public boolean isPositionLoaded(Position pos) {
		return this.backingWorld.isBlockLoaded( new BlockPos( pos.x, pos.y, pos.z ) );
	}

	@Override
	public boolean hasBlockEntity(Position pos) {
		return this.backingWorld.getTileEntity( new BlockPos( pos.x, pos.y, pos.z ) ) != null;
	}

	@Override
	public boolean canSeeTheSky(Position pos) {
		return this.backingWorld.canSeeSky( new BlockPos( pos.x, pos.y, pos.z ) );
	}

	@Override
	public boolean canSnow(Position pos) {
		return this.backingWorld.canSnowAt( new BlockPos( pos.x, pos.y, pos.z ), true );
	}

	@Override
	public Blockstate getBlockState(Position pos) {
		return new ForgeBlockstate( this.backingWorld.getBlockState( new BlockPos( pos.x, pos.y, pos.z ) ) );
	}

	@Override
	public void setBlockState(Position pos, Blockstate state) {
		this.backingWorld.setBlockState( new BlockPos( pos.x, pos.y, pos.z ), (IBlockState) state.getObject() );
	}

	@Override
	public void removeBlock(Position pos) {
		this.backingWorld.setBlockToAir( new BlockPos( pos.x, pos.y, pos.z ) );
	}

	@Override
	public Server getServer() {
		return new ForgeServer( this.backingWorld.getMinecraftServer() );
	}

	@Override
	public Position getSpawnLocation() {
		return BlockUtils.toPosition( this.backingWorld.getSpawnPoint() );
	}

	@Override
	public Difficulty getDifficulty() {
		return Difficulty.valueOf( this.backingWorld.getDifficulty().name() );
	}

	@Override
	public int getRedstonePower(Position pos, Direction direction) {
		return this.backingWorld.getRedstonePower( new BlockPos( pos.x, pos.y, pos.z ), EnumFacing.valueOf( direction.name() ) );
	}

	@Override
	public void playsound( Player player, double x, double y, double z, ResourceIdentifier sound, float volume, float pitch) {
		SoundEvent event = ForgeRegistries.SOUND_EVENTS.getValue( new ResourceLocation( sound.getNamespace(), sound.getPath() ) );

		if ( event == null )
			throw new IllegalArgumentException( String.format( "SoundEvent %s was not found!", sound ) );

		this.backingWorld.playSound(
			(EntityPlayer) player.getObject(),
			x, y, z,
			event,
			SoundCategory.MASTER,
			volume,
			pitch
		);
	}

	@Override
	public Object getObject() {
		return this.backingWorld;
	}
}
