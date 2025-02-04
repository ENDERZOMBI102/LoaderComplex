package com.enderzombi102.loadercomplex.fabric122.imixin;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.Map;

public interface IItemMixin {
	Map<Block, Item> lc$getBlockItemRegistry();
}
