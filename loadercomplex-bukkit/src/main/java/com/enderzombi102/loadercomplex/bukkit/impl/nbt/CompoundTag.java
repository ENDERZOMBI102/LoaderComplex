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

		for ( String key : this.tagMap.keySet() ) {
			Tag tag = this.tagMap.get(key);
			write(key, tag, output);
		}

		output.writeByte(0);
	}

	void method_32150(DataInput dataInput, int i, PositionTracker positionTracker) throws IOException {
		positionTracker.add(384L);
		if (i > 512) {
			throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
		} else {
			this.tagMap.clear();

			byte b;
			while((b = readByte(dataInput, positionTracker)) != 0) {
				String string = readString(dataInput, positionTracker);
				positionTracker.add((long)(224 + 16 * string.length()));
				Tag tag = method_32048(b, string, dataInput, i + 1, positionTracker);
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
		this.tagMap.put(key, new ByteTag(value));
	}

	public void putShort(String key, short value) {
		this.tagMap.put(key, new ShortTag(value));
	}

	public void putInt(String key, int value) {
		this.tagMap.put(key, new IntTag(value));
	}

	public void putLong(String key, long value) {
		this.tagMap.put(key, new LongTag(value));
	}

	public void putUuid(String key, UUID uuid) {
		this.putLong(key + "Most", uuid.getMostSignificantBits());
		this.putLong(key + "Least", uuid.getLeastSignificantBits());
	}

	@Nullable
	public UUID getUuid(String key) {
		return new UUID(this.getLong(key + "Most"), this.getLong(key + "Least"));
	}

	public boolean containsUuid(String key) {
		return this.contains(key + "Most", 99) && this.contains(key + "Least", 99);
	}

	public void putFloat(String key, float value) {
		this.tagMap.put(key, new FloatTag(value));
	}

	public void putDouble(String key, double value) {
		this.tagMap.put(key, new DoubleTag(value));
	}

	public void putString(String key, String value) {
		this.tagMap.put(key, new StringTag(value));
	}

	public void putByteArray(String key, byte[] value) {
		this.tagMap.put(key, new ByteArrayTag(value));
	}

	public void putIntArray(String key, int[] value) {
		this.tagMap.put(key, new IntArrayTag(value));
	}

	public void putBoolean(String string, boolean bl) {
		this.putByte(string, (byte)(bl ? 1 : 0));
	}

	public Tag get(String key) {
		return (Tag)this.tagMap.get(key);
	}

	public byte getType(String key) {
		Tag tag = (Tag)this.tagMap.get(key);
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
			if (this.contains(key, 99)) {
				return ((AbstractNumberTag)this.tagMap.get(key)).getByte();
			}
		} catch (ClassCastException var3) {
		}

		return 0;
	}

	public short getShort(String key) {
		try {
			if (this.contains(key, 99)) {
				return ((AbstractNumberTag)this.tagMap.get(key)).getShort();
			}
		} catch (ClassCastException var3) {
		}

		return 0;
	}

	public int getInt(String key) {
		try {
			if (this.contains(key, 99)) {
				return ((AbstractNumberTag)this.tagMap.get(key)).getInt();
			}
		} catch (ClassCastException var3) {
		}

		return 0;
	}

	public long getLong(String key) {
		try {
			if (this.contains(key, 99)) {
				return ((AbstractNumberTag)this.tagMap.get(key)).getLong();
			}
		} catch (ClassCastException var3) {
		}

		return 0L;
	}

	public float getFloat(String key) {
		try {
			if (this.contains(key, 99)) {
				return ((AbstractNumberTag)this.tagMap.get(key)).getFloat();
			}
		} catch (ClassCastException var3) {
		}

		return 0.0F;
	}

	public double getDouble(String key) {
		try {
			if (this.contains(key, 99)) {
				return ((AbstractNumberTag)this.tagMap.get(key)).getDouble();
			}
		} catch (ClassCastException var3) {
		}

		return 0.0D;
	}

	public String getString(String key) {
		try {
			if (this.contains(key, 8)) {
				return ((Tag)this.tagMap.get(key)).asString();
			}
		} catch (ClassCastException var3) {
		}

		return "";
	}

	public byte[] getByteArray(String key) {
		try {
			if (this.contains(key, 7)) {
				return ((ByteArrayTag)this.tagMap.get(key)).getByteArray();
			}
		} catch (ClassCastException e) {
			LOGGER.error(e);
		}

		return new byte[0];
	}

	public int[] getIntArray(String key) {
		try {
			if (this.contains(key, 11)) {
				return ((IntArrayTag)this.tagMap.get(key)).getIntArray();
			}
		} catch (ClassCastException e) {
			LOGGER.error(e);
		}

		return new int[0];
	}

	public CompoundTag getCompound(String key) {
		try {
			if (this.contains(key, 10)) {
				return (CompoundTag)this.tagMap.get(key);
			}
		} catch (ClassCastException e) {
			LOGGER.error(e);
		}

		return new CompoundTag();
	}

	public ListTag getList(String key, int type) {
		try {
			if (this.getType(key) == 9) {
				ListTag listTag = (ListTag)this.tagMap.get(key);
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

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("{");
		Collection<String> collection = this.tagMap.keySet();
		if (LOGGER.isDebugEnabled()) {
			List<String> list = Lists.newArrayList(this.tagMap.keySet());
			Collections.sort(list);
			collection = list;
		}

		String string;
		for(Iterator var5 = ((Collection)collection).iterator(); var5.hasNext(); stringBuilder.append(escapeTagKey(string)).append(':').append(this.tagMap.get(string))) {
			string = (String)var5.next();
			if (stringBuilder.length() != 1) {
				stringBuilder.append(',');
			}
		}

		return stringBuilder.append('}').toString();
	}

	public boolean isEmpty() {
		return this.tagMap.isEmpty();
	}

	public CompoundTag copy() {
		CompoundTag compoundTag = new CompoundTag();
		Iterator var2 = this.tagMap.keySet().iterator();

		while(var2.hasNext()) {
			String string = (String)var2.next();
			compoundTag.put(string, ((Tag)this.tagMap.get(string)).copy());
		}

		return compoundTag;
	}

	public boolean equals(Object object) {
		return super.equals(object) && Objects.equals(this.tagMap.entrySet(), ((CompoundTag)object).tagMap.entrySet());
	}

	public int hashCode() {
		return super.hashCode() ^ this.tagMap.hashCode();
	}

	private static void write(String key, Tag tag, DataOutput output) throws IOException {
		output.writeByte(tag.getType());
		if (tag.getType() != 0) {
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

	static Tag method_32048(byte b, String string, DataInput dataInput, int i, PositionTracker positionTracker) throws IOException {
		Tag tag = Tag.method_32149(b);

		try {
			tag.method_32150(dataInput, i, positionTracker);
			return tag;
		} catch (IOException var9) {
			CrashReport crashReport = CrashReport.create(var9, "Loading NBT data");
			CrashReportSection crashReportSection = crashReport.addElement("NBT Tag");
			crashReportSection.add("Tag name", (Object)string);
			crashReportSection.add("Tag type", (Object)b);
			throw new CrashException(crashReport);
		}
	}

	public void copyFrom(CompoundTag source) {
		Iterator var2 = source.tagMap.keySet().iterator();

		while(var2.hasNext()) {
			String string = (String)var2.next();
			Tag tag = (Tag)source.tagMap.get(string);
			if (tag.getType() == 10) {
				if (this.contains(string, 10)) {
					CompoundTag compoundTag = this.getCompound(string);
					compoundTag.copyFrom((CompoundTag)tag);
				} else {
					this.put(string, tag.copy());
				}
			} else {
				this.put(string, tag.copy());
			}
		}

	}

	protected static String escapeTagKey(String key) {
		return PATTERN.matcher(key).matches() ? key : StringTag.method_32146(key);
	}
}
