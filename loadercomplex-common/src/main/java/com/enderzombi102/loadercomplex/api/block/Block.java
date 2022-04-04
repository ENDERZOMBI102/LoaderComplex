package com.enderzombi102.loadercomplex.api.block;

import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.entity.Player;
import com.enderzombi102.loadercomplex.api.utils.Direction;
import com.enderzombi102.loadercomplex.api.utils.Hand;
import com.enderzombi102.loadercomplex.api.utils.Position;
import com.enderzombi102.loadercomplex.api.world.World;
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
	public void OnBreak( World world, Position pos, Blockstate state, Player player ) {}

	@ApiStatus.AvailableSince("0.1.3")
	public void OnSteppedOn( World world, Position pos, Entity entity) {}

	@ApiStatus.AvailableSince("0.1.3")
	public boolean OnBlockInteracted( World world, Position pos, Blockstate state, Player player, Hand hand, Direction direction, double hitX, double hitY, double hitZ ) { return false; }

	public void OnRandomTick( World world, Position pos, Blockstate state, Random random ) {}

	@ApiStatus.AvailableSince("0.1.3")
	public void OnEntityCollision( World world, Position pos, Blockstate state, Entity entity ) {}

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
