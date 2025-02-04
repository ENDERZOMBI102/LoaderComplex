package com.enderzombi102.loadercomplex.api.math;

/**
 * A vector composed of 3 integers.
 *
 * <p>This class is very often used to hold the coordinates. To hold
 * positions for entities and other non-voxels, consider using {@link Vec3d} that
 * holds values using {@code double} instead.
 *
 * <p>TODO: Candidate for value class
 *
 * @see Vec3d
 */
public class Vec3i implements Comparable<Vec3i> {
	public static final Vec3i ZERO = new Vec3i( 0, 0, 0 );
	public final int x;
	public final int y;
	public final int z;

	public Vec3i( Vec3i vec ) {
		this( vec.x, vec.y, vec.z );
	}
	public Vec3i( int x, int y, int z ) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	protected Vec3i withX( int x ) {
		return new Vec3i( x, this.y, this.z );
	}

	protected Vec3i withY( int y ) {
		return new Vec3i( this.x, y, this.z );
	}

	protected Vec3i withZ( int z ) {
		return new Vec3i( this.x, this.y, z );
	}

	/**
	 * {@return another Vec3i whose coordinates have the parameter x, y, and z
	 * added to the coordinates of this vector}
	 *
	 * <p>This method always returns an immutable object.
	 */
	public Vec3i add( int x, int y, int z ) {
		return x == 0 && y == 0 && z == 0 ? this : new Vec3i( this.x + x, this.y + y, this.z + z );
	}

	/**
	 * {@return another Vec3i whose coordinates have the coordinates of {@code vec}
	 * added to the coordinates of this vector}
	 *
	 * <p>This method always returns an immutable object.
	 */
	public Vec3i add( Vec3i vec ) {
		return this.add( vec.x, vec.y, vec.z );
	}

	/**
	 * {@return another Vec3i whose coordinates have the coordinates of {@code vec}
	 * subtracted from the coordinates of this vector}
	 *
	 * <p>This method always returns an immutable object.
	 */
	public Vec3i subtract( Vec3i vec ) {
		return this.add( -vec.x, -vec.y, -vec.z );
	}

	/**
	 * {@return a vector with all components multiplied by {@code scale}}
	 *
	 * @implNote This can return the same vector if {@code scale} equals {@code 1}.
	 */
	public Vec3i multiply( int scale ) {
		if ( scale == 1 ) {
			return this;
		} else if ( scale == 0 ) {
			return ZERO;
		}
		return new Vec3i( this.x * scale, this.y * scale, this.z * scale );
	}

	/**
	 * {@return a vector which is offset by {@code 1} in the upward direction}
	 */
	public Vec3i up() {
		return this.up( 1 );
	}

	/**
	 * {@return a vector which is offset by {@code distance} in the upward direction}
	 *
	 * @implNote This can return the same vector if {@code distance} equals {@code 0}.
	 */
	public Vec3i up( int distance ) {
		return this.offset( Direction.UP, distance );
	}

	/**
	 * {@return a vector which is offset by {@code 1} in the downward direction}
	 */
	public Vec3i down() {
		return this.down( 1 );
	}

	/**
	 * {@return a vector which is offset by {@code distance} in the downward direction}
	 *
	 * @implNote This can return the same vector if {@code distance} equals {@code 0}.
	 */
	public Vec3i down( int distance ) {
		return this.offset( Direction.DOWN, distance );
	}

	/**
	 * {@return a vector which is offset by {@code 1} in the northward direction}
	 */
	public Vec3i north() {
		return this.north( 1 );
	}

	/**
	 * {@return a vector which is offset by {@code distance} in the northward direction}
	 *
	 * @implNote This can return the same vector if {@code distance} equals {@code 0}.
	 */
	public Vec3i north( int distance ) {
		return this.offset( Direction.NORTH, distance );
	}

	/**
	 * {@return a vector which is offset by {@code 1} in the southward direction}
	 */
	public Vec3i south() {
		return this.south( 1 );
	}

	/**
	 * {@return a vector which is offset by {@code distance} in the southward direction}
	 *
	 * @implNote This can return the same vector if {@code distance} equals {@code 0}.
	 */
	public Vec3i south( int distance ) {
		return this.offset( Direction.SOUTH, distance );
	}

	/**
	 * {@return a vector which is offset by {@code 1} in the westward direction}
	 */
	public Vec3i west() {
		return this.west( 1 );
	}

	/**
	 * {@return a vector which is offset by {@code distance} in the westward direction}
	 *
	 * @implNote This can return the same vector if {@code distance} equals {@code 0}.
	 */
	public Vec3i west( int distance ) {
		return this.offset( Direction.WEST, distance );
	}

	/**
	 * {@return a vector which is offset by {@code 1} in the eastward direction}
	 */
	public Vec3i east() {
		return this.east( 1 );
	}

	/**
	 * {@return a vector which is offset by {@code distance} in the eastward direction}
	 *
	 * @implNote This can return the same vector if {@code distance} equals {@code 0}.
	 */
	public Vec3i east( int distance ) {
		return this.offset( Direction.EAST, distance );
	}

	/**
	 * {@return a vector which is offset by {@code 1} in {@code direction} direction}
	 */
	public Vec3i offset( Direction direction ) {
		return this.offset( direction, 1 );
	}

	/**
	 * {@return a vector which is offset by {@code distance} in {@code direction} direction}
	 *
	 * @implNote This can return the same vector if {@code distance} equals {@code 0}.
	 */
	public Vec3i offset( Direction direction, int distance ) {
		return distance == 0
			? this
			: new Vec3i( this.x + direction.getOffsetX() * distance, this.y + direction.getOffsetY() * distance, this.z + direction.getOffsetZ() * distance );
	}

	/**
	 * {@return a vector which is offset by {@code distance} on {@code axis} axis}
	 *
	 * @implNote This can return the same vector if {@code distance} equals {@code 0}.
	 */
	public Vec3i offset( Direction.Axis axis, int distance ) {
		if ( distance == 0 ) {
			return this;
		} else {
			int i = axis == Direction.Axis.X ? distance : 0;
			int j = axis == Direction.Axis.Y ? distance : 0;
			int k = axis == Direction.Axis.Z ? distance : 0;
			return new Vec3i( this.x + i, this.y + j, this.z + k );
		}
	}

	public Vec3i cross( Vec3i vec ) {
		return new Vec3i(
			this.y * vec.z - this.z * vec.y,
			this.z * vec.x - this.x * vec.z,
			this.x * vec.y - this.y * vec.x
		);
	}

	/**
	 * {@return whether the distance between here and {@code vec} is less than {@code distance}}
	 */
	public boolean isWithinDistance( Vec3i vec, double distance ) {
		return this.getSquaredDistance( vec ) < Formulas.square( distance );
	}

	/**
	 * {@return whether the distance between here and {@code pos} is less than {@code distance}}
	 */
	public boolean isWithinDistance( Vec3d pos, double distance ) {
		return this.getSquaredDistance( pos.toVec3i() ) < Formulas.square( distance );
	}

	/**
	 * {@return the squared distance between here (center) and {@code vec}}
	 *
	 * @see #getSquaredDistance(double, double, double)
	 * @see #getSquaredDistanceFromCenter(double, double, double)
	 */
	public double getSquaredDistance( Vec3i vec ) {
		return this.getSquaredDistance( vec.x, vec.y, vec.z );
	}

	/**
	 * {@return the squared distance between the center of this voxel and {@code (x, y, z)}}
	 * This is equivalent to {@link Vec3d#ofCenter(Vec3i)
	 * Vec3d.ofCenter(this).squaredDistanceTo(x, y, z)}.
	 */
	public double getSquaredDistanceFromCenter( double x, double y, double z ) {
		double d = (double) this.x + 0.5 - x;
		double e = (double) this.y + 0.5 - y;
		double f = (double) this.z + 0.5 - z;
		return d * d + e * e + f * f;
	}

	/**
	 * {@return the squared distance between here and {@code (x, y, z)}}
	 * This is equivalent to {@code Vec3d.of(this).squaredDistanceTo(x, y, z)}.
	 */
	public double getSquaredDistance( double x, double y, double z ) {
		double d = (double) this.x - x;
		double e = (double) this.y - y;
		double f = (double) this.z - z;
		return d * d + e * e + f * f;
	}

	/**
	 * {@return the Manhattan distance between here and {@code vec}}
	 *
	 * <p>Manhattan distance, also called taxicab distance or snake distance, is the
	 * distance measured as the sum of the absolute differences of their coordinates.
	 * For example, the Manhattan distance between {@code (0, 0, 0)} and {@code (1, 1, 1)}
	 * is {@code 3}.
	 */
	public int getManhattanDistance( Vec3i vec ) {
		float f = (float) Math.abs( vec.x - this.x );
		float g = (float) Math.abs( vec.y - this.y );
		float h = (float) Math.abs( vec.z - this.z );
		return (int) (f + g + h);
	}

	/**
	 * Converts to a {@link Vec3d}.
	 */
	public Vec3d toVec3d() {
		return new Vec3d( this.x, this.y, this.z );
	}

	@Override
	public boolean equals( Object o ) {
		if ( o == null || getClass() != o.getClass() ) return false;
		Vec3i vec3i = (Vec3i) o;
		return x == vec3i.x && y == vec3i.y && z == vec3i.z;
	}

	public int hashCode() {
		return (this.y + this.z * 31) * 31 + this.x;
	}

	public int compareTo( Vec3i o ) {
		if ( this.y == o.y ) {
			return this.z == o.z ? this.x - o.x : this.z - o.z;
		} else {
			return this.y - o.y;
		}
	}

	@Override
	public String toString() {
		return String.format( "Vec3i{ x=%s, y=%s, z=%s }", this.x, this.y, this.z );
	}
}
