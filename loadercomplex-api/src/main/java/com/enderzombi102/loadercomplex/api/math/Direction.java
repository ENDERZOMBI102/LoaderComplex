package com.enderzombi102.loadercomplex.api.math;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Represents the 6 cardinal directions.
 * The X axis determines the east-west direction, the Y axis determines the up-down direction, and the Z axis determines the south-north direction
 */
@ApiStatus.AvailableSince( "0.1.3" )
public enum Direction {
	DOWN( 1, -1, Direction.Axis.Y, new Vec3i( 0, -1, 0 ) ),
	UP( 0, -1, Direction.Axis.Y, new Vec3i( 0, 1, 0 ) ),
	NORTH( 3, 2, Direction.Axis.Z, new Vec3i( 0, 0, -1 ) ),
	SOUTH( 2, 0, Direction.Axis.Z, new Vec3i( 0, 0, 1 ) ),
	WEST( 5, 1, Direction.Axis.X, new Vec3i( -1, 0, 0 ) ),
	EAST( 4, 3, Direction.Axis.X, new Vec3i( 1, 0, 0 ) );

	private final int idOpposite;
	private final int idHorizontal;
	private final Direction.Axis axis;
	private final Vec3i vector;
	private static final Direction[] ALL = values();
	private static final Direction[] VALUES = Arrays.stream( ALL )
		.sorted( Comparator.comparingInt( Enum::ordinal ) )
		.toArray( Direction[]::new );
	private static final Direction[] HORIZONTAL = Arrays.stream( ALL )
		.filter( direction -> direction.getAxis().isHorizontal() )
		.sorted( Comparator.comparingInt( direction -> direction.idHorizontal ) )
		.toArray( Direction[]::new );

	Direction( final int idOpposite, final int idHorizontal, final Direction.Axis axis, final Vec3i vector ) {
		this.idHorizontal = idHorizontal;
		this.idOpposite = idOpposite;
		this.axis = axis;
		this.vector = vector;
	}

	public static Direction transform( Mat4f matrix, Direction direction ) {
		Vec3i dir = direction.getVector();
		Vec4f vec4 = matrix.transform( new Vec4f( (float) dir.x, (float) dir.y, (float) dir.z, 0.0F ) );
		return getFacing( vec4.x, vec4.y, vec4.z );
	}

	public static Stream<Direction> stream() {
		return Stream.of( ALL );
	}

	public int getHorizontal() {
		return this.idHorizontal;
	}

	public static Direction getLookDirectionForAxis( float yaw, float pitch, Direction.Axis axis ) {
		switch ( axis ) {
			case X: return EAST.pointsTo( yaw ) ? EAST : WEST;
			case Y: return pitch < 0.0F ? UP : DOWN;
			case Z: return SOUTH.pointsTo( yaw ) ? SOUTH : NORTH;
			default: throw new IllegalStateException();
		}
	}

	public Direction getOpposite() {
		return byId( this.idOpposite );
	}

	public Direction rotateClockwise( Direction.Axis axis ) {
		switch ( axis ) {
			case X: return this != WEST && this != EAST ? this.rotateXClockwise() : this;
			case Y: return this != UP && this != DOWN ? this.rotateYClockwise() : this;
			case Z: return this != NORTH && this != SOUTH ? this.rotateZClockwise() : this;
			default: throw new IllegalStateException();
		}
	}

	public Direction rotateCounterclockwise( Direction.Axis axis ) {
		switch ( axis ) {
			case X: return this != WEST && this != EAST ? this.rotateXCounterclockwise() : this;
			case Y: return this != UP && this != DOWN ? this.rotateYCounterclockwise() : this;
			case Z: return this != NORTH && this != SOUTH ? this.rotateZCounterclockwise() : this;
			default: throw new IllegalStateException();
		}
	}

	public Direction rotateYClockwise() {
		switch ( this ) {
			case NORTH: return EAST;
			case SOUTH: return WEST;
			case WEST: return NORTH;
			case EAST: return SOUTH;
			default: throw new IllegalStateException( "Unable to get Y-rotated facing of " + this );
		}
	}

	private Direction rotateXClockwise() {
		switch ( this ) {
			case DOWN: return SOUTH;
			case UP: return NORTH;
			case NORTH: return DOWN;
			case SOUTH: return UP;
			default: throw new IllegalStateException( "Unable to get X-rotated facing of " + this );
		}
	}

	private Direction rotateXCounterclockwise() {
		switch ( this ) {
			case DOWN: return NORTH;
			case UP: return SOUTH;
			case NORTH: return UP;
			case SOUTH: return DOWN;
			default: throw new IllegalStateException( "Unable to get X-rotated facing of " + this );
		}
	}

	private Direction rotateZClockwise() {
		switch ( this ) {
			case DOWN: return WEST;
			case UP: return EAST;
			case WEST: return UP;
			case EAST: return DOWN;
			default: throw new IllegalStateException( "Unable to get Z-rotated facing of " + this );
		}
	}

	private Direction rotateZCounterclockwise() {
		switch ( this ) {
			case DOWN: return EAST;
			case UP: return WEST;
			case WEST: return DOWN;
			case EAST: return UP;
			default: throw new IllegalStateException( "Unable to get Z-rotated facing of " + this );
		}
	}

	public Direction rotateYCounterclockwise() {
		switch ( this ) {
			case NORTH: return WEST;
			case SOUTH: return EAST;
			case WEST: return SOUTH;
			case EAST: return NORTH;
			default: throw new IllegalStateException( "Unable to get CCW facing of " + this );
		}
	}

	public int getOffsetX() {
		return this.vector.x;
	}

	public int getOffsetY() {
		return this.vector.y;
	}

	public int getOffsetZ() {
		return this.vector.z;
	}

	public Vec3d getUnitVector() {
		return new Vec3d( this.getOffsetX(), this.getOffsetY(), this.getOffsetZ() );
	}

	public Direction.Axis getAxis() {
		return this.axis;
	}

	public static Direction byName( @NotNull String name ) {
		return valueOf( name.toUpperCase( Locale.ROOT ) );
	}

	public static Direction byId( int id ) {
		return VALUES[Math.abs( id % VALUES.length )];
	}

	public static Direction fromHorizontal( int value ) {
		return HORIZONTAL[Math.abs( value % HORIZONTAL.length )];
	}

	public static @Nullable Direction fromVector( int x, int y, int z ) {
		if ( x == 0 ) {
			if ( y == 0 ) {
				if ( z > 0 ) {
					return SOUTH;
				}

				if ( z < 0 ) {
					return NORTH;
				}
			} else if ( z == 0 ) {
				return y > 0 ? UP : DOWN;
			}
		} else if ( y == 0 && z == 0 ) {
			return x > 0 ? EAST : WEST;
		}

		return null;
	}

	public static Direction fromRotation( double rotation ) {
		return fromHorizontal( Formulas.floor( rotation / 90.0 + 0.5 ) & 3 );
	}

	public float asRotation() {
		return (float) ((this.idHorizontal & 3) * 90);
	}

	public static Direction getFacing( double x, double y, double z ) {
		return getFacing( (float) x, (float) y, (float) z );
	}

	public static Direction getFacing( float x, float y, float z ) {
		Direction direction = NORTH;
		float f = Float.MIN_VALUE;

		for ( Direction direction2 : ALL ) {
			float g = x * (float) direction2.vector.x + y * (float) direction2.vector.y + z * (float) direction2.vector.z;
			if ( g > f ) {
				f = g;
				direction = direction2;
			}
		}

		return direction;
	}

	public static Direction getFacing( Vec3d vec ) {
		return getFacing( vec.x, vec.y, vec.z );
	}

	public Vec3i getVector() {
		return this.vector;
	}

	/**
	 * {@return whether the given yaw points to the direction}
	 *
	 * @implNote This returns whether the yaw can make an acute angle with the direction.
	 *
	 * <p>This always returns {@code false} for vertical directions.
	 */
	public boolean pointsTo( float yaw ) {
		float f = yaw * (float) (Math.PI / 180.0);
		float g = (float) -Math.sin( f );
		float h = (float) Math.cos( f );
		return (float) this.vector.x * g + (float) this.vector.z * h > 0.0F;
	}

	public enum Axis implements Predicate<Direction> {
		X {
			@Override
			public int choose( int x, int y, int z ) {
				return x;
			}

			@Override
			public double choose( double x, double y, double z ) {
				return x;
			}
		},
		Y {
			@Override
			public int choose( int x, int y, int z ) {
				return y;
			}

			@Override
			public double choose( double x, double y, double z ) {
				return y;
			}
		},
		Z {
			@Override
			public int choose( int x, int y, int z ) {
				return z;
			}

			@Override
			public double choose( double x, double y, double z ) {
				return z;
			}
		};

		public static final Axis[] VALUES = values();

		public static Axis fromName( String name ) {
			return Axis.valueOf( name.toUpperCase( Locale.ROOT ) );
		}

		public boolean isVertical() {
			return this == Y;
		}

		public boolean isHorizontal() {
			return this == X || this == Z;
		}

		public boolean test( @Nullable Direction direction ) {
			return direction != null && direction.getAxis() == this;
		}

		public abstract int choose( int x, int y, int z );

		public abstract double choose( double x, double y, double z );
	}
}
