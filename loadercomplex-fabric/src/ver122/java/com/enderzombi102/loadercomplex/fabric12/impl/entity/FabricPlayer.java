package com.enderzombi102.loadercomplex.fabric12.impl.entity;

import com.enderzombi102.loadercomplex.fabric12.impl.utils.BlockUtils;
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity;
import com.enderzombi102.loadercomplex.api.minecraft.util.Gamemode;
import com.enderzombi102.loadercomplex.api.minecraft.util.Position;
import net.minecraft.client.Minecraft;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;

public class FabricPlayer extends FabricLivingEntity implements PlayerEntity {
	private final net.minecraft.entity.living.player.PlayerEntity backingEntity;

	public FabricPlayer( net.minecraft.entity.living.player.PlayerEntity backingEntity ) {
		super( backingEntity );
		this.backingEntity = backingEntity;
	}

	@Override
	public boolean isSleeping() {
		return this.backingEntity.isSleeping();
	}

	@Override
	public Position getBedLocation() {
		return BlockUtils.toPosition( this.backingEntity.getSpawnPoint() );
	}

	@Override
	public int getScore() {
		return this.backingEntity.getScore();
	}

	@Override
	public void addScore( int score ) {
		this.backingEntity.addToScore( score );
	}

	@Override
	public void sendMessage( String msg ) {
		this.backingEntity.sendMessage( new LiteralText( msg ) );
	}

	@Override
	public void setRespawnPoint( Position pos ) {
		this.backingEntity.setSpawnPoint( new BlockPos( pos.x, pos.y, pos.z ), true );
	}

	@Override
	public int getFoodLevel() {
		return this.backingEntity.getHungerManager().getFoodLevel();
	}

	@Override
	public void setFoodLevel( int level ) {
		this.backingEntity.getHungerManager().setFoodLevel( level );
	}

	@Override
	public float getSaturationLevel() {
		return this.backingEntity.getHungerManager().getSaturationLevel();
	}

	@Override
	public void setSaturationLevel( float level ) {
		this.backingEntity.getHungerManager().setSaturationLevel( level );
	}

	@Override
	public Gamemode getGamemode() {
		GameMode mode;
		if ( this.backingEntity.getSourceWorld().isClient )
			mode = Minecraft.getInstance().interactionManager.getGameMode();
		else
			mode = ((ServerPlayerEntity) this.backingEntity).interactionManager.getGameMode();
		if ( mode == GameMode.NOT_SET )
			throw new IllegalStateException( "Player has no gamemode!" );
		return Gamemode.valueOf( mode.name().toUpperCase() );
	}

	@Override
	public int getExperienceLevel() {
		return this.backingEntity.xpLevel;
	}
}
