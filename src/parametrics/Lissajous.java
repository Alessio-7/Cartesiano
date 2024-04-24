package parametrics;

import primitives.Point;

public class Lissajous implements Parametric {

    private final double alpha;
    private final double beta;
    private double a;
    private double b;
    private final double kx;
    private final double ky;

    public Lissajous( double kx, double ky ) {
        this( 1, 1, kx, ky );
    }

    public Lissajous( double a, double b, double kx, double ky ) {
        this( 0, 0, a, b, kx, ky );
    }

    public Lissajous( double alpha, double beta, double a, double b, double kx, double ky ) {
        this.alpha = alpha;
        this.beta = beta;
        this.a = a;
        this.b = b;
        this.kx = kx;
        this.ky = ky;
    }

    @Override
    public double getX( double t ) {
        return alpha + a * Math.cos( kx * t );
    }

    @Override
    public double getY( double t ) {
        return beta + b * Math.sin( ky * t );
    }

    @Override
    public double getStart() {
        return 0;
    }

    @Override
    public double getEnd() {
        return Math.PI * 2d;
    }

    @Override
    public Point getCenter() {
        return new Point( alpha, beta );
    }

    @Override
    public void update( double time ) {

    }

    public void setA( double a ) {
        this.a = a;
    }

    public void setB( double b ) {
        this.b = b;
    }
}
