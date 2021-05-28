package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class CompoundTag extends Tag {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Pattern PATTERN = Pattern.compile("[A-Za-z0-9._+-]+");
	private final Map<String, Tag> tagMap = Maps.newHashMap();

	void write(DataOutput output) throws IOException {

		for ( Map.Entry<String, Tag> entry : this.tagMap.entrySet() )
			write(entry.getKey(), entry.getValue(), output);

		output.writeByte(0);
	}

	void read(DataInput dataInput, int i, PositionTracker positionTracker) throws IOException {
		positionTracker.add(384L);
		if (i > 512) {
			throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
		} else {
			this.tagMap.clear();

			byte b;
			while( ( b = readByte(dataInput, positionTracker) ) != 0 ) {
				String string = readString(dataInput, positionTracker);
				positionTracker.add( 224 + 16L * string.length() );
				Tag tag = readTagFrom( b, string, dataInput, i + 1, positionTracker );
				if (this.tagMap.put(string, tag) != null) {
					positionTracker.add(288L);
				}
			}

		}
	}

	public Set<String> getKeys() {
		return this.tagMap.keySet();
	}

	public byte getType() {
		return 10;
	}

	public int getSize() {
		return this.tagMap.size();
	}

	public void put(String key, Tag tag) {
		this.tagMap.put(key, tag);
	}

	public void putByte(String key, byte value) {
		this.tagMap.put( key, new ByteTag(value) );
	}

	public void putShort(String key, short value) {
		this.tagMap.put( key, new ShortTag(value) );
	}

	public void putInt(String key, int value) {
		this.tagMap.put( key, new IntTag(value) );
	}

	public void putLong(String key, long value) {
		this.tagMap.put( key, new LongTag(value) );
	}

	public void putUuid(String key, UUID uuid) {
		this.putLong( key + "Most", uuid.getMostSignificantBits() );
		this.putLong( key + "Least", uuid.getLeastSignificantBits() );
	}

	@Nullable
	public UUID getUuid(String key) {
		return new UUID( this.getLong(key + "Most"), this.getLong(key + "Least") );
	}

	public boolean containsUuid(String key) {
		return this.contains(key + "Most", 99) && this.contains(key + "Least", 99);
	}

	public void putFloat(String key, float value) {
		this.tagMap.put( key, new FloatTag(value) );
	}

	public void putDouble(String key, double value) {
		this.tagMap.put( key, new DoubleTag(value) );
	}

	public void putString(String key, String value) {
		this.tagMap.put( key, new StringTag(value) );
	}

	public void putByteArray(String key, byte[] value) {
		this.tagMap.put( key, new ByteArrayTag(value) );
	}

	public void putIntArray(String key, int[] value) {
		this.tagMap.put( key, new IntArrayTag(value) );
	}

	public void putBoolean(String string, boolean bl) {
		this.putByte( string, (byte) (bl ? 1 : 0) );
	}

	public Tag get(String key) {
		return this.tagMap.get(key);
	}

	public byte getType(String key) {
		Tag tag = this.tagMap.get(key);
		return tag == null ? 0 : tag.getType();
	}

	public boolean contains(String key) {
		return this.tagMap.containsKey(key);
	}

	public boolean contains(String key, int type) {
		int i = this.getType(key);
		if (i == type) {
			return true;
		} else if (type != 99) {
			return false;
		} else {
			return i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6;
		}
	}

	public byte getByte(String key) {
		try {
			if ( this.contains(key, 99) ) {
				return ( (AbstractNumberTag)this.tagMap.get(key) ).getByte();
			}
		} catch (ClassCastException var3) {
		}

		return 0;
	}

	public short getShort(String key) {
		try {
			if ( this.contains(key, 99) ) {
				return ( (AbstractNumberTag) this.tagMap.get(key) ).getShort();
			}
		} catch (ClassCastException var3) {
		}

		return 0;
	}

	public int getInt(String key) {
		try {
			if ( this.contains(key, 99) ) {
				return ( (AbstractNumberTag) this.tagMap.get(key) ).getInt();
			}
		} catch (ClassCastException var3) {
		}

		return 0;
	}

	public long getLong(String key) {
		try {
			if ( this.contains(key, 99) ) {
				return ( (AbstractNumberTag) this.tagMap.get(key) ).getLong();
			}
		} catch (ClassCastException var3) {
		}

		return 0L;
	}

	public float getFloat(String key) {
		try {
			if ( this.contains(key, 99) ) {
				return ( (AbstractNumberTag) this.tagMap.get(key) ).getFloat();
			}
		} catch (ClassCastException var3) {
		}

		return 0.0F;
	}

	public double getDouble(String key) {
		try {
			if ( this.contains(key, 99) ) {
				return ( (AbstractNumberTag) this.tagMap.get(key) ).getDouble();
			}
		} catch (ClassCastException var3) {
		}

		return 0.0D;
	}

	public String getString(String key) {
		try {
			if ( this.contains(key, 8) ) {
				return this.tagMap.get(key).asString();
			}
		} catch (ClassCastException var3) {
		}

		return "";
	}

	public byte[] getByteArray(String key) {
		try {
			if ( this.contains(key, 7) ) {
				return ( (ByteArrayTag) this.tagMap.get(key) ).getByteArray();
			}
		} catch (ClassCastException e) {
			LOGGER.error(e);
		}

		return new byte[0];
	}

	public int[] getIntArray(String key) {
		try {
			if ( this.contains(key, 11) ) {
				return ( (IntArrayTag) this.tagMap.get(key) ).getIntArray();
			}
		} catch (ClassCastException e) {
			LOGGER.error(e);
		}

		return new int[0];
	}

	public CompoundTag getCompound(String key) {
		try {
			if ( this.contains(key, 10) ) {
				return (CompoundTag) this.tagMap.get(key);
			}
		} catch (ClassCastException e) {
			LOGGER.error(e);
		}

		return new CompoundTag();
	}

	public ListTag getList(String key, int type) {
		try {
			if ( this.getType(key) == 9 ) {
				ListTag listTag = (ListTag) this.tagMap.get(key);
				if (!listTag.isEmpty() && listTag.getElementType() != type) {
					return new ListTag();
				}

				return listTag;
			}
		} catch (ClassCastException e) {
			LOGGER.error(e);
		}

		return new ListTag();
	}

	public boolean getBoolean(String key) {
		return this.getByte(key) != 0;
	}

	public void remove(String key) {
		this.tagMap.remove(key);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("{");
		Collection<String> collection = this.tagMap.keySet();
		if ( LOGGER.isDebugEnabled() ) {
			List<String> list = Lists.newArrayList( this.tagMap.keySet() );
			Collections.sort(list);
			collection = list;
		}

		for( String key : collection ) {
			if (stringBuilder.length() != 1) {
				stringBuilder.append(',');
			}
			stringBuilder.append( escapeTagKey(key) ).append(':').append( this.tagMap.get(key) );
		}

		return stringBuilder.append('}').toString();
	}

	public boolean isEmpty() {
		return this.tagMap.isEmpty();
	}

	public CompoundTag copy() {
		CompoundTag compoundTag = new CompoundTag();

		this.tagMap.forEach( (key, value) -> {
			compoundTag.put( key, value.copy() );
		} );

		return compoundTag;
	}

	public boolean equals(Object object) {
		return super.equals(object) && Objects.equals(
				this.tagMap.entrySet(),
				( (CompoundTag) object ).tagMap.entrySet()
		);
	}

	public int hashCode() {
		return super.hashCode() ^ this.tagMap.hashCode();
	}

	private static void write(String key, Tag tag, DataOutput output) throws IOException {
		output.writeByte( tag.getType() );
		if ( tag.getType() != 0 ) {
			output.writeUTF(key);
			tag.write(output);
		}
	}

	private static byte readByte(DataInput input, PositionTracker tracker) throws IOException {
		return input.readByte();
	}

	private static String readString(DataInput input, PositionTracker tracker) throws IOException {
		return input.readUTF();
	}

	static Tag readTagFrom(byte b, String string, DataInput dataInput, int i, PositionTracker positionTracker) throws IOException {
		Tag tag = Tag.tagFromByte(b);

		try {
			tag.read(dataInput, i, positionTracker);
			return tag;
		} catch (IOException var9) {
			return tag;
		}
	}

	public void copyFrom(CompoundTag source) {

		source.tagMap.forEach( (key, value) -> {
			if ( value.getType() == 10 ) {
				if ( this.contains(key, 10) ) {
					CompoundTag compoundTag = this.getCompound(key);
					compoundTag.copyFrom( (CompoundTag) value );
				} else {
					this.put( key, value.copy() );
				}
			} else {
				this.put( key, value.copy() );
			}
		} );

	}

	protected static String escapeTagKey(String key) {
		return PATTERN.matcher(key).matches() ? key : StringTag.escapeString(key);
	}
}
