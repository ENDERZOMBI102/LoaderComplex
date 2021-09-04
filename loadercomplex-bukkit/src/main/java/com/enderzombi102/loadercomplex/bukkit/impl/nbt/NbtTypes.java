package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

public class NbtTypes {
	private static final NbtType<?>[] VALUES;

	/**
	 * Gets the associated {@linkplain NbtType NBT type} for a given {@code id}.
	 * <p>
	 * This method does not support id aliases.
	 * 
	 * @return the NBT type, or {@linkplain NbtType#createInvalid an invalid type} if there is no type with the given {@code id}
	 */
	public static NbtType<?> byId(int id) {
		return id >= 0 && id < VALUES.length ? VALUES[id] : NbtType.createInvalid(id);
	}

	static {
		VALUES = new NbtType[]{NbtNull.TYPE, NbtByte.TYPE, NbtShort.TYPE, NbtInt.TYPE, NbtLong.TYPE, NbtFloat.TYPE, NbtDouble.TYPE, NbtByteArray.TYPE, NbtString.TYPE, NbtList.TYPE, NbtCompound.TYPE, NbtIntArray.TYPE, NbtLongArray.TYPE};
	}
}
