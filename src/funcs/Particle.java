package funcs;

import primitives.Complex;

public class Particle implements Function<Double> {

    private final double A;
    private final double k;
    private final double w;
    Complex z;
    private double t;

    public Particle( double a, double k, double w ) {
        A = a;
        this.k = k;
        this.w = w;
    }

    public Function<Double> getImaginaryPart() {
        return new Function<Double>() {
            @Override
            public Double f( Double x ) {
                return z.b;
            }

            @Override
            public void update( double t ) {

            }
        };
    }

    public Function<Complex> getComplex() {
        return new Function<Complex>() {
            double t1;

            @Override
            public Complex f( Complex x ) {
                return Complex.exp( x.timesRe( k ).sum( new Complex( w * t1, 0 ) ) ).timesRe( A );
            }

            @Override
            public void update( double t ) {
                t1 = t;
            }
        };
    }

    @Override
    public Double f( Double x ) {
        z = Complex.exp( k * x - w * t ).timesRe( A );
        return z.a;
    }

    @Override
    public void update( double time ) {
        this.t = time;
    }
}
