package polygons;

import funcs.Function;
import parametrics.Parametric;
import primitives.Mat;
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

    protected Function<Double> derivateParametricGetX() {
        return new Study( parametric::getX ).derivate();
    }

    protected Function<Double> derivateParametricGetY() {
        return new Study( parametric::getY ).derivate();
    }

    private Mat inverseJacobian( double d, double t, double u ) {

        Function<Double> dqx = derivateParametricGetX();
        Function<Double> dqy = derivateParametricGetY();

        double scalar = 1 / (d * ((dqx.f( t ) * Math.cos( u )) + (dqy.f( t ) * Math.sin( u ))));

        return new Mat( new double[]{d * Math.cos( u ), d * Math.sin( u )}, new double[]{dqy.f( t ), -dqx.f( t )} ).scale( scalar );
    }

    protected Vec2 f( double d, double t, double u ) {
        return new Vec2( parametric.getX( t ) - d * Math.cos( u ) - parametric.getX( tpk ), parametric.getY( t ) - d * Math.sin( u ) - parametric.getY( tpk ) );
    }

    protected Vec2 f( double d, Vec2 v ) {
        return f( d, v.i(), v.j() );
    }

    protected double solution2( double d, Vec2 a, Vec2 b ) {
        Vec2 fa = f( d, a );
        Vec2 fb = f( d, b );
        if( fa.dot( fb ) >= 0 )
            return Double.NEGATIVE_INFINITY;
        if( fa.mod() < fb.mod() ) {
            Vec2 t = b;
            b = a;
            a = t;
        }
        Vec2 c = a, s = new Vec2( 0, 0 );
        boolean mflag = true;

        fa = f( d, a );
        fb = f( d, b );
        Vec2 fc, fs = f( d, s );
        while( !((fb.i() < 0.05 || fs.i() < 0.05) || b.sub( a ).mod() < 0.05) ) {
            fa = f( d, a );
            fb = f( d, b );
            fc = f( d, c );
            fs = f( d, s );

            if( !fa.equals( fc ) && !fb.equals( fc ) ) {
                //s= nuh uh

            }
        }

        return 0;
    }

    protected double solution( double d, double x0 ) {

        double x = tpk;
        Vec xi = new Vec2( x, 0 );

        int iterations = 0;
        do {
            xi = xi.sub( inverseJacobian( d, xi.i(), xi.j() ).timesVec( f( d, xi.i(), xi.j() ) ) );
            if( iterations++ > 2000 ) break;
        } while( f( d, xi.i(), xi.j() ).mod() > 0.1 );

        if( Double.isNaN( xi.mod() ) )
            return 0;

        return xi.i() % (2 * Math.PI);
    }


    public double findDistance( double t, double distance ) {
        return 0;
    }
}
