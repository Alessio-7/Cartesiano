package parametrics;

import primitives.Point;

public class Lissajous implements Parametric {

    private double alpha;
    private double beta;
    private double a;
    private double b;
    private double kx;
    private double ky;

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

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha( double alpha ) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta( double beta ) {
        this.beta = beta;
    }

    public double getA() {
        return a;
    }

    public void setA( double a ) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB( double b ) {
        this.b = b;
    }

    public double getKx() {
        return kx;
    }

    public void setKx( double kx ) {
        this.kx = kx;
    }

    public double getKy() {
        return ky;
    }

    public void setKy( double ky ) {
        this.ky = ky;
    }
}
