package com.enderzombi102.loadercomplex.fabric12.mixin;

import com.enderzombi102.loadercomplex.fabric12.LoaderComplexFabric;
import com.enderzombi102.loadercomplex.fabric12.impl.FabricRegistry;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.BlockModel;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
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

import static com.enderzombi102.loadercomplex.fabric12.LoaderComplexFabric.LOGGER;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {

	@Shadow protected abstract BlockModel getModel(Identifier id) throws IOException;


	@Shadow
	@Final
	private Map<Item, List<String>> modelVariantNames;

	@Shadow
	@Final
	private Map<String, Identifier> modelsToBake;

	@Shadow
	@Final
	private Map<Identifier, BlockModel> bakedModels;

	@Inject( method = "method_12514", at = @At("TAIL") )
	public void onSetupItems(CallbackInfo ci) {
		List<Item> items = ( (FabricRegistry) LoaderComplexFabric.INSTANCE.getLoader().getRegistry() ).getRegisteredItems();
		for ( Item item : items ) {
			Identifier id = Item.REGISTRY.getIdentifier( item );
			if ( id == null )
				LOGGER.error( "Item {} has not been registered! H O W", item );
			else {
				this.modelsToBake.put( id.toString(), id );
				if (this.bakedModels.get(id) == null) {
					Identifier identifier = new Identifier( id.getNamespace(), "item/" + id.getPath() );
					try {
						BlockModel blockModel = this.getModel(identifier);
						this.bakedModels.put(identifier, blockModel);
					} catch (Exception var5) {
						LOGGER.warn( "Unable to load item model: '{}' for item: '{}'", identifier, id, var5 );
					}
				}
				// FIXME: This crashes somehow
//				if ( item instanceof BlockItem)
//					this.field_11315.put( id, this.method_12508( id ) );
//				else {
//					id = new Identifier( id.getNamespace(), "item/" + id.getPath() + ".json" );
//					this.field_11315.put(id, this.method_12510(id, id));
//				}
				this.modelVariantNames.computeIfAbsent( item, key -> new ArrayList<>() );
				this.modelVariantNames.get( item ).add( id.toString() );
			}
		}
	}
}
