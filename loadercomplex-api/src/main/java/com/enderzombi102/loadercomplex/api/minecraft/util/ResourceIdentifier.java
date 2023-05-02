package com.enderzombi102.loadercomplex.api.minecraft.util;

import org.jetbrains.annotations.NotNull;

/**
 * Used to identify a resource
 */
public class ResourceIdentifier implements Comparable<ResourceIdentifier> {
	private final String namespace, path;

	public ResourceIdentifier(@NotNull String namespace, @NotNull String path) {
		this.namespace = namespace;
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}

	public String getNamespace() {
		return this.namespace;
	}

	/**
	 * Returns a string in {namespace}:{path} format
	 */
	public String toString() {
		return this.namespace + ':' + this.path;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (! (object instanceof ResourceIdentifier) ) {
			return false;
		} else {
			ResourceIdentifier identifier = (ResourceIdentifier) object;
			return this.namespace.equals(identifier.namespace) && this.path.equals(identifier.path);
		}
	}

	@Override
	public int hashCode() {
		return 31 * this.namespace.hashCode() + this.path.hashCode();
	}

	@Override
	public int compareTo(ResourceIdentifier identifier) {
		int i = this.path.compareTo(identifier.path);
		if (i == 0) {
			i = this.namespace.compareTo(identifier.namespace);
		}

		return i;
	}

	/**
	 * @param string a string representing a resource identifier, may or may not be namespaced.
	 * @return a {@link ResourceIdentifier} representation of that string
	*/
	public static ResourceIdentifier ri( @NotNull String string ) {
		if ( string.contains(":") ) {
			// it's a namespaced name
			String[] parts = string.split(":");
			return new ResourceIdentifier( parts[0], parts[1] );
		} else {
			// not a namespaced name, default namespace to minecraft
			return new ResourceIdentifier( "minecraft", string );
		}
	}
}
