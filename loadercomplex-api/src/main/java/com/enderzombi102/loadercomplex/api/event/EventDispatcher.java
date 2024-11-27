package com.enderzombi102.loadercomplex.api.event;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;


public abstract class EventDispatcher<T extends Event> {
	private final Object lock = new Object();
	protected Consumer<T>[] listeners;

	public EventDispatcher() {
		//noinspection unchecked
		this.listeners = (Consumer<T>[]) new Consumer[0];
	}

	/**
	 * Registers an event listener to this event dispatcher.
	 * @param listener to register.
	 */
	public void register( Consumer<T> listener ) {
		Objects.requireNonNull( listener, "Cannot register a null listener." );

		synchronized ( this.lock ) {
			//noinspection unchecked
			Consumer<T>[] newListeners = new Consumer[this.listeners.length + 1];
			newListeners[0] = listener;
			System.arraycopy( this.listeners, 0, newListeners, 1, this.listeners.length );
			this.listeners = newListeners;
		}
	}

	/**
	 * Unregisters a previously registered listener.
	 * @param listener to unregister.
	 */
	public void unregister( Consumer<T> listener ) {
		Objects.requireNonNull( listener, "Cannot unregister a null listener." );

		synchronized ( this.lock ) {
			//noinspection unchecked
			this.listeners = Arrays
				.stream( this.listeners )
				.filter( it -> listener != it )
				.toArray(Consumer[]::new);
		}
	}

	/**
	 * Invoke the listeners for this event.
	 * @param evt to give to listeners.
	 */
	public abstract void invoke( T evt );

	/**
	 * Creates an event dispatcher for the given event type.
	 * @return the dispatcher.
	 * @param <T> type of the event the created dispatcher will handle.
	 */
	public static <T extends Event> EventDispatcher<T> create( Class<T> ignored ) {
		return new EventDispatcher<T>() {
			@Override
			public void invoke( T evt ) {
				for ( Consumer<T> obj : this.listeners ) {
					obj.accept( evt );
				}
			}
		};
	}
	/**
	 * Creates a cancellable event dispatcher for the given event type.
	 * @return the dispatcher.
	 * @param <T> type of the event the created dispatcher will handle.
	 */
	public static <T extends CancellableEvent> EventDispatcher<T> createCancellable( Class<T> ignored ) {
		return new EventDispatcher<T>() {
			@Override
			public void invoke( T evt ) {
				for ( Consumer<T> obj : this.listeners ) {
					obj.accept( evt );
					if ( evt.isCancelled() ) {
						return;
					}
				}
			}
		};
	}
}

class CaseChangedEvent extends Event {

}

class Test {
	public static final EventDispatcher<CaseChangedEvent> CASE_CHANGED = EventDispatcher.create( CaseChangedEvent.class );

	public static void main( String[] argv ) {
		CASE_CHANGED.invoke( new CaseChangedEvent() );
	}
}
