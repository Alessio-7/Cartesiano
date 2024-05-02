package primitives;

public class Vec {

    public final double[] v;

    public Vec( double... v ) {
        this.v = v;
    }

    public Vec( int n ) {
        this.v = new double[n];
    }

    public double get( int i ) {
        if( i >= v.length ) return 0;
        return v[i];
    }

    public double i() {
        return v[0];
    }

    public double j() {
        return v[1];
    }

    public double k() {
        return v[2];
    }

    public Vec sum( Vec vec ) {
        int l = Math.max( this.v.length, vec.v.length );
        Vec r = new Vec( l );

        for( int i = 0; i < l; i++ )
            r.v[i] = this.get( i ) + vec.get( i );

        return r;
    }

    public Vec sub( Vec vec ) {
        int l = Math.max( this.v.length, vec.v.length );
        Vec r = new Vec( l );

        for( int i = 0; i < l; i++ )
            r.v[i] = this.get( i ) - vec.get( i );

        return r;
    }

    public Vec scale( double scalar ) {
        Vec r = new Vec( this.v.length );

        for( int i = 0; i < this.v.length; i++ )
            r.v[i] = this.get( i ) * scalar;

        return r;
    }

    public double mod() {
        return Math.sqrt( dot( this ) );
    }

    public double dot( Vec vec ) {
        double dot = 0;

        for( int i = 0; i < this.v.length; i++ )
            dot += v[i] * vec.v[i];

        return dot;
    }

    @Override
    public String toString() {
        String s = "[ ";
        for( double e : v )
            s += e + ", ";

        return s.substring( 0, s.length() - 2 ) + " ]";
    }
}
