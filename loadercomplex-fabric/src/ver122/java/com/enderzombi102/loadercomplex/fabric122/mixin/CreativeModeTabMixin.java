package com.enderzombi102.loadercomplex.fabric122.mixin;

import com.enderzombi102.loadercomplex.fabric122.imixin.ICreativeModeTabMixin;
import net.minecraft.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CreativeModeTab.class)
public abstract class CreativeModeTabMixin implements ICreativeModeTabMixin {
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
