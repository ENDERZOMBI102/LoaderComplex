package com.enderzombi102.loadercomplex.api.event;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;


public abstract class EventDispatcher<T extends Event> {
	private final Object lock = new Object();
	@SuppressWarnings( "unchecked" )
	protected Listener<T>[] listeners = (Listener<T>[]) new Listener[0];

	private EventDispatcher() { }

	/**
	 * Registers an event listener to this event dispatcher.
	 * @param listener to register.
	 */
	public void register( @NotNull Listener<T> listener ) {
		Objects.requireNonNull( listener, "Cannot register a null listener." );

		synchronized ( this.lock ) {
			@SuppressWarnings( "unchecked" )
			Listener<T>[] newListeners = new Listener[this.listeners.length + 1];
			newListeners[0] = listener;
			System.arraycopy( this.listeners, 0, newListeners, 1, this.listeners.length );
			this.listeners = newListeners;
		}
	}

	/**
	 * Unregisters a previously registered listener.
	 * @param listener to unregister.
	 */
	@SuppressWarnings( "unchecked" )
	public void unregister( @NotNull Listener<T> listener ) {
		Objects.requireNonNull( listener, "Cannot unregister a null listener." );

		synchronized ( this.lock ) {
			this.listeners = Arrays
				.stream( this.listeners )
				.filter( it -> listener != it )
				.toArray(Listener[]::new);
		}
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
