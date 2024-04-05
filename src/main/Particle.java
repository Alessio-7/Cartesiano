package main;

public class Particle extends Function<Double> {

    double A;
    double k;
    double w;
    double t;

    Complex z;

    public Particle( double a, double k, double w ) {
        A = a;
        this.k = k;
        this.w = w;
    }

    public Function<Double> getImaginaryPart() {
        return new Function<Double>() {
            @Override
            Double f( Double x ) {
                return z.b;
            }

            @Override
            void update( double t ) {

            }
        };
    }

    public Function<Complex> getComplex() {
        return new Function<Complex>() {
            double t1;

            @Override
            Complex f( Complex x ) {
                return Complex.exp( x.timesRe( k ).sum( new Complex( w * t1, 0 ) ) ).timesRe( A );
            }

            @Override
            void update( double t ) {
                t1 = t;
            }
        };
    }

    @Override
    Double f( Double x ) {
        z = Complex.exp( k * x - w * t ).timesRe( A );
        return z.a;
    }

    @Override
    void update( double t ) {
        this.t = t;
    }
}
