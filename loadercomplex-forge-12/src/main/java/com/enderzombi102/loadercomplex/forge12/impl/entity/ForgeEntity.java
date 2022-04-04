package com.enderzombi102.loadercomplex.forge12.impl.entity;

import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.entity.ItemEntity;
import com.enderzombi102.loadercomplex.api.entity.LivingEntity;
import com.enderzombi102.loadercomplex.api.utils.Position;
import com.enderzombi102.loadercomplex.api.world.World;
import com.enderzombi102.loadercomplex.forge12.impl.utils.BlockUtils;
import com.enderzombi102.loadercomplex.forge12.impl.world.ForgeWorld;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.text.ITextComponent;

import java.util.UUID;

public class ForgeEntity implements Entity {
	private final net.minecraft.entity.Entity wrappedEntity;

	public ForgeEntity(net.minecraft.entity.Entity entity) {
		this.wrappedEntity = entity;
	}

	@Override
	public String getDisplayName() {
		return ITextComponent.Serializer.componentToJson( this.wrappedEntity.getDisplayName() );
	}

	@Override
	public String getName() {
		return this.wrappedEntity.getName();
	}

	@Override
	public UUID getUuid() {
		return this.wrappedEntity.getUniqueID();
	}

	@Override
	public boolean isLivingEntity() {
		return this.wrappedEntity instanceof EntityLivingBase;
	}

	@Override
	public LivingEntity asLivingEntity() {
		return new ForgeLivingEntity( (EntityLivingBase) this.wrappedEntity );
	}

	@Override
	public boolean isItem() {
		return this.wrappedEntity instanceof EntityItem;
	}

	@Override
	public ItemEntity asItem() {
		return new ForgeItemEntity((EntityItem) this.wrappedEntity);
	}

	@Override
	public void kill() {
		this.wrappedEntity.setDead();
	}

	@Override
	public boolean isDead() {
		return this.wrappedEntity.isDead;
	}

	@Override
	public Position getPosition() {
		return BlockUtils.toPosition( this.wrappedEntity.getPosition() );

	}

	@Override
	public void setPosition(Position pos) {
		this.wrappedEntity.setPosition( pos.x, pos.y, pos.z );
	}

	@Override
	public float getPitch() {
		return this.wrappedEntity.rotationPitch;
	}

	@Override
	public float getYaw() {
		return this.wrappedEntity.rotationYaw;
	}

	@Override
	public World getWorld() {
		return new ForgeWorld( this.wrappedEntity.world );
	}

	@Override
	public Object getObject() {
		return this.wrappedEntity;
	}
}
