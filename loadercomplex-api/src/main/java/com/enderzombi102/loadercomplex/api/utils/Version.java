package com.enderzombi102.loadercomplex.api.utils;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a version
 */
public final class Version {
	private final int major, minor, patch;
	private final @NotNull String buildDate;
	private final @NotNull String buildTime;

	public Version( @NotNull String version, @NotNull String buildDate ) {
		String[] parts = version.split("\\.");
		// we need all three parts of the version
		assert parts.length == 3;
		this.major = Integer.parseInt( parts[0] );
		this.minor = Integer.parseInt( parts[1] );
		this.patch = Integer.parseInt( parts[2] );
		this.buildDate = buildDate.substring( 0, buildDate.indexOf('T') );
		this.buildTime = buildDate.substring( buildDate.indexOf('T') );
	}

	public Version( @NotNull String version, @NotNull String buildDate, @NotNull String buildTime ) {
		String[] parts = version.split("\\.");
		// we need all three parts of the version
		assert parts.length == 3;
		this.major = Integer.parseInt( parts[0] );
		this.minor = Integer.parseInt( parts[1] );
		this.patch = Integer.parseInt( parts[2] );
		this.buildDate = buildDate;
		this.buildTime = buildTime;
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

	public @NotNull String getBuildDate() {
		return buildDate;
	}

	public @NotNull String getBuildTime() {
		return buildTime;
	}

	public @NotNull String asString() {
		return major + "." + minor + "." + patch + "-build." + buildDate.replaceAll( "-", "" );
	}

	@Override
	public String toString() {
		return String.format(
			"Version{major=%s, minor=%s, patch=%s, buildDate='%s', buildTime='%s'}",
			major,
			minor,
			patch,
			buildDate,
			buildTime
		);
	}
}
