package com.enderzombi102.loadercomplex.forge12.impl.entity;

import com.enderzombi102.loadercomplex.api.minecraft.entity.LivingEntity;
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity;
import com.enderzombi102.loadercomplex.api.minecraft.item.EquipmentSlot;
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack;
import com.enderzombi102.loadercomplex.forge12.impl.item.ForgeItemStack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ForgeLivingEntity extends ForgeEntity implements LivingEntity {
	private final EntityLivingBase wrappedEntity;

	public ForgeLivingEntity( EntityLivingBase livingEntity ) {
		super( livingEntity );
		this.wrappedEntity = livingEntity;
	}

	@Override
	public float getHealth() {
		return this.wrappedEntity.getHealth();
	}

	@Override
	public void setHealth( float health ) {
		this.wrappedEntity.setHealth( health );
	}

	@Override
	public boolean isLivingEntity() {
		return true;
	}

	@Override
	public LivingEntity asLivingEntity() {
		return this;
	}

	@Override
	public boolean isPlayer() {
		return this.wrappedEntity instanceof EntityPlayer;
	}

	@Override
	public PlayerEntity asPlayer() {
		return new ForgePlayer( (EntityPlayer) this.wrappedEntity );
	}

	@Override
	public boolean isChild() {
		return this.wrappedEntity.isChild();
	}

	@Override
	public boolean isWaterMob() {
		return this.wrappedEntity.canBreatheUnderwater();
	}

	@Override
	public boolean isClimbing() {
		return this.wrappedEntity.isOnLadder();
	}

	@Override
	public int getArmorProtection() {
		return this.wrappedEntity.getTotalArmorValue();
	}

	@Override
	public ItemStack getItemInMainHand() {
		return new ForgeItemStack( this.wrappedEntity.getHeldItemMainhand() );
	}

	@Override
	public ItemStack getItemInOffHand() {
		return new ForgeItemStack( this.wrappedEntity.getHeldItemOffhand() );
	}

	@Override
	public boolean hasEquipment( EquipmentSlot slot ) {
		return this.wrappedEntity.hasItemInSlot( EntityEquipmentSlot.valueOf( slot.name() ) );
	}

	@Override
	public ItemStack getEquipment( EquipmentSlot slot ) {
		return new ForgeItemStack(
			this.wrappedEntity.getItemStackFromSlot( EntityEquipmentSlot.valueOf( slot.name() ) )
		);
	}

	@Override
	public void setEquipment( EquipmentSlot slot, ItemStack stack ) {
		this.wrappedEntity.setItemStackToSlot(
			EntityEquipmentSlot.valueOf( slot.name() ),
			(net.minecraft.item.ItemStack) stack.getStack()
		);
	}
}
