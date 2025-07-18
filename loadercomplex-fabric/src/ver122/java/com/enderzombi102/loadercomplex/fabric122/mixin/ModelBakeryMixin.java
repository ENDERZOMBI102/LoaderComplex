package com.enderzombi102.loadercomplex.fabric122.mixin;

import com.enderzombi102.loadercomplex.fabric122.LoaderComplexFabric;
import com.enderzombi102.loadercomplex.fabric122.impl.platform.FabricRegistry;
import net.minecraft.client.render.model.block.BlockModel;
import net.minecraft.client.resource.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraft.resource.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.enderzombi102.loadercomplex.fabric122.LoaderComplexFabric.LOGGER;

@Mixin( ModelBakery.class )
public abstract class ModelBakeryMixin {
	@Shadow
	@Final
	private Map<String, Identifier> itemModels;

	@Shadow
	protected abstract BlockModel loadBlockModel( Identifier location ) throws IOException;

	@Shadow
	@Final
	private Map<Identifier, BlockModel> blockModels;

	@Shadow
	@Final
	private Map<Item, List<String>> itemVariants;

	@Inject( method = "registerItemVariants", at = @At( "TAIL" ) )
	public void onRegisterItemVariants( CallbackInfo ci ) {
		List<Item> items = ((FabricRegistry) LoaderComplexFabric.INSTANCE.getContext().getRegistry()).getRegisteredItems();
		for ( Item item : items ) {
			Identifier id = Item.REGISTRY.getKey( item );
			if ( id == null ) {
				LOGGER.error( "Item {} has not been registered! H O W", item );
			} else {
				this.itemModels.put( id.toString(), id );
				if ( this.blockModels.get( id ) == null ) {
					Identifier identifier = new Identifier( id.getNamespace(), "item/" + id.getPath() );
					try {
						BlockModel blockModel = this.loadBlockModel( identifier );
						this.blockModels.put( identifier, blockModel );
					} catch ( Exception var5 ) {
						LOGGER.warn( "Unable to load item model: '{}' for item: '{}'", identifier, id, var5 );
					}
				}

				this.itemVariants.computeIfAbsent( item, key -> new ArrayList<>() );
				this.itemVariants.get( item ).add( id.toString() );
			}
		}
	}
}
