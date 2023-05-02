package com.enderzombi102.loadercomplex.api.minecraft.block;

import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity;
import com.enderzombi102.loadercomplex.api.minecraft.entity.Player;
import com.enderzombi102.loadercomplex.api.minecraft.world.World;
import com.enderzombi102.loadercomplex.api.minecraft.util.Direction;
import com.enderzombi102.loadercomplex.api.minecraft.util.Hand;
import com.enderzombi102.loadercomplex.api.minecraft.util.Position;
import org.jetbrains.annotations.ApiStatus;

import java.util.Random;

/**
 * Represents an extensible block.<br/>
 * <br/>
 * Any block implemented by addons should extend this class.
 */
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

	/**
	 * Internal field, do not use.<br/>
	 * Holds the Loader-Specific wrapper instance for this block
	 */
	@ApiStatus.Internal
	public Object implementationBlock;

	// methods to be overwritten

	/**
	 * Called when the block is broken
	 * @param world world the block was broken in
	 * @param pos position of the broken block
	 * @param state state of the broken block
	 * @param player player that broke the block
	 */
	@ApiStatus.AvailableSince("0.1.3")
	public void OnBreak(World world, Position pos, Blockstate state, Player player ) {}

	/**
	 * Called when an entity steps on this block
	 * @param world world the block is in
	 * @param pos position the block is in
	 * @param entity entity that stepped on the block
	 */
	@ApiStatus.AvailableSince("0.1.3")
	public void OnSteppedOn( World world, Position pos, Entity entity) {}

	/**
	 * Called when a player interacts with this block
	 * @param world the world the interaction happened in
	 * @param pos the position of the block the player interacted with
	 * @param state state of the block the player interacted with
	 * @param player player that interacted with the block
	 * @param hand hand which interaction was performed from
	 * @param direction direction of the interaction
	 * @param hitX precise hit X position
	 * @param hitY precise hit Y position
	 * @param hitZ precise hit Z position
	 * @return i have no idea
	 */
	@ApiStatus.AvailableSince("0.1.3")
	public boolean OnBlockInteracted( World world, Position pos, Blockstate state, Player player, Hand hand, Direction direction, double hitX, double hitY, double hitZ ) { return false; }

	/**
	 * Called on random block tick
	 * @param world the world the block is in
	 * @param pos the position of the block the player interacted with
	 * @param state state of the block the player interacted with
	 * @param random a {@link java.util.Random} instance
	 */
	public void OnRandomTick( World world, Position pos, Blockstate state, Random random ) {}

	/**
	 * Called when an entity collides with this block
	 * @param world the world the collision happened in
	 * @param pos the position of the block the entity collided with
	 * @param state state of the block the entity collided with
	 * @param entity entity that collided with this block
	 */
	@ApiStatus.AvailableSince("0.1.3")
	public void OnEntityCollision( World world, Position pos, Blockstate state, Entity entity ) {}

	// methods used to set values

	/**
	 * Sets the light emitted by this block
	 * @param lightLevel a float in the range 0.0-1.0
	 */
	protected void setLightLevel(float lightLevel) {
		this.lightLevel = (int)(15.0F * lightLevel);
	}

	/**
	 * Sets the resistance of this block
	 */
	protected void setResistance(float resistance) {
		this.resistance = resistance * 3.0F;
	}

	/**
	 * Sets the hardness of this block
	 */
	protected void setHardness(float hardness) {
		this.hardness = hardness;
		if (this.resistance < hardness * 5.0F) {
			this.resistance = hardness * 5.0F;
		}
	}

	/**
	 * Convenience method to make the block unbreakable
	 */
	protected void setUnbreakable() {
		this.setHardness(-1.0F);
	}
}
