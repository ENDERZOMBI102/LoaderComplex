package com.enderzombi102.loadercomplex.api.event;

public abstract class CancellableEvent extends Event {
	private boolean cancelled = false;
	private boolean othersMayRun = false;

	/**
	 * Whether this event was cancelled.
	 */
	public boolean isCancelled() {
		return this.cancelled;
	}

	/**
	 * Whether other listeners are allowed to run after this event is cancelled.
	 */
	public boolean areOthersAllowedToRun() {
		return this.othersMayRun;
	}

	/**
	 * Cancels this event, forfeiting other listeners' ability to run.
	 */
	public void cancel() {
		this.cancelled = true;
	}

	/**
	 * Cancels this event.
	 * @param allowOthers whether other listeners should be allowed to run anyway.
	 */
	public void cancel( boolean allowOthers ) {
		this.cancelled = true;
		this.othersMayRun = allowOthers;
	}
}
