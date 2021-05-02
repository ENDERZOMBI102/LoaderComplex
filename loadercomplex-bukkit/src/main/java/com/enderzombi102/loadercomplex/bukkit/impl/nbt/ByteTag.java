package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ByteTag extends AbstractNumberTag {
	private byte value;

	ByteTag() {
	}

	public ByteTag(byte value) {
		this.value = value;
	}

	void write(DataOutput output) throws IOException {
		output.writeByte(this.value);
	}

	void method_32150(DataInput dataInput, int i, PositionTracker positionTracker) throws IOException {
		positionTracker.add(72L);
		this.value = dataInput.readByte();
	}

	public byte getType() {
		return 1;
	}

	public String toString() {
		return this.value + "b";
	}

	public ByteTag copy() {
		return new ByteTag(this.value);
	}

	public boolean equals(Object object) {
		return super.equals(object) && this.value == ((ByteTag)object).value;
	}

	public int hashCode() {
		return super.hashCode() ^ this.value;
	}

	public long getLong() {
		return (long)this.value;
	}

	public int getInt() {
		return this.value;
	}

	public short getShort() {
		return (short)this.value;
	}

	public byte getByte() {
		return this.value;
	}

	public double getDouble() {
		return (double)this.value;
	}

	public float getFloat() {
		return (float)this.value;
	}
}
