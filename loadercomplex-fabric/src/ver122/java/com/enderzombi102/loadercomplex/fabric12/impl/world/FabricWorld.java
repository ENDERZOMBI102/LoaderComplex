package com.enderzombi102.loadercomplex.fabric12.impl.world;

import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity;
import com.enderzombi102.loadercomplex.api.minecraft.util.*;
import com.enderzombi102.loadercomplex.fabric12.impl.FabricServer;
import com.enderzombi102.loadercomplex.fabric12.impl.block.FabricBlockstate;
import com.enderzombi102.loadercomplex.api.minecraft.block.Blockstate;
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity;
import com.enderzombi102.loadercomplex.api.minecraft.world.World;
import com.enderzombi102.loadercomplex.fabric12.impl.utils.ConversionKt;
import net.minecraft.resource.Identifier;
import net.minecraft.util.math.BlockPos;

public class FabricWorld implements World {
	private final net.minecraft.world.World backingWorld;

	public FabricWorld( net.minecraft.world.World world ) {
		backingWorld = world;
	}

	@Override
	public void spawn( Entity entity, Position pos ) {
		entity.setPosition( pos );
		this.backingWorld.addEntity( (net.minecraft.entity.Entity) entity.getObject() );
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
	public boolean isSunny() {
		return this.backingWorld.isSunny();
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
	public boolean isAir( Position pos ) {
		return this.backingWorld.isAir( new BlockPos( pos.x, pos.y, pos.z ) );
	}

	@Override
	public boolean isPositionLoaded( Position pos ) {
		return this.backingWorld.isChunkLoaded( new BlockPos( pos.x, pos.y, pos.z ), true );
	}

	@Override
	public boolean hasBlockEntity( Position pos ) {
		return this.backingWorld.getBlockEntity( new BlockPos( pos.x, pos.y, pos.z ) ) != null;
	}

	@Override
	public boolean hasSkyAccess( Position pos ) {
		return this.backingWorld.hasSkyAccess( new BlockPos( pos.x, pos.y, pos.z ) );
	}

	@Override
	public boolean canSnowFall( Position pos ) {
		return this.backingWorld.canSnowFall( new BlockPos( pos.x, pos.y, pos.z ), true );
	}

	@Override
	public Blockstate getBlockState( Position pos ) {
		return new FabricBlockstate( this.backingWorld.getBlockState( new BlockPos( pos.x, pos.y, pos.z ) ) );
	}

	@Override
	public void setBlockState( Position pos, Blockstate state ) {
		this.backingWorld.setBlockState( new BlockPos( pos.x, pos.y, pos.z ), (net.minecraft.block.state.BlockState) state.getObject() );
	}

	@Override
	public void breakBlock( Position pos, boolean dropItems ) {
		this.backingWorld.breakBlock( new BlockPos( pos.x, pos.y, pos.z ), dropItems );
	}

	@Override
	public void removeBlock( Position pos ) {
		this.backingWorld.removeBlock( new BlockPos( pos.x, pos.y, pos.z ) );
	}

	@Override
	public Server getServer() {
		return new FabricServer( this.backingWorld.getServer() );
	}

	@Override
	public Position getSpawnLocation() {
		return ConversionKt.toLC( this.backingWorld.getSpawnPoint() );
	}

	@Override
	public Difficulty getDifficulty() {
		return Difficulty.valueOf( this.backingWorld.getDifficulty().name() );
	}

	@Override
	public int getRedstonePower( Position pos, Direction direction ) {
		return this.backingWorld.getDirectSignal(
			new BlockPos( pos.x, pos.y, pos.z ),
			net.minecraft.util.math.Direction.valueOf( direction.name() )
		);
	}

	@Override
	public void playsound( PlayerEntity player, double x, double y, double z, ResourceIdentifier sound, float volume, float pitch ) {
		net.minecraft.sound.SoundEvent event = net.minecraft.sound.SoundEvent.REGISTRY.get( new Identifier( sound.getNamespace(), sound.getPath() ) );
		if ( event == null ) {
			throw new IllegalArgumentException( String.format( "SoundEvent \"%s\" was not found!", sound ) );
		}
		this.backingWorld.playSound(
			(net.minecraft.entity.living.player.PlayerEntity) player.getObject(),
			x, y, z,
			event,
			net.minecraft.sound.SoundCategory.MASTER,
			volume, pitch
		);
	}

	@Override
	public Object getObject() {
		return this.backingWorld;
	}
}
