package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ListTag extends Tag {
	private static final Logger LOGGER = LogManager.getLogger();
	private List<Tag> buffer = Lists.newArrayList();
	private byte type = 0;

	void write(DataOutput output) throws IOException {
		if ( this.buffer.isEmpty() ) {
			this.type = 0;
		} else {
			this.type = this.buffer.get(0).getType();
		}

		output.writeByte(this.type);
		output.writeInt( this.buffer.size() );

		for ( Tag tag : this.buffer ) tag.write( output );

	}

	void read(DataInput dataInput, int i, PositionTracker positionTracker) throws IOException {
		positionTracker.add(296L);
		if (i > 512) {
			throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
		} else {
			this.type = dataInput.readByte();
			int j = dataInput.readInt();
			if ( this.type == 0 && j > 0 ) {
				throw new RuntimeException("Missing type on ListTag");
			} else {
				positionTracker.add( 32L * j );
				this.buffer = Lists.newArrayListWithCapacity(j);

				for(int k = 0; k < j; ++k) {
					Tag tag = Tag.tagFromByte(this.type);
					tag.read(dataInput, i + 1, positionTracker);
					this.buffer.add(tag);
				}

			}
		}
	}

	public byte getType() {
		return 9;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("[");

		for(int i = 0; i < this.buffer.size(); ++i) {
			if (i != 0) {
				stringBuilder.append(',');
			}

			stringBuilder.append(this.buffer.get(i));
		}

		return stringBuilder.append(']').toString();
	}

	public void add(Tag tag) {
		if ( tag.getType() == 0 ) {
			LOGGER.warn("Invalid TagEnd added to ListTag");
		} else {
			if (this.type == 0) {
				this.type = tag.getType();
			} else if ( this.type != tag.getType() ) {
				LOGGER.warn("Adding mismatching tag types to tag list");
				return;
			}

			this.buffer.add(tag);
		}
	}

	public void setIndex(int i, Tag tag) {
		if (tag.getType() == 0) {
			LOGGER.warn("Invalid TagEnd added to ListTag");
		} else if ( i >= 0 && i < this.buffer.size() ) {
			if (this.type == 0) {
				this.type = tag.getType();
			} else if ( this.type != tag.getType() ) {
				LOGGER.warn("Adding mismatching tag types to tag list");
				return;
			}

			this.buffer.set(i, tag);
		} else {
			LOGGER.warn("index out of bounds to set tag in tag list");
		}
	}

	public Tag remove(int i) {
		return this.buffer.remove(i);
	}

	public boolean isEmpty() {
		return this.buffer.isEmpty();
	}

	public CompoundTag getCompound(int index) {
		if ( index >= 0 && index < this.buffer.size() ) {
			Tag tag = this.buffer.get(index);
			if ( tag.getType() == 10 ) {
				return (CompoundTag) tag;
			}
		}

		return new CompoundTag();
	}

	public int getInt(int i) {
		if ( i >= 0 && i < this.buffer.size() ) {
			Tag tag = this.buffer.get(i);
			if (tag.getType() == 3) {
				return ( (IntTag) tag ).getInt();
			}
		}

		return 0;
	}

	public int[] getIntArray(int index) {
		if ( index >= 0 && index < this.buffer.size() ) {
			Tag tag = this.buffer.get(index);
			if (tag.getType() == 11) {
				return ( (IntArrayTag) tag ).getIntArray();
			}
		}

		return new int[0];
	}

	public double getDouble(int index) {
		if ( index >= 0 && index < this.buffer.size() ) {
			Tag tag = this.buffer.get(index);
			if (tag.getType() == 6) {
				return ( (DoubleTag) tag ).getDouble();
			}
		}

		return 0.0D;
	}

	public float getFloat(int index) {
		if ( index >= 0 && index < this.buffer.size() ) {
			Tag tag = this.buffer.get(index);
			if (tag.getType() == 5) {
				return ( (FloatTag) tag ).getFloat();
			}
		}

		return 0.0F;
	}

	public String getString(int index) {
		if ( index >= 0 && index < this.buffer.size() ) {
			Tag tag = this.buffer.get(index);
			return tag.getType() == 8 ? tag.asString() : tag.toString();
		} else {
			return "";
		}
	}

	public Tag getTag(int i) {
		return i >= 0 && i < this.buffer.size() ? this.buffer.get(i) : new EndTag();
	}

	public int size() {
		return this.buffer.size();
	}

	public ListTag copy() {
		ListTag listTag = new ListTag();
		listTag.type = this.type;

		for ( Tag tag : this.buffer) {
			Tag tag2 = tag.copy();
			listTag.buffer.add(tag2);
		}

		return listTag;
	}

	public boolean equals(Object object) {
		if (! super.equals(object) ) {
			return false;
		} else {
			ListTag listTag = (ListTag) object;
			return this.type == listTag.type && Objects.equals(this.buffer, listTag.buffer);
		}
	}

	public int hashCode() {
		return super.hashCode() ^ this.buffer.hashCode();
	}

	public int getElementType() {
		return this.type;
	}
}
