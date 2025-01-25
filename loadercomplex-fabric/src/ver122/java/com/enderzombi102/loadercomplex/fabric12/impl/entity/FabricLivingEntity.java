package com.enderzombi102.loadercomplex.fabric12.impl.entity;

import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity;
import com.enderzombi102.loadercomplex.fabric12.impl.item.FabricItemStack;
import com.enderzombi102.loadercomplex.api.minecraft.entity.LivingEntity;
import com.enderzombi102.loadercomplex.api.minecraft.item.EquipmentSlot;
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack;
import net.minecraft.world.InteractionHand;

public class FabricLivingEntity extends FabricEntity implements LivingEntity {
	private final net.minecraft.entity.living.LivingEntity backingEntity;

	public FabricLivingEntity( net.minecraft.entity.living.LivingEntity backingEntity ) {
		super( backingEntity );
		this.backingEntity = backingEntity;
	}

	@Override
	public float getHealth() {
		return this.backingEntity.getHealth();
	}

	@Override
	public void setHealth( float health ) {
		this.backingEntity.setHealth( health );
	}

	@Override
	public boolean isPlayer() {
		return this.backingEntity instanceof net.minecraft.entity.living.player.PlayerEntity;
	}

	@Override
	public PlayerEntity asPlayer() {
		return new FabricPlayer( (net.minecraft.entity.living.player.PlayerEntity) this.backingEntity );
	}

	@Override
	public boolean isChild() {
		return this.backingEntity.isBaby();
	}

	@Override
	public boolean canBreathUnderwater() {
		return false; // this.backingEntity.method_13055();
	}

	@Override
	public boolean isAttachedToLadder() {
		return this.backingEntity.isClimbing();
	}

	@Override
	public int getArmorValue() {
		return this.backingEntity.getArmorProtection();
	}

	@Override
	public ItemStack getItemInMainHand() {
		return new FabricItemStack( this.backingEntity.getItemInHand( InteractionHand.MAIN_HAND ) );
	}

	@Override
	public ItemStack getItemInOffHand() {
		return new FabricItemStack( this.backingEntity.getItemInHand( InteractionHand.OFF_HAND ) );
	}

	@Override
	public boolean hasItemInSlot( EquipmentSlot slot ) {
		return false; // this.backingEntity.method_13946( net.minecraft.entity.EquipmentSlot.valueOf( slot.name() ) );
	}

	@Override
	public ItemStack getStackInSlot( EquipmentSlot slot ) {
		return new FabricItemStack( this.backingEntity.getEquipment( net.minecraft.entity.EquipmentSlot.valueOf( slot.name() ) ) );
	}

	@Override
	public void setStackInSlot( EquipmentSlot slot, ItemStack stack ) {
		// TODO: Find this
	}
}
