package com.enderzombi102.loadercomplex.fabric17.impl.entity;

import com.enderzombi102.loadercomplex.fabric17.impl.utils.BlockUtils;
import com.enderzombi102.loadercomplex.api.minecraft.entity.Player;
import com.enderzombi102.loadercomplex.api.minecraft.util.Gamemode;
import com.enderzombi102.loadercomplex.api.minecraft.util.Position;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;

public class FabricPlayer extends FabricLivingEntity implements Player {
	private final PlayerEntity wrappedEntity;

	public FabricPlayer(PlayerEntity backingEntity) {
		super(backingEntity);
		this.wrappedEntity = backingEntity;
	}

	@Override
	public boolean isSleeping() {
		return this.wrappedEntity.isSleeping();
	}

	@Override
	public Position getBedLocation() {
		return BlockUtils.toPosition( this.wrappedEntity.getSleepingPosition().orElseThrow() );
	}

	@Override
	public int getScore() {
		return this.wrappedEntity.getScore();
	}

	@Override
	public void addScore(int score) {
		this.wrappedEntity.addScore( score );
	}

	@Override
	public void sendMessage(String msg) {
		this.wrappedEntity.sendMessage( new LiteralText( msg ), false );
	}

	@Override
	public void setRespawnPoint(Position pos) {
		this.wrappedEntity.setSleepingPosition( new BlockPos( pos.x, pos.y, pos.z ) );
	}

	@Override
	public int getFoodLevel() {
		return this.wrappedEntity.getHungerManager().getFoodLevel();
	}

	@Override
	public void setFoodLevel(int level) {
		this.wrappedEntity.getHungerManager().setFoodLevel( level );
	}

	@Override
	public float getSaturationLevel() {
		return this.wrappedEntity.getHungerManager().getSaturationLevel();
	}

	@Override
	public void setSaturationLevel(float level) {
		this.wrappedEntity.getHungerManager().setSaturationLevel( level );
	}

	@Override
	public Gamemode getGamemode() {
		GameMode mode;
		if ( this.wrappedEntity.getEntityWorld().isClient )
			//noinspection ConstantConditions
			mode = MinecraftClient.getInstance().interactionManager.getCurrentGameMode();
		else
			mode = ( (ServerPlayerEntity) this.wrappedEntity ).interactionManager.getGameMode();
		if ( mode == GameMode.DEFAULT )
			return Gamemode.SURVIVAL;
		return Gamemode.valueOf( mode.getName().toUpperCase() );
	}

	@Override
	public int getExperienceLevel() {
		return this.wrappedEntity.experienceLevel;
	}
}
