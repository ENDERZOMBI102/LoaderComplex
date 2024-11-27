package com.enderzombi102.loadercomplex.api.event;

public class CancelToken {
	private boolean cancelled = false;

	public boolean isCancelled() {
		return cancelled;
	}

	public void cancel() {
		this.cancelled = true;
	}
}
