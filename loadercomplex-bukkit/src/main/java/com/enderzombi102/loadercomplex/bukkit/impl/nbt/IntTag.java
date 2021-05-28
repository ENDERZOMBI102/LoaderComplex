package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class IntTag extends AbstractNumberTag {
   private int value;

   IntTag() {}

   public IntTag(int value) {
      this.value = value;
   }

   void write(DataOutput output) throws IOException {
      output.writeInt(this.value);
   }

   void read(DataInput dataInput, int i, PositionTracker positionTracker) throws IOException {
      positionTracker.add(96L);
      this.value = dataInput.readInt();
   }

   public byte getType() {
      return 3;
   }

   public String toString() {
      return String.valueOf(this.value);
   }

   public IntTag copy() {
      return new IntTag(this.value);
   }

   public boolean equals(Object object) {
      return super.equals(object) && this.value == ( (IntTag) object ).value;
   }

   public int hashCode() {
      return super.hashCode() ^ this.value;
   }

   public long getLong() {
      return this.value;
   }

   public int getInt() {
      return this.value;
   }

   public short getShort() {
      return (short) (this.value & '\uffff');
   }

   public byte getByte() {
      return (byte) (this.value & 255);
   }

   public double getDouble() {
      return this.value;
   }

   public float getFloat() {
      return (float) this.value;
   }
}
