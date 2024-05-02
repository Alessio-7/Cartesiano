package polygons;

import funcs.Function;
import parametrics.Parametric;
import primitives.Mat;
import primitives.Point;
import primitives.Vec;
import primitives.Vec2;
import study.Study;

public abstract class ParametricInscribed extends Polygon {

    private final Parametric parametric;
    protected int kf;
    protected double tpk;

    public ParametricInscribed( Parametric parametric, int kf ) {
        super( true );
        this.parametric = parametric;
        this.kf = kf;

        // findDistance();
    }

    private Mat inverseJacobian( double d, Vec v ) {

        Function<Double> dqx = new Study( parametric::getX ).derivate();
        Function<Double> dqy = new Study( parametric::getY ).derivate();

        double scalar = 1 / (d * (dqx.f( v.i() ) * Math.cos( v.j() ) + dqy.f( v.i() ) * Math.sin( v.j() )));

        return new Mat(
                new double[]{d * Math.cos( v.j() ), d * Math.sin( v.j() )},
                new double[]{dqy.f( v.i() ), -dqx.f( v.i() )}
        ).scale( scalar );
    }

    protected double solution( double d ) {

        double x = tpk, y = 0;
        Vec xi = new Vec2( x, y );

        Vec2 u = new Vec2(
                parametric.getX( x ) - d * Math.cos( y ) - parametric.getX( tpk ),
                parametric.getY( x ) - d * Math.sin( y ) - parametric.getY( tpk )
        );

        xi = xi.sub( inverseJacobian( d, xi ).timesVec( u ) );
        xi = xi.sub( inverseJacobian( d, xi ).timesVec( u ) );
        //xi = xi.sub( inverseJacobian( d, xi ).timesVec( u ) );
        //xi = xi.sub( inverseJacobian( d, xi ).timesVec( u ) );

        return xi.i();

    }


    public double findDistance( double t, double distance ) {

        Function<Double> f = d -> {
            double sum = 0;

            tpk = t;

            Point pk = new Point( parametric.getX( t ), parametric.getY( t ) );
            Point pk1;
            double tpk1;
            for( int k = 1; k < kf; k++ ) {
                tpk1 = solution( d );
                //System.out.println( xpk1 );
                pk1 = new Point( parametric.getX( tpk1 ), parametric.getY( tpk1 ) );
                sum += Math.pow( -1, k ) * Point.distance( pk, pk1 );
                pk = pk1;
            }

            return sum;
        };

        return f.f( distance );
    }
}
