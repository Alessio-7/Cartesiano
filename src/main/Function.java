package main;

public abstract class Function<T> {

    abstract T f( T x );

    abstract void update( double t );
}
