package com.enderzombi102.loadercomplex.fabric17.mixin;

import com.enderzombi102.loadercomplex.fabric17.imixin.IItemMixin;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Item.class)
public abstract class ItemMixin implements IItemMixin {
	@Mutable
	@Shadow
	@Final
	protected ItemGroup group;

	@Override
	public Item lc$setGroup(ItemGroup group) {
		this.group = group;
		return (Item) (Object) this;
	}
}
