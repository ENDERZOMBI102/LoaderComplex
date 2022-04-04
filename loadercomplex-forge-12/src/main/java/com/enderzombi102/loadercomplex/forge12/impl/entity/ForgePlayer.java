package com.enderzombi102.loadercomplex.forge12.impl.entity;

import com.enderzombi102.loadercomplex.api.entity.Player;
import com.enderzombi102.loadercomplex.api.utils.Gamemode;
import com.enderzombi102.loadercomplex.api.utils.Position;
import com.enderzombi102.loadercomplex.forge12.impl.utils.BlockUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameType;

public class ForgePlayer extends ForgeLivingEntity implements Player {
	private final EntityPlayer wrappedEntity;

	public ForgePlayer(EntityPlayer player) {
		super(player);
		this.wrappedEntity = player;
	}

	@Override
	public boolean isSleeping() {
		return this.wrappedEntity.isPlayerSleeping();
	}

	@Override
	public Position getBedLocation() {
		return BlockUtils.toPosition( this.wrappedEntity.getBedLocation() );
	}

	@Override
	public int getScore() {
		return this.wrappedEntity.getScore();
	}

	@Override
	public void addScore(int score) {
		this.wrappedEntity.addScore(score);
	}

	@Override
	public void sendMessage(String msg) {
		this.wrappedEntity.sendMessage( new TextComponentString( msg ) );
	}

	@Override
	public void setRespawnPoint(Position pos) {
		this.wrappedEntity.setSpawnPoint( new BlockPos( pos.x, pos.y, pos.z ), true );
	}

	@Override
	public int getFoodLevel() {
		return this.wrappedEntity.getFoodStats().getFoodLevel();
	}

	@Override
	public void setFoodLevel(int level) {
		this.wrappedEntity.getFoodStats().setFoodLevel( level );
	}

	@Override
	public float getSaturationLevel() {
		return this.wrappedEntity.getFoodStats().getSaturationLevel();
	}

	@Override
	public void setSaturationLevel(float level) {
		this.wrappedEntity.getFoodStats().setFoodSaturationLevel( level );
	}

	@Override
	public Gamemode getGamemode() {
		GameType type;
		if ( this.wrappedEntity.getEntityWorld().isRemote )
			type = Minecraft.getMinecraft().playerController.getCurrentGameType();
		else
			type = ( (EntityPlayerMP) this.wrappedEntity ).interactionManager.getGameType();
		if ( type == GameType.NOT_SET )
			throw new IllegalStateException("Player has no gamemode!");
		return Gamemode.valueOf( type.getName().toUpperCase() );
	}

	@Override
	public int getExperienceLevel() {
		return this.wrappedEntity.experienceLevel;
	}

	@Override
	public boolean isPlayer() {
		return true;
	}

	@Override
	public Player asPlayer() {
		return this;
	}
}
