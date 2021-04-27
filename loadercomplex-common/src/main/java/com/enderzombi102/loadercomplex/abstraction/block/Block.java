package com.enderzombi102.loadercomplex.abstraction.block;

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
	public void OnBreak(Object player) {}
	public void OnSteppedOn(Object entity) {}
	public boolean OnBlockInteracted(Object player) { return false; }
	public void OnRandomTick(Random random) {}
	public void OnEntityCollision(Object entity) {}

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
