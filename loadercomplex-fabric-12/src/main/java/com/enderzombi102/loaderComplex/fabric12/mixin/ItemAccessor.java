package com.enderzombi102.loaderComplex.fabric12.mixin;

import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Item.class)
public class ItemAccessor {

	@Accessor( value = "DAMAGED_PROVIDER" )
	public static ModelPredicateProvider getDamagedProvider() {
		throw new IllegalStateException("why is this happening");
	}

	@Accessor( value = "DAMAGE_PROVIDER" )
	public static ModelPredicateProvider getDamageProvider() {
		throw new IllegalStateException("why is this happening");
	}

	@Accessor( value = "LEFTHANDED_PROVIDER" )
	public static ModelPredicateProvider getLeftHandedProvider() {
		throw new IllegalStateException("why is this happening");
	}

	@Accessor( value = "COOLDOWN_PROVIDER" )
	public static ModelPredicateProvider getCooldownProvider() {
		throw new IllegalStateException("why is this happening");
	}

}
