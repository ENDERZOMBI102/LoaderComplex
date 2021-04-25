package com.enderzombi102.loadercomplex.abstraction.utils;

public class ResourceIdentifier {

	private final String namespace, path;

	public ResourceIdentifier(String namespace, String path) {
		this.namespace = namespace;
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}

	public String getNamespace() {
		return this.namespace;
	}

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

	public int hashCode() {
		return 31 * this.namespace.hashCode() + this.path.hashCode();
	}

	public int compareTo(ResourceIdentifier identifier) {
		int i = this.path.compareTo(identifier.path);
		if (i == 0) {
			i = this.namespace.compareTo(identifier.namespace);
		}

		return i;
	}


}
