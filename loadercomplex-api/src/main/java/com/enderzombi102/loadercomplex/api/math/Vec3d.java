package com.enderzombi102.loadercomplex.api.math;

import java.util.Objects;

/**
 * An immutable vector composed of 3 doubles.
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
public class Vec3d {
	public static final Vec3d ZERO = new Vec3d( 0.0, 0.0, 0.0 );
	public final double x;
	public final double y;
	public final double z;

	public Vec3d( Vec3d vec ) {
		this( vec.x, vec.y, vec.z );
	}
	public Vec3d( double x, double y, double z ) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Subtracts this vector from the given vector.
	 *
	 * @return the difference between the given vector and this vector
	 * @see #subtract(Vec3d)
	 */
	public Vec3d relativize( Vec3d vec ) {
		return new Vec3d( vec.x - this.x, vec.y - this.y, vec.z - this.z );
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
	public Vec3d normalize() {
		double d = Math.sqrt( this.x * this.x + this.y * this.y + this.z * this.z );
		return d < 1.0E-4 ? ZERO : new Vec3d( this.x / d, this.y / d, this.z / d );
	}

	/**
	 * Returns the dot product of this vector and the given vector.
	 */
	public double dot( Vec3d vec ) {
		return this.x * vec.x + this.y * vec.y + this.z * vec.z;
	}

	/**
	 * Returns the cross product of this vector and the given vector.
	 */
	public Vec3d cross( Vec3d vec ) {
		return new Vec3d( this.y * vec.z - this.z * vec.y, this.z * vec.x - this.x * vec.z, this.x * vec.y - this.y * vec.x );
	}

	/**
	 * Subtracts the given vector from this vector.
	 *
	 * @return the difference between this vector and the given vector
	 * @see #subtract(double, double, double)
	 * @see #relativize(Vec3d)
	 */
	public Vec3d subtract( Vec3d vec ) {
		return this.subtract( vec.x, vec.y, vec.z );
	}

	/**
	 * Subtracts the given vector from this vector.
	 *
	 * @return the difference between this vector and the given vector
	 * @see #relativize(Vec3d)
	 */
	public Vec3d subtract( double x, double y, double z ) {
		return this.add( -x, -y, -z );
	}

	/**
	 * Returns the sum of this vector and the given vector.
	 *
	 * @see #add(double, double, double)
	 */
	public Vec3d add( Vec3d vec ) {
		return this.add( vec.x, vec.y, vec.z );
	}

	/**
	 * Returns the sum of this vector and the given vector.
	 *
	 * @see #add(Vec3d)
	 */
	public Vec3d add( double x, double y, double z ) {
		return new Vec3d( this.x + x, this.y + y, this.z + z );
	}

	/**
	 * Checks if the distance between this vector and the given position is
	 * less than {@code radius}.
	 */
	public boolean isInRange( Vec3d pos, double radius ) {
		return this.squaredDistanceTo( pos.x, pos.y, pos.z ) < radius * radius;
	}

	/**
	 * Returns the distance between this vector and the given vector.
	 *
	 * @see #squaredDistanceTo(Vec3d)
	 */
	public double distanceTo( Vec3d vec ) {
		double d = vec.x - this.x;
		double e = vec.y - this.y;
		double f = vec.z - this.z;
		return Math.sqrt( d * d + e * e + f * f );
	}

	/**
	 * Returns the squared distance between this vector and the given vector.
	 *
	 * <p>Can be used for fast comparison between distances.
	 *
	 * @see #squaredDistanceTo(double, double, double)
	 * @see #distanceTo(Vec3d)
	 */
	public double squaredDistanceTo( Vec3d vec ) {
		double d = vec.x - this.x;
		double e = vec.y - this.y;
		double f = vec.z - this.z;
		return d * d + e * e + f * f;
	}

	/**
	 * Returns the squared distance between this vector and the given vector.
	 *
	 * <p>Can be used for fast comparison between distances.
	 *
	 * @see #squaredDistanceTo(Vec3d)
	 * @see #distanceTo(Vec3d)
	 */
	public double squaredDistanceTo( double x, double y, double z ) {
		double d = x - this.x;
		double e = y - this.y;
		double f = z - this.z;
		return d * d + e * e + f * f;
	}

	/**
	 * Return a vector whose coordinates are the coordinates of this vector
	 * each multiplied by the given scalar value.
	 *
	 * @see #multiply(Vec3d)
	 * @see #multiply(double, double, double)
	 */
	public Vec3d multiply( double value ) {
		return this.multiply( value, value, value );
	}

	/**
	 * Creates a vector with the same length but with the opposite direction.
	 */
	public Vec3d negate() {
		return this.multiply( -1.0 );
	}

	/**
	 * Returns a vector whose coordinates are the product of each pair of
	 * coordinates in this vector and the given vector.
	 *
	 * @see #multiply(double, double, double)
	 * @see #multiply(double)
	 */
	public Vec3d multiply( Vec3d vec ) {
		return this.multiply( vec.x, vec.y, vec.z );
	}

	/**
	 * Returns a vector whose coordinates are the product of each pair of
	 * coordinates in this vector and the given vector.
	 *
	 * @see #multiply(Vec3d)
	 * @see #multiply(double)
	 */
	public Vec3d multiply( double x, double y, double z ) {
		return new Vec3d( this.x * x, this.y * y, this.z * z );
	}

	/**
	 * {@return the length of this vector}
	 *
	 * <p>The length of a vector is equivalent to the distance between that
	 * vector and the {@linkplain #ZERO} vector.
	 *
	 * @see #lengthSquared()
	 */
	public double length() {
		return Math.sqrt( this.x * this.x + this.y * this.y + this.z * this.z );
	}

	/**
	 * {@return the squared length of this vector}
	 *
	 * <p>Can be used for fast comparison between lengths.
	 *
	 * @see #length()
	 */
	public double lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z;
	}

	/**
	 * Performs linear interpolation from this vector to the given vector.
	 *
	 * @param delta the interpolation coefficient in the range between 0 and 1
	 * @param to    the vector to interpolate to
	 */
	public Vec3d lerp( Vec3d to, double delta ) {
		return new Vec3d( Formulas.lerp( delta, this.x, to.x ), Formulas.lerp( delta, this.y, to.y ), Formulas.lerp( delta, this.z, to.z ) );
	}

	/**
	 * Rotates this vector by the given angle counterclockwise around the X axis.
	 *
	 * @param angle the angle in radians
	 */
	public Vec3d rotateX( float angle ) {
		float f = (float) Math.cos( angle );
		float g = (float) Math.sin( angle );
		double e = this.y * (double) f + this.z * (double) g;
		double h = this.z * (double) f - this.y * (double) g;
		return new Vec3d( this.x, e, h );
	}

	/**
	 * Rotates this vector by the given angle counterclockwise around the Y axis.
	 *
	 * @param angle the angle in radians
	 */
	public Vec3d rotateY( float angle ) {
		float f = (float) Math.cos( angle );
		float g = (float) Math.sin( angle );
		double d = this.x * (double) f + this.z * (double) g;
		double h = this.z * (double) f - this.x * (double) g;
		return new Vec3d( d, this.y, h );
	}

	/**
	 * Rotates this vector by the given angle counterclockwise around the Z axis.
	 *
	 * @param angle the angle in radians
	 */
	public Vec3d rotateZ( float angle ) {
		float f = (float) Math.cos( angle );
		float g = (float) Math.sin( angle );
		double d = this.x * (double) f + this.y * (double) g;
		double e = this.y * (double) f - this.x * (double) g;
		return new Vec3d( d, e, this.z );
	}

	/**
	 * Floors and converts to a {@link Vec3f}.
	 */
	public Vec3f toVec3f() {
		return new Vec3f( (float) this.x, (float) this.y, (float) this.z );
	}

	/**
	 * Floors and converts to a {@link Vec3i}.
	 */
	public Vec3i toVec3i() {
		return new Vec3i( Formulas.floor( this.x ), Formulas.floor( this.y ), Formulas.floor( this.z ) );
	}

	public Vec3d offset( Direction direction, double value ) {
		Vec3i vec3i = direction.getVector();
		return new Vec3d( this.x + value * (double) vec3i.x, this.y + value * (double) vec3i.y, this.z + value * (double) vec3i.z );
	}

	@Override
	public boolean equals( Object o ) {
		if ( o == null || getClass() != o.getClass() ) return false;
		Vec3d vec3d = (Vec3d) o;
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
	public static Vec3d of( Vec3i vec ) {
		return new Vec3d( vec.x, vec.y, vec.z );
	}

	/**
	 * {@return a new vector from {@code vec} with {@code deltaX}, {@code deltaY}, and
	 * {@code deltaZ} added to X, Y, Z values, respectively}
	 */
	public static Vec3d add( Vec3i vec, double deltaX, double deltaY, double deltaZ ) {
		return new Vec3d( (double) vec.x + deltaX, (double) vec.y + deltaY, (double) vec.z + deltaZ );
	}

	/**
	 * Creates a vector representing the center of the given block position.
	 */
	public static Vec3d ofCenter( Vec3i vec ) {
		return add( vec, 0.5, 0.5, 0.5 );
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
	public static Vec3d ofBottomCenter( Vec3i vec ) {
		return add( vec, 0.5, 0.0, 0.5 );
	}

	/**
	 * Creates a vector representing the center of the given block position but
	 * with the given offset for the Y coordinate.
	 *
	 * @return a vector of {@code (vec.x + 0.5, vec.y + deltaY,
	 * vec.z + 0.5)}
	 */
	public static Vec3d ofCenter( Vec3i vec, double deltaY ) {
		return add( vec, 0.5, deltaY, 0.5 );
	}

	/**
	 * Converts pitch and yaw into a direction vector.
	 *
	 * @param polar the vector composed of pitch and yaw
	 * @see #fromPolar(float, float)
	 */
	public static Vec3d fromPolar( Vec2f polar ) {
		return fromPolar( polar.x, polar.y );
	}

	/**
	 * Converts pitch and yaw into a direction vector.
	 *
	 * @see #fromPolar(Vec2f)
	 */
	public static Vec3d fromPolar( float pitch, float yaw ) {
		float f = (float) Math.cos( -yaw * (float) (Math.PI / 180.0) - (float) Math.PI );
		float g = (float) Math.sin( -yaw * (float) (Math.PI / 180.0) - (float) Math.PI );
		float h = (float) -Math.cos( -pitch * (float) (Math.PI / 180.0) );
		float i = (float) Math.sin( -pitch * (float) (Math.PI / 180.0) );
		return new Vec3d( g * h, i, f * h );
	}
}
