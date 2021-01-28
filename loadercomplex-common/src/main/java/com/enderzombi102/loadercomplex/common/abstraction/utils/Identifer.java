package com.enderzombi102.loadercomplex.common.abstraction.utils;

public class Identifer {

	private final String namespace;
	private final String path;

	public Identifer(String namespace, String path) {
		this.namespace = namespace;
		this.path = path;
	}

	public static Identifer fromString(String id) {
		return new Identifer( id.substring( 0, id.indexOf(':') ), id.substring( id.indexOf(':') + 1 ) );
	}

	public String getNamespace() {
		return namespace;
	}

	public String getPath() {
		return path;
	}

	public String get(LoaderType type) {
		return this.namespace + "_" + type.toString() + ":" + this.path;
	}

	public String get() {
		return this.namespace + ":" + this.path;
	}

	public enum IdType {
		Command,
		Block,
		Item
	}



}
