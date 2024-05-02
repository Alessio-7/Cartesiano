package primitives;

public class Mat implements Cloneable {
    public final double[][] m;

    public Mat( double[]... r ) {
        this.m = r;
    }

    public Mat( int m, int n ) {
        this.m = new double[m][n];
    }

    public double get( int i, int j ) {
        if( i >= m.length | j >= m[0].length )
            return 0;
        return m[i][j];
    }

    public Mat sum( Mat mat ) {
        int m = Math.max( this.m.length, mat.m.length );
        int n = Math.max( this.m[0].length, mat.m[0].length );

        Mat r = new Mat( m, n );

        for( int i = 0; i < m; i++ ) {
            for( int j = 0; j < n; j++ ) {
                r.m[i][j] = get( i, j ) + mat.get( i, j );
            }
        }

        return r;
    }

    public Mat sub( Mat mat ) {
        int m = Math.max( this.m.length, mat.m.length );
        int n = Math.max( this.m[0].length, mat.m[0].length );

        Mat r = new Mat( m, n );

        for( int i = 0; i < m; i++ ) {
            for( int j = 0; j < n; j++ ) {
                r.m[i][j] = get( i, j ) - mat.get( i, j );
            }
        }

        return r;
    }

    public Mat transpose() {
        Mat r = new Mat( m[0].length, m.length );

        for( int i = 0; i < m[0].length; i++ ) {
            for( int j = 0; j < m.length; j++ ) {
                r.m[i][j] = get( j, i );
            }
        }

        return r;
    }

    public Vec timesVec( Vec vec ) {
        double[] r = new double[vec.v.length];

        Vec row;
        for( int i = 0; i < m.length; i++ ) {
            row = new Vec( m[i] );
            r[i] = row.dot( vec );
        }

        return new Vec( r );
    }

    public Mat scale( double scalar ) {
        Mat r = null;
        try {
            r = (Mat) this.clone();
        } catch( CloneNotSupportedException e ) {
            e.printStackTrace();
        }

        for( int i = 0; i < m[0].length; i++ ) {
            for( int j = 0; j < m.length; j++ ) {
                r.m[i][j] *= scalar;
            }
        }

        return r;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for( int i = 0; i < m[0].length; i++ ) {
            s.append( "[ " );
            for( int j = 0; j < m.length; j++ ) {
                s.append( m[i][j] ).append( "  " );
            }
            s = new StringBuilder( s.substring( 0, s.length() - 2 ) );
            s.append( " ]\n" );
        }

        return s.toString();
    }
}
