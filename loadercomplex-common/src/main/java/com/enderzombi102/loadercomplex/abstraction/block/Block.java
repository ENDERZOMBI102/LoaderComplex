package com.enderzombi102.loadercomplex.abstraction.block;

public interface Block {

	default void OnBreak(Object player) {}
	default void OnWalkOn(Object entity) {}
	default void OnBlockInteracted(Object player) {}

}
