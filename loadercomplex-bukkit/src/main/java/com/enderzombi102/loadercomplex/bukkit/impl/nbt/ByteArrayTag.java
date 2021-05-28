package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ByteArrayTag extends Tag {
	private byte[] value;

	ByteArrayTag() {
	}

	public ByteArrayTag(byte[] value) {
		this.value = value;
	}

	public ByteArrayTag(List<Byte> value) {
		this( toArray(value) );
	}

	private static byte[] toArray(List<Byte> list) {
		byte[] bs = new byte[ list.size() ];

		for(int i = 0; i < list.size(); ++i) {
			Byte lv = list.get(i);
			bs[i] = lv == null ? 0 : lv;
		}

		return bs;
	}

	void write(DataOutput output) throws IOException {
		output.writeInt(this.value.length);
		output.write(this.value);
	}

	void read(DataInput dataInput, int i, PositionTracker positionTracker) throws IOException {
		positionTracker.add(192L);
		int j = dataInput.readInt();
		positionTracker.add( 8L * j );
		this.value = new byte[j];
		dataInput.readFully(this.value);
	}

	public byte getType() {
		return 7;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("[B;");

		for(int i = 0; i < this.value.length; ++i) {
			if (i != 0) {
				stringBuilder.append(',');
			}

			stringBuilder.append( this.value[i] ).append('B');
		}

		return stringBuilder.append(']').toString();
	}

	public Tag copy() {
		byte[] bs = new byte[this.value.length];
		System.arraycopy(this.value, 0, bs, 0, this.value.length);
		return new ByteArrayTag(bs);
	}

	public boolean equals(Object object) {
		return super.equals(object) && Arrays.equals(this.value, ( (ByteArrayTag) object ).value);
	}

	public int hashCode() {
		return super.hashCode() ^ Arrays.hashCode(this.value);
	}

	public byte[] getByteArray() {
		return this.value;
	}
}
