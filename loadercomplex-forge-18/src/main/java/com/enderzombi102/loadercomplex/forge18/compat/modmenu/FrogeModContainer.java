package com.enderzombi102.loadercomplex.forge18.compat.modmenu;

import com.enderzombi102.loadercomplex.addonloader.AddonContainer;
import net.minecraftforge.fml.ModContainer;

import java.util.Objects;

public class FrogeModContainer extends ModContainer {
	private final AddonContainer container;

	public FrogeModContainer(AddonContainer container) {
		super( new FrogeModInfo( container, null ) );
		this.container = container;
	}

	@Override
	public boolean matches(Object mod) {
		return Objects.equals( mod, this.getMod() );
	}

	@Override
	public Object getMod() {
		return this.container.getImplementation();
	}
}
