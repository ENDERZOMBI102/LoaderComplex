package com.enderzombi102.loadercomplex.api.math;

import java.util.Objects;


/**
 * An immutable vector composed of 3 floats.
 *
 * <p>This vector class is used for representing position, velocity,
 * rotation, color, etc.
 *
 * <p>This vector has proper {@link #hashCode()} and {@link #equals(Object)}
 * implementations and can be used as a map key.
 *
 * <p>TODO: Candidate for value class
 * @see Vec3i
 */
public class Vec3f {
	public static final Vec3f ZERO = new Vec3f( 0.0f, 0.0f, 0.0f );
	public final float x;
	public final float y;
	public final float z;

	public Vec3f( Vec3f vec ) {
		this( vec.x, vec.y, vec.z );
	}
	public Vec3f( float x, float y, float z ) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Subtracts this vector from the given vector.
	 *
	 * @return the difference between the given vector and this vector
	 * @see #subtract(Vec3f)
	 */
	public Vec3f relativize( Vec3f vec ) {
		return new Vec3f( vec.x - this.x, vec.y - this.y, vec.z - this.z );
	}

	/**
	 * Normalizes this vector.
	 *
	 * <p>Normalized vector is a vector with the same direction but with
	 * length 1. Each coordinate of normalized vector has value between 0
	 * and 1.
	 *
	 * @return the normalized vector of this vector
	 */
	public Vec3f normalize() {
		float d = (float) Math.sqrt( this.x * this.x + this.y * this.y + this.z * this.z );
		return d < 1.0E-4 ? ZERO : new Vec3f( this.x / d, this.y / d, this.z / d );
	}

	/**
	 * Returns the dot product of this vector and the given vector.
	 */
	public float dot( Vec3f vec ) {
		return this.x * vec.x + this.y * vec.y + this.z * vec.z;
	}

	/**
	 * Returns the cross product of this vector and the given vector.
	 */
	public Vec3f cross( Vec3f vec ) {
		return new Vec3f( this.y * vec.z - this.z * vec.y, this.z * vec.x - this.x * vec.z, this.x * vec.y - this.y * vec.x );
	}

	/**
	 * Subtracts the given vector from this vector.
	 *
	 * @return the difference between this vector and the given vector
	 * @see #subtract(float, float, float)
	 * @see #relativize(Vec3f)
	 */
	public Vec3f subtract( Vec3f vec ) {
		return this.subtract( vec.x, vec.y, vec.z );
	}

	/**
	 * Subtracts the given vector from this vector.
	 *
	 * @return the difference between this vector and the given vector
	 * @see #relativize(Vec3f)
	 */
	public Vec3f subtract( float x, float y, float z ) {
		return this.add( -x, -y, -z );
	}

	/**
	 * Returns the sum of this vector and the given vector.
	 *
	 * @see #add(float, float, float)
	 */
	public Vec3f add( Vec3f vec ) {
		return this.add( vec.x, vec.y, vec.z );
	}

	/**
	 * Returns the sum of this vector and the given vector.
	 *
	 * @see #add(Vec3f)
	 */
	public Vec3f add( float x, float y, float z ) {
		return new Vec3f( this.x + x, this.y + y, this.z + z );
	}

	/**
	 * Checks if the distance between this vector and the given position is
	 * less than {@code radius}.
	 */
	public boolean isInRange( Vec3f pos, float radius ) {
		return this.squaredDistanceTo( pos.x, pos.y, pos.z ) < radius * radius;
	}

	/**
	 * Returns the distance between this vector and the given vector.
	 *
	 * @see #squaredDistanceTo(Vec3f)
	 */
	public float distanceTo( Vec3f vec ) {
		float d = vec.x - this.x;
		float e = vec.y - this.y;
		float f = vec.z - this.z;
		return (float) Math.sqrt( d * d + e * e + f * f );
	}

	/**
	 * Returns the squared distance between this vector and the given vector.
	 *
	 * <p>Can be used for fast comparison between distances.
	 *
	 * @see #squaredDistanceTo(float, float, float)
	 * @see #distanceTo(Vec3f)
	 */
	public float squaredDistanceTo( Vec3f vec ) {
		float d = vec.x - this.x;
		float e = vec.y - this.y;
		float f = vec.z - this.z;
		return d * d + e * e + f * f;
	}

	/**
	 * Returns the squared distance between this vector and the given vector.
	 *
	 * <p>Can be used for fast comparison between distances.
	 *
	 * @see #squaredDistanceTo(Vec3f)
	 * @see #distanceTo(Vec3f)
	 */
	public float squaredDistanceTo( float x, float y, float z ) {
		float d = x - this.x;
		float e = y - this.y;
		float f = z - this.z;
		return d * d + e * e + f * f;
	}

	/**
	 * Return a vector whose coordinates are the coordinates of this vector
	 * each multiplied by the given scalar value.
	 *
	 * @see #multiply(Vec3f)
	 * @see #multiply(float, float, float)
	 */
	public Vec3f multiply( float value ) {
		return this.multiply( value, value, value );
	}

	/**
	 * Creates a vector with the same length but with the opposite direction.
	 */
	public Vec3f negate() {
		return this.multiply( -1.0f );
	}

	/**
	 * Returns a vector whose coordinates are the product of each pair of
	 * coordinates in this vector and the given vector.
	 *
	 * @see #multiply(float, float, float)
	 * @see #multiply(float)
	 */
	public Vec3f multiply( Vec3f vec ) {
		return this.multiply( vec.x, vec.y, vec.z );
	}

	/**
	 * Returns a vector whose coordinates are the product of each pair of
	 * coordinates in this vector and the given vector.
	 *
	 * @see #multiply(Vec3f)
	 * @see #multiply(float)
	 */
	public Vec3f multiply( float x, float y, float z ) {
		return new Vec3f( this.x * x, this.y * y, this.z * z );
	}

	/**
	 * {@return the length of this vector}
	 *
	 * <p>The length of a vector is equivalent to the distance between that
	 * vector and the {@linkplain #ZERO} vector.
	 *
	 * @see #lengthSquared()
	 */
	public float length() {
		return (float) Math.sqrt( this.x * this.x + this.y * this.y + this.z * this.z );
	}

	/**
	 * {@return the squared length of this vector}
	 *
	 * <p>Can be used for fast comparison between lengths.
	 *
	 * @see #length()
	 */
	public float lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z;
	}

	/**
	 * Performs linear interpolation from this vector to the given vector.
	 *
	 * @param delta the interpolation coefficient in the range between 0 and 1
	 * @param to    the vector to interpolate to
	 */
	public Vec3f lerp( Vec3f to, float delta ) {
		return new Vec3f( Formulas.lerp( delta, this.x, to.x ), Formulas.lerp( delta, this.y, to.y ), Formulas.lerp( delta, this.z, to.z ) );
	}

	/**
	 * Rotates this vector by the given angle counterclockwise around the X axis.
	 *
	 * @param angle the angle in radians
	 */
	public Vec3f rotateX( float angle ) {
		float f = (float) Math.cos( angle );
		float g = (float) Math.sin( angle );
		float e = this.y * (float) f + this.z * (float) g;
		float h = this.z * (float) f - this.y * (float) g;
		return new Vec3f( this.x, e, h );
	}

	/**
	 * Rotates this vector by the given angle counterclockwise around the Y axis.
	 *
	 * @param angle the angle in radians
	 */
	public Vec3f rotateY( float angle ) {
		float f = (float) Math.cos( angle );
		float g = (float) Math.sin( angle );
		float d = this.x * (float) f + this.z * (float) g;
		float h = this.z * (float) f - this.x * (float) g;
		return new Vec3f( d, this.y, h );
	}

	/**
	 * Rotates this vector by the given angle counterclockwise around the Z axis.
	 *
	 * @param angle the angle in radians
	 */
	public Vec3f rotateZ( float angle ) {
		float f = (float) Math.cos( angle );
		float g = (float) Math.sin( angle );
		float d = this.x * (float) f + this.y * (float) g;
		float e = this.y * (float) f - this.x * (float) g;
		return new Vec3f( d, e, this.z );
	}

	/**
	 * Floors and converts to a {@link Vec3d}.
	 */
	public Vec3d toVec3d() {
		return new Vec3d( this.x, this.y, this.z );
	}
	/**
	 * Floors and converts to a {@link Vec3i}.
	 */
	public Vec3i toVec3i() {
		return new Vec3i( Formulas.floor( this.x ), Formulas.floor( this.y ), Formulas.floor( this.z ) );
	}

	public Vec3f offset( Direction direction, float value ) {
		Vec3i vec3i = direction.getVector();
		return new Vec3f( this.x + value * (float) vec3i.x, this.y + value * (float) vec3i.y, this.z + value * (float) vec3i.z );
	}

	@Override
	public boolean equals( Object o ) {
		if ( o == null || getClass() != o.getClass() ) return false;
		Vec3f vec3d = (Vec3f) o;
		return Double.compare( x, vec3d.x ) == 0 && Double.compare( y, vec3d.y ) == 0 && Double.compare( z, vec3d.z ) == 0;
	}

	public int hashCode() {
		return Objects.hash( this.x, this.y, this.z );
	}

	@Override
	public String toString() {
		return String.format( "Vec3d{ x=%s, y=%s, z=%s }", this.x, this.y, this.z );
	}


	// STATIC METHODS
	/**
	 * Copies the given vector.
	 */
	public static Vec3f of( Vec3i vec ) {
		return new Vec3f( vec.x, vec.y, vec.z );
	}

	/**
	 * {@return a new vector from {@code vec} with {@code deltaX}, {@code deltaY}, and
	 * {@code deltaZ} added to X, Y, Z values, respectively}
	 */
	public static Vec3f add( Vec3i vec, float deltaX, float deltaY, float deltaZ ) {
		return new Vec3f( (float) vec.x + deltaX, (float) vec.y + deltaY, (float) vec.z + deltaZ );
	}

	/**
	 * Creates a vector representing the center of the given block position.
	 */
	public static Vec3f ofCenter( Vec3i vec ) {
		return add( vec, 0.5f, 0.5f, 0.5f );
	}

	/**
	 * Creates a vector representing the bottom center of the given block
	 * position.
	 *
	 * <p>The bottom center of a block position {@code pos} is
	 * {@code (pos.x + 0.5, pos.y, pos.z + 0.5)}.
	 *
	 * @see #ofCenter(Vec3i)
	 */
	public static Vec3f ofBottomCenter( Vec3i vec ) {
		return add( vec, 0.5f, 0.0f, 0.5f );
	}

	/**
	 * Creates a vector representing the center of the given block position but
	 * with the given offset for the Y coordinate.
	 *
	 * @return a vector of {@code (vec.x + 0.5, vec.y + deltaY,
	 * vec.z + 0.5)}
	 */
	public static Vec3f ofCenter( Vec3i vec, float deltaY ) {
		return add( vec, 0.5f, deltaY, 0.5f );
	}

	/**
	 * Converts pitch and yaw into a direction vector.
	 *
	 * @param polar the vector composed of pitch and yaw
	 * @see #fromPolar(float, float)
	 */
	public static Vec3f fromPolar( Vec2f polar ) {
		return fromPolar( polar.x, polar.y );
	}

	/**
	 * Converts pitch and yaw into a direction vector.
	 *
	 * @see #fromPolar(Vec2f)
	 */
	public static Vec3f fromPolar( float pitch, float yaw ) {
		float f = (float) Math.cos( -yaw * (float) (Math.PI / 180.0) - (float) Math.PI );
		float g = (float) Math.sin( -yaw * (float) (Math.PI / 180.0) - (float) Math.PI );
		float h = (float) -Math.cos( -pitch * (float) (Math.PI / 180.0) );
		float i = (float) Math.sin( -pitch * (float) (Math.PI / 180.0) );
		return new Vec3f( g * h, i, f * h );
	}
}
