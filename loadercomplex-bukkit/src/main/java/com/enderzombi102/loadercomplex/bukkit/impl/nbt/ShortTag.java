package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ShortTag extends AbstractNumberTag {
   private short value;

   public ShortTag() {}

   public ShortTag(short value) {
      this.value = value;
   }

   void write(DataOutput output) throws IOException {
      output.writeShort(this.value);
   }

   void read(DataInput dataInput, int i, PositionTracker positionTracker) throws IOException {
      positionTracker.add(80L);
      this.value = dataInput.readShort();
   }

   public byte getType() {
      return 2;
   }

   public String toString() {
      return this.value + "s";
   }

   public ShortTag copy() {
      return new ShortTag(this.value);
   }

   public boolean equals(Object object) {
      return super.equals(object) && this.value == ( (ShortTag) object ).value;
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
      return this.value;
   }

   public byte getByte() {
      return (byte) ( this.value & 255 );
   }

   public double getDouble() {
      return this.value;
   }

   public float getFloat() {
      return this.value;
   }
}
