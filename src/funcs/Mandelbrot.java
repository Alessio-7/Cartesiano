package funcs;

import primitives.Complex;

public class Mandelbrot implements Function<Complex> {

    private final double iterations;
    private final double xTraslation;

    public Mandelbrot( double iterations, double xTraslation ) {
        this.iterations = iterations;
        this.xTraslation = xTraslation;
    }

    @Override
    public Complex f( Complex x ) {

        Complex c = x.sum( new Complex( xTraslation, 0 ) );

        for( int i = 0; i < iterations; i++ ) {
            x = x.times( x ).sum( c );
        }

        return x;
    }

    @Override
    public void update( double t ) {

    }
}
