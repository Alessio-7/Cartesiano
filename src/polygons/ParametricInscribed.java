package polygons;

import funcs.Function;
import parametrics.Parametric;
import primitives.Point;
import study.RootsFinder;

public abstract class ParametricInscribed extends Polygon {

    private final Parametric parametric;
    protected int kf;
    protected Point pk;

    public ParametricInscribed( Parametric parametric ) {
        super( true );
        this.parametric = parametric;

        kf = 3;

        // findDistance();
    }

    public abstract double inverseGetX( double x );

    public double gamma( double x ) {
        return (pk.y < 0 ? -1 : 1) * parametric.getY( inverseGetX( x ) );
    }


    public double polynomial( double x, double d ) {
        return Math.pow( gamma( x ) - pk.y, 2 ) + Math.pow( x - pk.x, 2 ) - Math.pow( d, 2 );
    }

    protected double solution( double d ) {
        int root = 0;
        if( pk.x < 0 && pk.y > 0 || pk.x > 0 && pk.y < 0 ) root = 1;
        return new RootsFinder( x -> polynomial( x, d ) ).newton();
    }


    public double findDistance( double t, double distance ) {

        Function<Double> f = d -> {
            double sum = 0;

            pk = new Point( parametric.getX( t ), parametric.getY( t ) );

            Point pk1;
            double xpk1;
            for( int k = 1; k < kf; k++ ) {
                xpk1 = solution( d );
                //System.out.println( xpk1 );
                pk1 = new Point( xpk1, gamma( xpk1 ) );
                sum += Math.pow( -1, k ) * Point.distance( pk, pk1 );
                pk = pk1;
            }

            return sum;
        };

        return f.f( distance );
    }
}
