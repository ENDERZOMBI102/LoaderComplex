package com.enderzombi102.loaderComplex.fabric12.mixin;

import com.enderzombi102.loaderComplex.fabric12.imixin.IItemMixin;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(Item.class)
public class ItemMixin implements IItemMixin {

	@Shadow
	@Final
	private static Map<Block, Item> BLOCK_ITEMS;

	@Override
	public Map<Block, Item> lc$getBlockItemRegistry() {
		return BLOCK_ITEMS;
	}
}
