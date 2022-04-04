package com.enderzombi102.loadercomplex.api.utils;

public class Position {
	public final int x, y, z;

	public Position(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Position() {
		this( 0, 0, 0 );
	}

	public static Position of(int x, int y, int z) {
		return new Position( x, y, z );
	}
}
