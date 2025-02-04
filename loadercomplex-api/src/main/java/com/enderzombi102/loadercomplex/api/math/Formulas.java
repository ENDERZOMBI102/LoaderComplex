package com.enderzombi102.loadercomplex.api.math;

public final class Formulas {
	private Formulas() { }

	public static float lerp( float delta, float start, float end ) {
		return start + delta * (end - start);
	}
	public static double lerp( double delta, double start, double end ) {
		return start + delta * (end - start);
	}

	public static float square( float v ) {
		return v * v;
	}
	public static double square( double v ) {
		return v * v;
	}

	public static int floor( float v ) {
		return (int) Math.floor( v );
	}
	public static int floor( double v ) {
		return (int) Math.floor( v );
	}
}
