package funcs;

public interface Function<T> {

    T f( T x );

    void update( double time );
}
