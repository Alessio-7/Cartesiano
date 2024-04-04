package main;

public class Sin extends Function<Double> {

    double freq;
    double phase;
    double ampl;
    double twopi = 2d * Math.PI;

    public Sin( double phase, double freq, double ampl ) {
        this.freq = freq;
        this.phase = phase;
        this.ampl = ampl;
    }

    public Sin( double freq, double ampl ) {
        this( 0, freq, ampl );
    }

    public Double f( Double x ) {
        return ampl * Math.sin( (twopi * freq * x) + phase );
    }

    @Override
    void update( double t ) {
        phase = t;
    }
}
