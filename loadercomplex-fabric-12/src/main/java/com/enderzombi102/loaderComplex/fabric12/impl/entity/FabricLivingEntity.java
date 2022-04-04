package com.enderzombi102.loaderComplex.fabric12.impl.entity;

import com.enderzombi102.loaderComplex.fabric12.impl.item.FabricItemStack;
import com.enderzombi102.loadercomplex.api.entity.LivingEntity;
import com.enderzombi102.loadercomplex.api.entity.Player;
import com.enderzombi102.loadercomplex.api.item.EquipmentSlot;
import com.enderzombi102.loadercomplex.api.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class FabricLivingEntity extends FabricEntity implements LivingEntity {
	private final net.minecraft.entity.LivingEntity backingEntity;

	public FabricLivingEntity(net.minecraft.entity.LivingEntity backingEntity) {
		super(backingEntity);
		this.backingEntity = backingEntity;
	}

	@Override
	public float getHealth() {
		return this.backingEntity.getHealth();
	}

	@Override
	public void setHealth(float health) {
		this.backingEntity.setHealth( health );
	}

	@Override
	public boolean isPlayer() {
		return this.backingEntity instanceof PlayerEntity;
	}

	@Override
	public Player asPlayer() {
		return new FabricPlayer( (PlayerEntity) this.backingEntity );
	}

	@Override
	public boolean isChild() {
		return this.backingEntity.isBaby();
	}

	@Override
	public boolean canBreathUnderwater() {
		return this.backingEntity.method_13055();
	}

	@Override
	public boolean isAttachedToLadder() {
		return this.backingEntity.isClimbing();
	}

	@Override
	public int getArmorValue() {
		return this.backingEntity.getArmorProtectionValue();
	}

	@Override
	public ItemStack getItemInMainHand() {
		return new FabricItemStack( this.backingEntity.getStackInHand(Hand.MAIN_HAND) );
	}

	@Override
	public ItemStack getItemInOffHand() {
		return new FabricItemStack( this.backingEntity.getStackInHand(Hand.OFF_HAND) );
	}

	@Override
	public boolean hasItemInSlot(EquipmentSlot slot) {
		return this.backingEntity.method_13946( net.minecraft.entity.EquipmentSlot.valueOf( slot.name() ) );
	}

	@Override
	public ItemStack getStackInSlot(EquipmentSlot slot) {
		return new FabricItemStack( this.backingEntity.method_13043( net.minecraft.entity.EquipmentSlot.valueOf( slot.name() ) ) );
	}

	@Override
	public void setStackInSlot(EquipmentSlot slot, ItemStack stack) {
		// TODO: Find this
	}
}
