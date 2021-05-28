package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FloatTag extends AbstractNumberTag {
	private float value;

	FloatTag() {}

	public FloatTag(float value) {
		this.value = value;
	}

	void write(DataOutput output) throws IOException {
		output.writeFloat(this.value);
	}

	void read(DataInput dataInput, int i, PositionTracker positionTracker) throws IOException {
		positionTracker.add(96L);
		this.value = dataInput.readFloat();
	}

	public byte getType() {
		return 5;
	}

	public String toString() {
		return this.value + "f";
	}

	public FloatTag copy() {
		return new FloatTag(this.value);
	}

	public boolean equals(Object object) {
		return super.equals(object) && this.value == ( (FloatTag) object ).value;
	}

	public int hashCode() {
		return super.hashCode() ^ Float.floatToIntBits(this.value);
	}

	public long getLong() {
		return (long) this.value;
	}

	public int getInt() {
		return floor(this.value);
	}

	public short getShort() {
		return (short) (floor(this.value) & '\uffff');
	}

	public byte getByte() {
		return (byte) (floor(this.value) & 255);
	}

	public double getDouble() {
		return this.value;
	}

	public float getFloat() {
		return this.value;
	}

	private static int floor(double value) {
		int intValue = (int) value;
		return value < (double) intValue ? intValue - 1 : intValue;
	}
}
