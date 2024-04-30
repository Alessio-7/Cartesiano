package study;

import funcs.Function;

public class RootsFinder {

    Function<Double> f;

    public RootsFinder( Function<Double> f ) {
        this.f = f;
    }

    public double steffen() {
        return steffen( 0 );
    }

    public double steffen( double startX ) {
        return steffen( startX, 2 );
    }

    public double steffen( double startX, int iterations ) {
        Function<Double> g = x -> (f.f( x + f.f( x ) ) / f.f( x )) - 1;
        double xi = startX;
        for( int i = 0; i < iterations; i++ )
            xi -= f.f( xi ) / g.f( xi );
        return xi;
    }

    public double newton() {
        return newton( 0 );
    }

    public double newton( double startX ) {
        return newton( startX, 2 );
    }

    public double newton( double startX, int iterations ) {
        Function<Double> derivate = new Study( f ).derivate();
        double xi = startX;
        for( int i = 0; i < iterations; i++ )
            xi -= f.f( xi ) / derivate.f( xi );

        return xi;
    }

    public void updateF() {
        updateF( 0 );
    }

    public void updateF( double time ) {
        f.update( time );
    }

}
