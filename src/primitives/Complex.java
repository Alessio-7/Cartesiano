package primitives;

public class Complex {

    public final double a;
    public final double b;

    public Complex( double a, double b ) {
        this.a = a;
        this.b = b;
    }

    public static Complex fromPolar(double r, double theta){
        return Complex.exp(theta).timesRe(r);
    }
    public static Complex exp( double phase ) {
        return new Complex( Math.cos( phase ), Math.sin( phase ) );
    }

    public static Complex exp( Complex z ) {
        return fromPolar(z.b, Math.exp( z.a ) );
    }

    public static Complex sin( Complex z ) {
        return Complex.exp( z.timesIm( 1 ) ).sub( Complex.exp( z.timesIm( -1 ) ) ).over( new Complex( 0, 2 ) );
    }

    public static Complex fromVec2( Vec2 v ) {
        return new Complex( v.a, v.b );
    }

    public Complex pow( int p ) {
        return Complex.fromPolar(Math.pow(mod(),p), phaseRad()*p);
    }

    public Complex timesIm( double im ) {
        return new Complex( -b * im, a * im );
    }

    public Complex timesRe( double real ) {
        return new Complex( a * real, b * real );
    }

    public Complex times( Complex z ) {
        return new Complex( (a * z.a) - (b * z.b), (a * z.b) + (b * z.a) );
    }

    public Complex over( Complex z ) {
        return new Complex( ((a * z.a) + (b * z.b)), ((b * z.a) - (a * z.b)) ).timesRe( 1d / ((z.a * z.a) + (z.b * z.b)) );
    }

    public Complex sum( Complex z ) {
        return new Complex( a + z.a, b + z.b );
    }

    public Complex sub( Complex z ) {
        return new Complex( a - z.a, b - z.b );
    }

    public double mod() {
        return Math.sqrt( a * a + b * b );
    }

    public double phaseRad() {
        return Math.atan2( b, a );
    }

    public double phaseDeg() {
        return Math.toDegrees( phaseRad() );
    }

    public Complex conjugate() {
        return new Complex( a, -b );
    }

    @Override
    public String toString() {
        return a + (b < 0 ? "" : "+") + b + "i";
    }

    public Vec2 toVec2() {
        return new Vec2( a, b );
    }
}
