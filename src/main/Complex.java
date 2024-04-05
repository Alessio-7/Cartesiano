package main;

public class Complex {

    double a;
    double b;

    public Complex( double a, double b ) {

        this.a = a;
        this.b = b;
    }

    public static Complex exp( double phase ) {
        return exp( new Complex( 0, phase ) );
    }

    public static Complex exp( Complex z ) {
        return new Complex( Math.cos( z.b ), Math.sin( z.b ) ).timesRe( Math.exp( z.a ) );
    }

    public static Complex sin( Complex z ) {
        return Complex.exp( z.timesIm( 1 ) ).sub( Complex.exp( z.timesIm( -1 ) ) ).over( new Complex( 0, 2 ) );
    }

    public Complex pow( int p ) {
        Complex z = this;

        if( p == 1 )
            return this;

        if( p == 0 )
            return new Complex( 1, 0 );

        for( int i = 1; i < p; i++ )
            z = z.times( this );

        return z;
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

    public double phaseRad() {
        return Math.atan2( b, a );
    }

    public double phaseDeg() {
        //double p = Math.toDegrees( Math.atan2( b, a ) );
        //return p < 0 ? p + 360 : p;
        return Math.toDegrees( phaseRad() );
    }

    public double mod() {
        return Math.sqrt( Math.pow( a, 2 ) + Math.pow( b, 2 ) );
    }

    public Complex conjugate() {
        return new Complex( a, -b );
    }

    @Override
    public String toString() {
        return a + (b < 0 ? "" : "+") + b + "i";
    }
}
