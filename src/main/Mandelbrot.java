package main;

public class Mandelbrot extends Function<Complex> {

    double iterations;
    double xTraslation;

    public Mandelbrot( double iterations, double xTraslation ) {
        this.iterations = iterations;
        this.xTraslation = xTraslation;
    }

    @Override
    Complex f( Complex x ) {

        Complex c = x.sum( new Complex( xTraslation, 0 ) );

        for( int i = 0; i < iterations; i++ ) {
            x = x.times( x ).sum( c );
        }

        return x;
    }

    @Override
    void update( double t ) {

    }
}
