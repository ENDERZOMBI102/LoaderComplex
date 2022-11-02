package com.enderzombi102.loadercomplex.quilt.impl.entity;

import com.enderzombi102.loadercomplex.minecraft.entity.LivingEntity;
import com.enderzombi102.loadercomplex.minecraft.entity.Player;
import com.enderzombi102.loadercomplex.minecraft.item.EquipmentSlot;
import com.enderzombi102.loadercomplex.minecraft.item.ItemStack;
import com.enderzombi102.loadercomplex.quilt.impl.item.QuiltItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class QuiltLivingEntity extends QuiltEntity implements LivingEntity {
	private final net.minecraft.entity.LivingEntity backingEntity;

	public QuiltLivingEntity(net.minecraft.entity.LivingEntity backingEntity) {
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
		return new QuiltPlayer( (PlayerEntity) this.backingEntity );
	}

	@Override
	public boolean isChild() {
		return this.backingEntity.isBaby();
	}

	@Override
	public boolean canBreathUnderwater() {
		return this.backingEntity.canBreatheInWater();
	}

	@Override
	public boolean isAttachedToLadder() {
		return this.backingEntity.isClimbing();
	}

	@Override
	public int getArmorValue() {
		return this.backingEntity.getArmor();
	}

	@Override
	public ItemStack getItemInMainHand() {
		return new QuiltItemStack( this.backingEntity.getStackInHand(Hand.MAIN_HAND) );
	}

	@Override
	public ItemStack getItemInOffHand() {
		return new QuiltItemStack( this.backingEntity.getStackInHand(Hand.OFF_HAND) );
	}

	@Override
	public boolean hasItemInSlot(EquipmentSlot slot) {
		return this.backingEntity.hasStackEquipped( net.minecraft.entity.EquipmentSlot.valueOf( slot.name() ) );
	}

	@Override
	public ItemStack getStackInSlot(EquipmentSlot slot) {
		return new QuiltItemStack( this.backingEntity.getEquippedStack( net.minecraft.entity.EquipmentSlot.valueOf( slot.name() ) ) );
	}

	@Override
	public void setStackInSlot(EquipmentSlot slot, ItemStack stack) {
		this.backingEntity.equipStack( net.minecraft.entity.EquipmentSlot.valueOf( slot.name() ), ( (QuiltItemStack) stack ).getStack() );
	}
}
