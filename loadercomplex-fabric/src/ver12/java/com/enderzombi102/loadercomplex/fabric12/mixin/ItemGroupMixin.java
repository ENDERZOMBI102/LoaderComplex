package com.enderzombi102.loadercomplex.fabric12.mixin;

import com.enderzombi102.loadercomplex.fabric12.imixin.IItemGroupMixin;
import net.minecraft.item.itemgroup.ItemGroup;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemGroup.class)
public abstract class ItemGroupMixin implements IItemGroupMixin {

	@Shadow
	@Final
	@Mutable
	public static ItemGroup[] itemGroups;

	@Override
	public void lc$expandArray() {
		ItemGroup[] tempGroups = itemGroups;
		itemGroups = new ItemGroup[ itemGroups.length + 1 ];
		System.arraycopy( tempGroups, 0, itemGroups, 0, tempGroups.length );
	}
}
