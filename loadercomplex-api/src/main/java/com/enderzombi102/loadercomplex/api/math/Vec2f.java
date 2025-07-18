package com.enderzombi102.loadercomplex.api.math;

import java.util.Objects;

/**
 * An immutable vector composed of 2 floats.
 */
public class Vec2f {
	public static final Vec2f ZERO = new Vec2f( 0.0F, 0.0F );
	public static final Vec2f SOUTH_EAST_UNIT = new Vec2f( 1.0F, 1.0F );
	public static final Vec2f EAST_UNIT = new Vec2f( 1.0F, 0.0F );
	public static final Vec2f WEST_UNIT = new Vec2f( -1.0F, 0.0F );
	public static final Vec2f SOUTH_UNIT = new Vec2f( 0.0F, 1.0F );
	public static final Vec2f NORTH_UNIT = new Vec2f( 0.0F, -1.0F );
	public static final Vec2f MAX_SOUTH_EAST = new Vec2f( Float.MAX_VALUE, Float.MAX_VALUE );
	public static final Vec2f MIN_SOUTH_EAST = new Vec2f( Float.MIN_VALUE, Float.MIN_VALUE );
	public final float x;
	public final float y;

	public Vec2f( float x, float y ) {
		this.x = x;
		this.y = y;
	}

	public Vec2f multiply( float value ) {
		return new Vec2f( this.x * value, this.y * value );
	}

	public float dot( Vec2f vec ) {
		return this.x * vec.x + this.y * vec.y;
	}

	public Vec2f add( Vec2f vec ) {
		return new Vec2f( this.x + vec.x, this.y + vec.y );
	}

	public Vec2f add( float value ) {
		return new Vec2f( this.x + value, this.y + value );
	}

	public Vec2f normalize() {
		float f = (float) Math.sqrt( this.x * this.x + this.y * this.y );
		return f < 1.0E-4F ? ZERO : new Vec2f( this.x / f, this.y / f );
	}

	public float length() {
		return (float) Math.sqrt( this.x * this.x + this.y * this.y );
	}

	public float lengthSquared() {
		return this.x * this.x + this.y * this.y;
	}

	public float distanceSquared( Vec2f vec ) {
		float f = vec.x - this.x;
		float g = vec.y - this.y;
		return f * f + g * g;
	}

	public Vec2f negate() {
		return new Vec2f( -this.x, -this.y );
	}

	public boolean equals( Vec2f other ) {
		return this.x == other.x && this.y == other.y;
	}

	@Override
	public boolean equals( Object o ) {
		if ( o == null || getClass() != o.getClass() ) return false;
		Vec2f vec2f = (Vec2f) o;
		return Float.compare( x, vec2f.x ) == 0 && Float.compare( y, vec2f.y ) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash( this.x, this.y );
	}

	@Override
	public String toString() {
		return String.format( "Vec2f{ x=%s, y=%s }", this.x, this.y );
	}
}
