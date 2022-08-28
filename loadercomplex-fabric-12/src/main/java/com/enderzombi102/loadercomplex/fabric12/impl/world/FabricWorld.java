package com.enderzombi102.loadercomplex.fabric12.impl.world;

import com.enderzombi102.enderlib.Strings;
import com.enderzombi102.loadercomplex.fabric12.impl.FabricServer;
import com.enderzombi102.loadercomplex.fabric12.impl.block.FabricBlockstate;
import com.enderzombi102.loadercomplex.fabric12.impl.utils.BlockUtils;
import com.enderzombi102.loadercomplex.Utils;
import com.enderzombi102.loadercomplex.api.block.Blockstate;
import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.entity.Player;
import com.enderzombi102.loadercomplex.api.utils.*;
import com.enderzombi102.loadercomplex.api.world.World;
import net.minecraft.block.BlockState;
import net.minecraft.client.sound.SoundCategory;
import net.minecraft.client.sound.SoundEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class FabricWorld implements World {
	private final net.minecraft.world.World backingWorld;

	public FabricWorld(net.minecraft.world.World world) {
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
		return this.backingWorld.isLoaded( new BlockPos( pos.x, pos.y, pos.z ), true );
	}

	@Override
	public boolean hasBlockEntity(Position pos) {
		return this.backingWorld.getBlockEntity( new BlockPos( pos.x, pos.y, pos.z ) ) != null;
	}

	@Override
	public boolean canSeeTheSky(Position pos) {
		return this.backingWorld.method_8568( new BlockPos( pos.x, pos.y, pos.z ) );
	}

	@Override
	public boolean canSnow(Position pos) {
		return this.backingWorld.method_8550( new BlockPos( pos.x, pos.y, pos.z ), true );
	}

	@Override
	public Blockstate getBlockState(Position pos) {
		return new FabricBlockstate( this.backingWorld.getBlockState( new BlockPos( pos.x, pos.y, pos.z ) ) );
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
		return new FabricServer( this.backingWorld.getServer() );
	}

	@Override
	public Position getSpawnLocation() {
		return BlockUtils.toPosition( this.backingWorld.getSpawnPos() );
	}

	@Override
	public Difficulty getDifficulty() {
		return Difficulty.valueOf( this.backingWorld.getGlobalDifficulty().name() );
	}

	@Override
	public int getRedstonePower(Position pos, Direction direction) {
		return this.backingWorld.getEmittedRedstonePower(
			new BlockPos( pos.x, pos.y, pos.z ),
			net.minecraft.util.math.Direction.valueOf( direction.name() )
		);
	}

	@Override
	public void playsound(Player player, double x, double y, double z, ResourceIdentifier sound, float volume, float pitch) {
		SoundEvent event = SoundEvent.REGISTRY.get( new Identifier( sound.getNamespace(), sound.getPath() ) );
		if ( event == null )
			throw new IllegalArgumentException( Strings.format("SoundEvent \"{}\" was not found!", sound ) );
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
