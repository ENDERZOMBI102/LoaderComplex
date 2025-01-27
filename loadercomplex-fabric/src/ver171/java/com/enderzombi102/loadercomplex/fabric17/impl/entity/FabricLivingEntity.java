package com.enderzombi102.loadercomplex.fabric17.impl.entity;

import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity;
import com.enderzombi102.loadercomplex.fabric17.impl.item.FabricItemStack;
import com.enderzombi102.loadercomplex.api.minecraft.entity.LivingEntity;
import com.enderzombi102.loadercomplex.api.minecraft.item.EquipmentSlot;
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack;
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
		return this.backingEntity instanceof net.minecraft.entity.player.PlayerEntity;
	}

	@Override
	public PlayerEntity asPlayer() {
		return new FabricPlayer( (net.minecraft.entity.player.PlayerEntity) this.backingEntity );
	}

	@Override
	public boolean isChild() {
		return this.backingEntity.isBaby();
	}

	@Override
	public boolean isWaterMob() {
		return this.backingEntity.canBreatheInWater();
	}

	@Override
	public boolean isClimbing() {
		return this.backingEntity.isClimbing();
	}

	@Override
	public int getArmorProtection() {
		return this.backingEntity.getArmor();
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
	public boolean hasEquipment( EquipmentSlot slot) {
		return this.backingEntity.hasStackEquipped( net.minecraft.entity.EquipmentSlot.valueOf( slot.name() ) );
	}

	@Override
	public ItemStack getEquipment( EquipmentSlot slot) {
		return new FabricItemStack( this.backingEntity.getEquippedStack( net.minecraft.entity.EquipmentSlot.valueOf( slot.name() ) ) );
	}

	@Override
	public void setEquipment( EquipmentSlot slot, ItemStack stack) {
		// TODO: Find this
	}
}
