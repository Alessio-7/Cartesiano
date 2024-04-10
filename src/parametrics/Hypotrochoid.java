package parametrics;

import main.Utils;
import primitives.Point;

public class Hypotrochoid implements Parametric {

    private final double alpha, beta, R, r, d;

    public Hypotrochoid( double R, double r, double d ) {
        this( 0, 0, R, r, d );
    }

    public Hypotrochoid( double alpha, double beta, double R, double r, double d ) {
        this.alpha = alpha;
        this.beta = beta;
        this.R = R;
        this.r = r;
        this.d = d;
    }

    @Override
    public double getX( double t ) {
        return alpha + ((R - r) * Math.cos( t )) + (d * Math.cos( ((R - r) / r) * t ));
    }

    @Override
    public double getY( double t ) {
        return beta + ((R - r) * Math.sin( t )) - (d * Math.sin( ((R - r) / r) * t ));
    }

    @Override
    public double getStart() {
        return 0;
    }

    @Override
    public double getEnd() {
        return Math.PI * 2d * (Utils.lcm( (int) r, (int) R ) / R);
    }

    @Override
    public Point getCenter() {
        return new Point( alpha, beta );
    }

    @Override
    public void update( double time ) {

    }
}
