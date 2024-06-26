package funcs;

public class OndaQuadra implements Function<Double> {

    Sin[] sins;
    // double twopi = 2d * Math.PI;
    double t;


    public OndaQuadra( double f ) {
        int l = 5;
        sins = new Sin[l];
        for( int i = 1; i < l + 1; i++ ) {
            double fattore = (double) (2 * i) - 1;
            sins[i - 1] = new Sin( fattore * f, 1d / fattore );
        }
    }

    @Override
    public Double f( Double x ) {
        double y = 0;

        for( Sin s : sins ) {
            y += s.f( x + t );
        }

        return y * (4d / Math.PI);
    }

    @Override
    public void update( double time ) {
        this.t = time;
    }
}
