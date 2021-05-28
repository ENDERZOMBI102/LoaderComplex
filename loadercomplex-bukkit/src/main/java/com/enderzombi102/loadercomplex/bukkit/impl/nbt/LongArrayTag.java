package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LongArrayTag extends Tag {
	private long[] value;

	LongArrayTag() {}

	public LongArrayTag(long[] value) {
		this.value = value;
	}

	public LongArrayTag(List<Long> value) {
		this( toArray(value) );
	}

	private static long[] toArray(List<Long> list) {
		long[] ls = new long[list.size()];

		for(int i = 0; i < list.size(); ++i) {
			Long lv = list.get(i);
			ls[i] = lv == null ? 0L : lv;
		}

		return ls;
	}

	void write(DataOutput output) throws IOException {
		output.writeInt(this.value.length);

		for( long l : this.value ) {
			output.writeLong(l);
		}

	}

	void read(DataInput dataInput, int i, PositionTracker positionTracker) throws IOException {
		positionTracker.add(192L);
		int j = dataInput.readInt();
		positionTracker.add(64L * j);
		this.value = new long[j];

		for(int k = 0; k < j; ++k) {
			this.value[k] = dataInput.readLong();
		}

	}

	public byte getType() {
		return 12;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("[L;");

		for(int i = 0; i < this.value.length; ++i) {
			if (i != 0) {
				stringBuilder.append(',');
			}

			stringBuilder.append( this.value[i] ).append('L');
		}

		return stringBuilder.append(']').toString();
	}

	public LongArrayTag copy() {
		long[] ls = new long[this.value.length];
		System.arraycopy(this.value, 0, ls, 0, this.value.length);
		return new LongArrayTag(ls);
	}

	public boolean equals(Object object) {
		return super.equals(object) && Arrays.equals( this.value, ( (LongArrayTag) object).value );
	}

	public int hashCode() {
		return super.hashCode() ^ Arrays.hashCode(this.value);
	}
}
