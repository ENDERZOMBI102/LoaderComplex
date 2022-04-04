package com.enderzombi102.loadercomplex.api.block;

import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.entity.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.Random;

public abstract class Block {

	public boolean opaque = true;
	public int opacity = 255;
	public boolean translucent = false;
	public int lightLevel = 0;
	public boolean useNeighbourLight = false;
	public float hardness = 1.0F;
	public float resistance = 30.0F;
	public boolean randomTicks = false;
	public boolean hasCollision = true;
	public boolean blockEntity = false;
	public BlockSoundGroup soundGroup = BlockSoundGroup.STONE;
	public float particleGravity = 1.0F;
	public float slipperiness = 0.6F;
	// IMPLEMENTATION ATTRIBUTE
	public Object implementationBlock;

	// methods to be overwritten
	@ApiStatus.AvailableSince("0.1.3")
	public void OnBreak(Player player, boolean isClient) {}

	@ApiStatus.AvailableSince("0.1.3")
	public void OnSteppedOn(Entity entity, boolean isClient) {}

	@ApiStatus.AvailableSince("0.1.3")
	public boolean OnBlockInteracted(Player player, boolean isClient) { return false; }

	public void OnRandomTick(Random random, boolean isClient) {}

	@ApiStatus.AvailableSince("0.1.3")
	public void OnEntityCollision(Entity entity, boolean isClient) {}

	// methods used to set values
	protected void setLightLevel(float lightLevel) {
		this.lightLevel = (int)(15.0F * lightLevel);
	}

	protected void setResistance(float resistance) {
		this.resistance = resistance * 3.0F;
	}

	protected void setHardness(float hardness) {
		this.hardness = hardness;
		if (this.resistance < hardness * 5.0F) {
			this.resistance = hardness * 5.0F;
		}
	}

	protected void setUnbreakable() {
		this.setHardness(-1.0F);
	}
}
