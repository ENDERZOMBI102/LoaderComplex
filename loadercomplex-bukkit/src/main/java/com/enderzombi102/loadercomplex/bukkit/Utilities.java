package com.enderzombi102.loadercomplex.bukkit;

import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class Utilities {

	public static @Nullable Material getMaterialFor(ResourceIdentifier identifier) {
		return Arrays.stream( Material.values() )
				.filter( material -> material.name().equalsIgnoreCase( identifier.getPath() ) )
				.findFirst()
				.orElse(null);
	}

}
