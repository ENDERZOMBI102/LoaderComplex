package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class LongTag extends AbstractNumberTag {
   private long value;

   LongTag() {}

   public LongTag(long value) {
      this.value = value;
   }

   void write(DataOutput output) throws IOException {
      output.writeLong(this.value);
   }

   void read(DataInput dataInput, int i, PositionTracker positionTracker) throws IOException {
      positionTracker.add(128L);
      this.value = dataInput.readLong();
   }

   public byte getType() {
      return 4;
   }

   public String toString() {
      return this.value + "L";
   }

   public LongTag copy() {
      return new LongTag(this.value);
   }

   public boolean equals(Object object) {
      return super.equals(object) && this.value == ( (LongTag) object ).value;
   }

   public int hashCode() {
      return super.hashCode() ^ (int) ( this.value ^ this.value >>> 32 );
   }

   public long getLong() {
      return this.value;
   }

   public int getInt() {
      return (int) this.value;
   }

   public short getShort() {
      return (short) ( (int) ( this.value & 65535L ) );
   }

   public byte getByte() {
      return (byte) ( (int) ( this.value & 255L ) );
   }

   public double getDouble() {
      return (double) this.value;
   }

   public float getFloat() {
      return (float) this.value;
   }
}
