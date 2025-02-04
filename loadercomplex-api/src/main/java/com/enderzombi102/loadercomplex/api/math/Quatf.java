package com.enderzombi102.loadercomplex.api.math;

public class Quatf {
	private float x;
	private float y;
	private float z;
	private float w;

	public Quatf() {
		this( 0, 0, 0, 1 );
	}
	public Quatf( float x, float y, float z, float w ) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Vec3f multiply( Vec3f vec ) {
		Vec3f quat = new Vec3f( this.x, this.y, this.z );
		Vec3f uv = quat.cross( vec );
		Vec3f uuv = quat.cross( uv );

		return uv.multiply( this.w ).add( uuv ).multiply( 2f ).add( vec );
	}
}
