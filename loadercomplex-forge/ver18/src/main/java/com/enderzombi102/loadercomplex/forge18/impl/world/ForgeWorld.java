package com.enderzombi102.loadercomplex.forge18.impl.world;

import com.enderzombi102.loadercomplex.Utils;
import com.enderzombi102.loadercomplex.api.minecraft.block.Blockstate;
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity;
import com.enderzombi102.loadercomplex.api.minecraft.entity.Player;
import com.enderzombi102.loadercomplex.api.minecraft.util.*;
import com.enderzombi102.loadercomplex.minecraft.util.*;
import com.enderzombi102.loadercomplex.api.minecraft.world.World;
import com.enderzombi102.loadercomplex.forge18.impl.ForgeServer;
import com.enderzombi102.loadercomplex.forge18.impl.block.ForgeBlockstate;
import com.enderzombi102.loadercomplex.forge18.impl.utils.BlockUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

public class ForgeWorld implements World {
	private final net.minecraft.world.World backingWorld;

	public ForgeWorld(net.minecraft.world.World world) {
		backingWorld = world;
	}

	@Override
	public void spawn(Entity entity, Position pos) {
		entity.setPosition( pos );
		this.backingWorld.spawnEntity( (net.minecraft.entity.Entity) entity.getObject() );
	}

	@Override
	public boolean isClient() {
		return this.backingWorld.isClient;
	}

	@Override
	public boolean isHardcore() {
		return false;
	}

	@Override
	public boolean isDay() {
		return this.backingWorld.isDay();
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
		return this.backingWorld.isAir( new BlockPos( pos.x, pos.y, pos.z ) );
	}

	@Override
	public boolean isPositionLoaded(Position pos) {
		return this.backingWorld.isChunkLoaded( new BlockPos( pos.x, pos.y, pos.z ) );
	}

	@Override
	public boolean hasBlockEntity(Position pos) {
		return this.backingWorld.getBlockEntity( new BlockPos( pos.x, pos.y, pos.z ) ) != null;
	}

	@Override
	public boolean canSeeTheSky(Position pos) {
		return this.backingWorld.isSkyVisible( new BlockPos( pos.x, pos.y, pos.z ) );
	}

	@Override
	public boolean canSnow(Position pos) {
		return this.backingWorld
				.getBiome( new BlockPos( pos.x, pos.y, pos.z ) )
				.value()
				.canSetSnow(this.backingWorld, new BlockPos(pos.x, pos.y, pos.z));
	}

	@Override
	public Blockstate getBlockState(Position pos) {
		return new ForgeBlockstate( this.backingWorld.getBlockState( new BlockPos( pos.x, pos.y, pos.z ) ) );
	}

	@Override
	public void setBlockState(Position pos, Blockstate state) {
		this.backingWorld.setBlockState( new BlockPos( pos.x, pos.y, pos.z ), (BlockState) state.getObject() );
	}

	@Override
	public void removeBlock(Position pos) {
		this.backingWorld.removeBlock( new BlockPos( pos.x, pos.y, pos.z ), false );
	}

	@Override
	public Server getServer() {
		return new ForgeServer( this.backingWorld.getServer() );
	}

	@Override
	public Position getSpawnLocation() {
		return BlockUtils.toPosition( this.backingWorld.getTopPosition( Heightmap.Type.WORLD_SURFACE, new BlockPos( 0, 0, 0  ) ) );
	}

	@Override
	public Difficulty getDifficulty() {
		return Difficulty.valueOf( this.backingWorld.getDifficulty().name() );
	}

	@Override
	public int getRedstonePower(Position pos, Direction direction) {
		return this.backingWorld.getEmittedRedstonePower(
			new BlockPos( pos.x, pos.y, pos.z ),
			net.minecraft.util.math.Direction.valueOf( direction.name() )
		);
	}

	@Override
	public void playsound( Player player, double x, double y, double z, ResourceIdentifier sound, float volume, float pitch) {
		SoundEvent event = Registry.SOUND_EVENT.get( new Identifier( sound.getNamespace(), sound.getPath() ) );
		if ( event == null )
			throw new IllegalArgumentException( Utils.format("SoundEvent \"{}\" was not found!", sound ) );
		this.backingWorld.playSound(
			(PlayerEntity) player.getObject(),
			x, y, z,
			event,
			SoundCategory.MASTER,
			volume, pitch
		);
	}

	@Override
	public Object getObject() {
		return this.backingWorld;
	}
}
