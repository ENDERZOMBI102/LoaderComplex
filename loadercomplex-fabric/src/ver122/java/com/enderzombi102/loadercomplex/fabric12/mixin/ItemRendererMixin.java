package com.enderzombi102.loadercomplex.fabric12.mixin;

import com.enderzombi102.loadercomplex.fabric12.impl.FabricRegistry;
import com.enderzombi102.loadercomplex.impl.LoaderComplex;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.item.Item;
import net.minecraft.resource.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin( ItemRenderer.class )
public abstract class ItemRendererMixin {
	@Shadow
	protected abstract void registerModel( Item item, String key );

	@Inject( method = "registerGuiModels", at = @At( "TAIL" ) )
	public void onRegisterGuiModels( CallbackInfo ci ) {
		List<Item> items = ((FabricRegistry) LoaderComplex.get().getContext().getRegistry()).getRegisteredItems();

		for ( Item item : items ) {
			Identifier key = net.minecraft.item.Item.REGISTRY.getKey( item );
			assert key != null;
			this.registerModel( item, key.toString() );
		}
	}
}
