package com.enderzombi102.loadercomplex.api.utils;

import org.jetbrains.annotations.NotNull;

public class Version {

	private final int major, minor, patch;
	private final String buildDate;

	public Version(int major, int minor, int patch, String buildDate) {
		this.major = major;
		this.minor = minor;
		this.patch = patch;
		this.buildDate = buildDate;
	}

	public Version( @NotNull String version, @NotNull String buildDate) {
		String[] parts = version.split("\\.");
		// we need all three parts of the version
		assert parts.length == 3;
		this.major = Integer.parseInt( parts[0] );
		this.minor = Integer.parseInt( parts[1] );
		this.patch = Integer.parseInt( parts[2] );
		this.buildDate = buildDate;
	}

	public int getMajor() {
		return major;
	}

	public int getMinor() {
		return minor;
	}

	public int getPatch() {
		return patch;
	}

	public String getBuildDate() {
		return buildDate;
	}
}
