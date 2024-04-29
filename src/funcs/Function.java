package funcs;

public interface Function<T> {
    T f( T x );

    default void update( double time ) {
    }

    default boolean display() {
        return true;
    }
}
