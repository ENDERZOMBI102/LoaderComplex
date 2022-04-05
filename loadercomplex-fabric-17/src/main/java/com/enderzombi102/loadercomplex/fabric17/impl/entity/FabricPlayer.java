package com.enderzombi102.loadercomplex.fabric17.impl.entity;

import com.enderzombi102.loadercomplex.fabric17.impl.utils.BlockUtils;
import com.enderzombi102.loadercomplex.api.entity.Player;
import com.enderzombi102.loadercomplex.api.utils.Gamemode;
import com.enderzombi102.loadercomplex.api.utils.Position;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;

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
		return BlockUtils.toPosition( this.backingEntity.getSleepingPosition().orElseThrow() );
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
		this.backingEntity.setSleepingPosition( new BlockPos( pos.x, pos.y, pos.z ) );
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
		this.backingEntity.getHungerManager().setSaturationLevel( level );
	}

	@Override
	public Gamemode getGamemode() {
		return null;
	}

	@Override
	public int getExperienceLevel() {
		return this.backingEntity.experienceLevel;
	}
}
