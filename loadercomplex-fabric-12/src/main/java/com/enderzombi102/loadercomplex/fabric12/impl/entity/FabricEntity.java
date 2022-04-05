package com.enderzombi102.loadercomplex.fabric12.impl.entity;

import com.enderzombi102.loadercomplex.fabric12.impl.utils.BlockUtils;
import com.enderzombi102.loadercomplex.fabric12.impl.world.FabricWorld;
import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.entity.ItemEntity;
import com.enderzombi102.loadercomplex.api.entity.LivingEntity;
import com.enderzombi102.loadercomplex.api.utils.Position;
import com.enderzombi102.loadercomplex.api.world.World;
import net.minecraft.text.Text;

import java.util.UUID;

public class FabricEntity implements Entity {
	private final net.minecraft.entity.Entity backingEntity;

	public FabricEntity(net.minecraft.entity.Entity backingEntity) {
		this.backingEntity = backingEntity;
	}

	@Override
	public String getDisplayName() {
		return Text.Serializer.serialize( this.backingEntity.getName() );
	}

	@Override
	public String getName() {
		return this.backingEntity.getTranslationKey();
	}

	@Override
	public UUID getUuid() {
		return this.backingEntity.getUuid();
	}

	@Override
	public boolean isLivingEntity() {
		return this.backingEntity instanceof net.minecraft.entity.LivingEntity;
	}

	@Override
	public LivingEntity asLivingEntity() {
		return new FabricLivingEntity( (net.minecraft.entity.LivingEntity) this.backingEntity );
	}

	@Override
	public boolean isItem() {
		return this.backingEntity instanceof net.minecraft.entity.ItemEntity;
	}

	@Override
	public ItemEntity asItem() {
		return new FabricItemEntity( (net.minecraft.entity.ItemEntity) this.backingEntity);
	}

	@Override
	public void kill() {
		this.backingEntity.remove();
	}

	@Override
	public boolean isDead() {
		return this.backingEntity.removed;
	}

	@Override
	public Position getPosition() {
		return BlockUtils.toPosition( this.backingEntity.getBlockPos() );
	}

	@Override
	public void setPosition(Position pos) {
//		this.backingEntity; TODO: Add setPosition
	}

	@Override
	public float getPitch() {
		return this.backingEntity.pitch;
	}

	@Override
	public float getYaw() {
		return this.backingEntity.yaw;
	}

	@Override
	public World getWorld() {
		return new FabricWorld( this.backingEntity.world );
	}

	@Override
	public Object getObject() {
		return this.backingEntity;
	}
}
