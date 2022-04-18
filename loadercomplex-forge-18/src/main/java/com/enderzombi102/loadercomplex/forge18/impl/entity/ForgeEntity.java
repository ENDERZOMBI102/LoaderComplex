package com.enderzombi102.loadercomplex.forge18.impl.entity;

import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.entity.ItemEntity;
import com.enderzombi102.loadercomplex.api.entity.LivingEntity;
import com.enderzombi102.loadercomplex.api.utils.Position;
import com.enderzombi102.loadercomplex.api.world.World;
import com.enderzombi102.loadercomplex.forge18.impl.utils.BlockUtils;
import com.enderzombi102.loadercomplex.forge18.impl.world.ForgeWorld;
import net.minecraft.text.Text;

import java.util.UUID;

public class ForgeEntity implements Entity {
	private final net.minecraft.entity.Entity backingEntity;

	public ForgeEntity(net.minecraft.entity.Entity backingEntity) {
		this.backingEntity = backingEntity;
	}

	@Override
	public String getDisplayName() {
		return Text.Serializer.toJson( this.backingEntity.getName() );
	}

	@Override
	public String getName() {
		return this.backingEntity.getEntityName();
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
		return new ForgeLivingEntity( (net.minecraft.entity.LivingEntity) this.backingEntity );
	}

	@Override
	public boolean isItem() {
		return this.backingEntity instanceof net.minecraft.entity.ItemEntity;
	}

	@Override
	public ItemEntity asItem() {
		return new ForgeItemEntity( (net.minecraft.entity.ItemEntity) this.backingEntity);
	}

	@Override
	public void kill() {
		this.backingEntity.remove(net.minecraft.entity.Entity.RemovalReason.KILLED);
	}

	@Override
	public boolean isDead() {
		return this.backingEntity.isRemoved();
	}

	@Override
	public Position getPosition() {
		return BlockUtils.toPosition( this.backingEntity.getBlockPos() );
	}

	@Override
	public void setPosition(Position pos) {
		this.backingEntity.setPosition( pos.x, pos.y, pos.z );
	}

	@Override
	public float getPitch() {
		return this.backingEntity.getPitch();
	}

	@Override
	public float getYaw() {
		return this.backingEntity.getYaw();
	}

	@Override
	public World getWorld() {
		return new ForgeWorld( this.backingEntity.world );
	}

	@Override
	public Object getObject() {
		return this.backingEntity;
	}
}
