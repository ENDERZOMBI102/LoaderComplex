package com.enderzombi102.loadercomplex.api.event;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;


// TODO: use codegen to speed up listener invoking
public abstract class EventDispatcher<T extends Event> {
	protected CopyOnWriteArrayList<Listener<T>> listeners = new CopyOnWriteArrayList<>();

	private EventDispatcher() { }

	/**
	 * Registers an event listener to this event dispatcher.
	 * @param listener to register.
	 */
	public void register( @NotNull Listener<T> listener ) {
		Objects.requireNonNull( listener, "Cannot register a null listener." );

		this.listeners.add( listener );
	}

	/**
	 * Unregisters a previously registered listener.
	 * @param listener to unregister.
	 */
	public void unregister( @NotNull Listener<T> listener ) {
		Objects.requireNonNull( listener, "Cannot unregister a null listener." );

		this.listeners.remove( listener );
	}

	/**
	 * Invoke the listeners for this event.
	 * @param evt to give to listeners.
	 */
	public abstract void invoke( @NotNull T evt );

	/**
	 * Creates an event dispatcher for the given event type.
	 * @return the dispatcher.
	 * @param <T> type of the event the created dispatcher will handle.
	 */
	public static <T extends Event> EventDispatcher<T> create( @NotNull Class<T> ignored ) {
		return new EventDispatcher<T>() {
			@Override
			public void invoke( @NotNull T evt ) {
				for ( Listener<T> obj : this.listeners ) {
					obj.onEvent( evt );
				}
			}
		};
	}
	/**
	 * Creates a cancellable event dispatcher for the given event type.
	 * @return the dispatcher.
	 * @param <T> type of the event the created dispatcher will handle.
	 */
	public static <T extends CancellableEvent> EventDispatcher<T> createCancellable( @NotNull Class<T> ignored ) {
		return new EventDispatcher<T>() {
			@Override
			public void invoke( @NotNull T evt ) {
				for ( Listener<T> obj : this.listeners ) {
					obj.onEvent( evt );
					if ( evt.isCancelled() ) {
						return;
					}
				}
			}
		};
	}

	public interface Listener<T> {
		void onEvent( T evt );
	}
}
