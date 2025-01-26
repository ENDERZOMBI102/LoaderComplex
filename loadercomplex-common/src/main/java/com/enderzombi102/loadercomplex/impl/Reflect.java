package com.enderzombi102.loadercomplex.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sun.misc.Unsafe;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleProxies;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.util.Arrays;


/**
 * Reflection utilities, based on <a href="https://github.com/MrNavaStar/R">MrNavaStar's R library</a> and my <a href="https://github.com/ENDERZOMBI102/enderlib">own enderlib</a>.
 */
public class Reflect<T> {
	private static final Unsafe UNSAFE;
	private static final MethodHandles.Lookup IMPL_LOOKUP;

	private final @NotNull Class<T> clazz;
	private final @Nullable T object;
	private final @Nullable MethodHandle handle;

	private Reflect( @NotNull Class<T> clazz, @Nullable T object, @Nullable MethodHandle handle ) {
		this.clazz = clazz;
		this.object = object;
		this.handle = handle;
	}

	@SuppressWarnings( "unchecked" )
	public Reflect<Object> get( String fieldName, String typeName ) {
		try {
			return get( fieldName, (Class<Object>) Class.forName( typeName ) );
		} catch ( Throwable e ) {
			throw new RuntimeException( e );
		}
	}

	@SuppressWarnings( "unchecked" )
	public <Result> Reflect<Result> get( String fieldName, Class<Result> type ) {
		try {
			MethodHandle handle = this.object == null
				? IMPL_LOOKUP.findStaticGetter( this.clazz, fieldName, type )
				: IMPL_LOOKUP.findGetter( this.clazz, fieldName, type ).bindTo( this.object );
			return new Reflect<>( type, (Result) handle.invoke(), handle );
		} catch ( Throwable e ) {
			throw new RuntimeException( e );
		}
	}

	/**
	 * Set the value of a field. Can be private, final, or static
	 */
	@SuppressWarnings( "unchecked" )
	public <Value> Reflect<T> set( String fieldName, Value value ) {
		return set( fieldName, value, (Class<Value>) value.getClass() );
	}

	/**
	 * Set the value of a field. Can be private, final, or static
	 */
	public <Value> Reflect<T> set( String fieldName, Value value, Class<? super Value> type ) {
		try {
			MethodHandle handle = this.object == null
				? IMPL_LOOKUP.findStaticSetter( this.clazz, fieldName, type )
				: IMPL_LOOKUP.findSetter( this.clazz, fieldName, type ).bindTo( this.object );
			handle.invoke( value );
			return this;
		} catch ( Throwable e ) {
			throw new RuntimeException( e );
		}
	}

	/**
	 * Invoke a function which was pre-found
	 */
	@SuppressWarnings( "unchecked" )
	public T call( Object... args ) {
		try {
			assert this.handle != null;
			return (T) (args.length == 0 ? handle.invoke() : handle.invoke( args ));
		} catch ( Throwable e ) {
			throw new RuntimeException( e );
		}
	}

	/**
	 * Invoke a function with no return type
	 */
	public Reflect<T> call( String name, Object... args ) {
		call( name, void.class, args );
		return this;
	}

	/**
	 * Invoke a function with a return type
	 */
	@SuppressWarnings( "unchecked" )
	public Reflect<Object> call( String name, String typeName, Object... args ) {
		try {
			return call( name, (Class<Object>) Class.forName( typeName ), args );
		} catch ( ClassNotFoundException e ) {
			throw new RuntimeException( e );
		}
	}

	/**
	 * Invoke a function with a return type
	 */
	@SuppressWarnings( "unchecked" )
	public <Result> Reflect<Result> call( String name, Class<Result> type, Object... args ) {
		try {
			Class<?>[] classes = Arrays.stream( args ).map( Object::getClass ).toArray( Class[]::new );
			MethodHandle handle = invoker( name, type, classes );
			return new Reflect<>( type, (Result) (args.length == 0 ? handle.invoke() : handle.invoke( args )), handle );
		} catch ( Throwable e ) {
			throw new RuntimeException( e );
		}
	}

	/**
	 * Creates an invoker for the specified method
	 */
	public MethodHandle invoker( String name, String typeName, Class<?>... args ) {
		try {
			return invoker( name, Class.forName( typeName ), args );
		} catch ( Throwable e ) {
			throw new RuntimeException( e );
		}
	}

	/**
	 * Creates an invoker for the specified method
	 */
	public MethodHandle invoker( String name, Class<?> type, Class<?>... args ) {
		try {
			MethodType methodType = MethodType.methodType( type, args );
			return this.object == null
				? IMPL_LOOKUP.findStatic( this.clazz, name, methodType )
				: IMPL_LOOKUP.bind( this.object, name, methodType );
		} catch ( NoSuchMethodException | IllegalAccessException e ) {
			throw new RuntimeException( e );
		}
	}

	/**
	 * Creates an invoker under an interface alias for the specified method.
	 * @param name name of the method to reflect.
	 * @param type return type of the method to reflect.
	 * @param alias the target functional interface.
	 * @param args the types of the arguments.
	 * @return
	 * @param <Result>
	 * @implNote BROKEN
	 */
	@SuppressWarnings( "unchecked" )
	public <Result> Result invokerTyped( String name, Class<?> type, Class<?> alias, Class<?>... args ) {
		try {
			MethodType methodType = MethodType.methodType( type, args );
			MethodHandle handle = this.object == null
				? IMPL_LOOKUP.findStatic( this.clazz, name, methodType )
				: IMPL_LOOKUP.bind( this.object, name, methodType );

			return (Result) MethodHandleProxies.asInterfaceInstance( alias, handle );
		} catch ( Throwable e ) {
			throw new RuntimeException( e );
		}
	}

	/**
	 * Casts this reflected object to a new type
	 */
	@SuppressWarnings( "unchecked" )
	public Reflect<Object> castTo( String typeName ) {
		try {
			return castTo( (Class<Object>) Class.forName( typeName ) );
		} catch ( ClassNotFoundException e ) {
			throw new RuntimeException( e );
		}
	}

	/**
	 * Casts this reflected object to a new type
	 */
	public <Result> Reflect<Result> castTo( Class<Result> type ) {
		return new Reflect<>(
			type,
			type.cast( this.object ),
			this.handle == null
				? null
				: this.handle.asType( this.handle.type().changeReturnType( type ) )
		);
	}

	/**
	 * Creates a copy with the updated value.
	 */
	@SuppressWarnings( "unchecked" )
	public Reflect<T> update() {
		try {
			assert this.handle != null;
			return new Reflect<>( this.clazz, (T) this.handle.invoke(), this.handle );
		} catch ( Throwable e ) {
			throw new RuntimeException( e );
		}
	}
	/**
	 * Get the value which is being reflected upon.
	 */
	public T unwrap() {
		return this.object;
	}

	static {
		try {
			final Field unsafeField = Unsafe.class.getDeclaredField( "theUnsafe" );
			unsafeField.setAccessible( true );
			UNSAFE = (Unsafe) unsafeField.get( null );
			final Field implLookupField = MethodHandles.Lookup.class.getDeclaredField( "IMPL_LOOKUP" );
			IMPL_LOOKUP = (MethodHandles.Lookup) UNSAFE.getObject(
				UNSAFE.staticFieldBase( implLookupField ),
				UNSAFE.staticFieldOffset( implLookupField )
			);
		} catch ( NoSuchFieldException | IllegalAccessException e ) {
			throw new RuntimeException( e );
		}
	}

	/**
	 * Create an instance of {@link Reflect} that can only be used for static actions
	 */
	public static Reflect<?> that( Class<?> clazz ) {
		return new Reflect<>( clazz, null, null );
	}

	/**
	 * Create an instance of {@link Reflect}. Can be used for static or non-static actions
	 */
	@SuppressWarnings( "unchecked" )
	public static <T> Reflect<T> that( T object ) {
		return new Reflect<>( (Class<T>) object.getClass(), object, null );
	}

	/**
	 * Create an instance of {@link Reflect}. Can be used for static or non-static actions
	 */
	public static Reflect<?> that( String name ) {
		try {
			return new Reflect<>( Class.forName( name ), null, null );
		} catch ( ClassNotFoundException e ) {
			throw new RuntimeException( e );
		}
	}

	/**
	 * Loads a module and make it usable by the unnamed module
	 * @param module module's name
	 */
	public static Object loadModule( @NotNull String module ) {
		try {
			MethodType methodType = MethodType.methodType( Class.forName( "java.lang.Module" ), new Class[] { String.class } );
			MethodHandle handle = IMPL_LOOKUP.findStatic( Class.forName( "jdk.internal.module.Modules" ), "loadModule", methodType );
			return handle.invoke( module );
		} catch ( Throwable e ) {
			throw new RuntimeException( e );
		}
	}

	/**
	 * Marks a package defined by a module as usable by the unnamed module
	 * @param module module's name
	 */
	public static void openModule( @NotNull String module, @NotNull String pkg ) {
		try {
			MethodType methodType = MethodType.methodType( void.class, new Class[] { Class.forName( "java.lang.Module" ), String.class } );
			MethodHandle handle = IMPL_LOOKUP.findStatic( Class.forName( "jdk.internal.module.Modules" ), "addOpensToAllUnnamed", methodType );
			handle.invoke( loadModule( module ), pkg );
		} catch ( Throwable e ) {
			throw new RuntimeException( e );
		}
	}
}
