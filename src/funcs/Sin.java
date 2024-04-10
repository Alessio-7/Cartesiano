package funcs;

public class Sin implements Function<Double> {

    private final double twopi = 2d * Math.PI;
    private final double freq;
    private double phase;
    private final double ampl;

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
    public void update( double t ) {
        phase = t;
    }
}
