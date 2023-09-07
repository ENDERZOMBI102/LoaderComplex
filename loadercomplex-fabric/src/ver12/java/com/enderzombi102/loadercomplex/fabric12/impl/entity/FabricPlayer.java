package com.enderzombi102.loadercomplex.fabric12.impl.entity;

import com.enderzombi102.loadercomplex.fabric12.impl.utils.BlockUtils;
import com.enderzombi102.loadercomplex.minecraft.entity.Player;
import com.enderzombi102.loadercomplex.minecraft.util.Gamemode;
import com.enderzombi102.loadercomplex.minecraft.util.Position;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;

public class FabricPlayer extends FabricLivingEntity implements Player {
	private final PlayerEntity backingEntity;

	public FabricPlayer(PlayerEntity backingEntity) {
		super(backingEntity);
		this.backingEntity = backingEntity;
	}

	@Override
	public boolean isSleeping() {
		return this.backingEntity.isSleeping();
	}

	@Override
	public Position getBedLocation() {
		return BlockUtils.toPosition( this.backingEntity.getSpawnPosition() );
	}

	@Override
	public int getScore() {
		return this.backingEntity.getScore();
	}

	@Override
	public void addScore(int score) {
		this.backingEntity.addScore( score );
	}

	@Override
	public void sendMessage(String msg) {
		this.backingEntity.sendMessage( new LiteralText( msg ), false );
	}

	@Override
	public void setRespawnPoint(Position pos) {
		this.backingEntity.setPlayerSpawn( new BlockPos( pos.x, pos.y, pos.z ), true );
	}

	@Override
	public int getFoodLevel() {
		return this.backingEntity.getHungerManager().getFoodLevel();
	}

	@Override
	public void setFoodLevel(int level) {
		this.backingEntity.getHungerManager().setFoodLevel( level );
	}

	@Override
	public float getSaturationLevel() {
		return this.backingEntity.getHungerManager().getSaturationLevel();
	}

	@Override
	public void setSaturationLevel(float level) {
		this.backingEntity.getHungerManager().setSaturationLevelClient( level );
	}

	@Override
	public Gamemode getGamemode() {
		GameMode mode;
		if ( this.backingEntity.getWorld().isClient )
			mode = MinecraftClient.getInstance().interactionManager.method_9667();
		else
			mode = ( (ServerPlayerEntity) this.backingEntity ).interactionManager.getgamemode();
		if ( mode == GameMode.NOT_SET )
			throw new IllegalStateException("Player has no gamemode!");
		return Gamemode.valueOf( mode.name().toUpperCase() );
	}

	@Override
	public int getExperienceLevel() {
		return this.backingEntity.experienceLevel;
	}
}
