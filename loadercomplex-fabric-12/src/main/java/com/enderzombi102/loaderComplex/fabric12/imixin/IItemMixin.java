package com.enderzombi102.loaderComplex.fabric12.imixin;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.Map;

public interface IItemMixin {
	Map<Block, Item> lc$getBlockItemRegistry();
}
