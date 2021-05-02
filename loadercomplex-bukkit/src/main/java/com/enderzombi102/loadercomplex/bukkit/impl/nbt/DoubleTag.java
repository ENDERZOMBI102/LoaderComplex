package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DoubleTag extends AbstractNumberTag {
	private double value;

	DoubleTag() {
	}

	public DoubleTag(double value) {
		this.value = value;
	}

	void write(DataOutput output) throws IOException {
		output.writeDouble(this.value);
	}

	void method_32150(DataInput dataInput, int i, PositionTracker positionTracker) throws IOException {
		positionTracker.add(128L);
		this.value = dataInput.readDouble();
	}

	public byte getType() {
		return 6;
	}

	public String toString() {
		return this.value + "d";
	}

	public DoubleTag copy() {
		return new DoubleTag(this.value);
	}

	public boolean equals(Object object) {
		return super.equals(object) && this.value == ((DoubleTag)object).value;
	}

	public int hashCode() {
		long l = Double.doubleToLongBits(this.value);
		return super.hashCode() ^ (int)(l ^ l >>> 32);
	}

	public long getLong() {
		return (long)Math.floor(this.value);
	}

	public int getInt() {
		return floor(this.value);
	}

	public short getShort() {
		return (short)( floor(this.value) & '\uffff' );
	}

	public byte getByte() {
		return (byte)( floor(this.value) & 255 );
	}

	public double getDouble() {
		return this.value;
	}

	public float getFloat() {
		return (float)this.value;
	}

	private static int floor(double value) {
		int intValue = (int) value;
		return value < (double) intValue ? intValue - 1 : intValue;
	}
}
