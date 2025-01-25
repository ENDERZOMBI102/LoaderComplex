package com.enderzombi102.loadercomplex.fabric12.mixin;

import com.enderzombi102.loadercomplex.fabric12.imixin.IItemGroupMixin;
import net.minecraft.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CreativeModeTab.class)
public abstract class CreativeModeTabMixin implements IItemGroupMixin {

	@Shadow
	@Final
	@Mutable
	public static CreativeModeTab[] ALL;

	@Override
	public void lc$expandArray() {
		CreativeModeTab[] tempGroups = ALL;
		ALL = new CreativeModeTab[ tempGroups.length + 1 ];
		System.arraycopy( tempGroups, 0, ALL, 0, tempGroups.length );
	}
}
