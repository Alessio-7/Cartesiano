package polygons;

import parametrics.Ellipse;
import primitives.Point;

public class EllipseInscribed extends Polygon {
    private final Ellipse ellipse;

    public EllipseInscribed( Ellipse ellipse ) {
        super( false, (Point) null );
        this.ellipse = ellipse;
        update( 0 );
    }

    @Override
    public void update( double time ) {
        points.clear();

        double a = ellipse.getA();
        double b = ellipse.getB();
        double alpha = a * a;
        double beta = b * b;

        double betaSquaredOverAlphaSquared = (Math.pow( beta, 2 ) / Math.pow( alpha, 2 ));

        double A = alpha / beta + beta / alpha;
        double B = a * (alpha / beta + 1);
        double sqrtDelta = Math.sqrt( (Math.pow( alpha, 3 ) * ((1 / beta) - (1 / Math.pow( beta, 2 )) + (2 / (alpha * beta)))) + (beta * (2 + (3 * beta / alpha))) + (3 * alpha) );

        double xm = (-B + sqrtDelta) / (2 * A);
        double ym = Math.sqrt( beta * (1 - (xm * xm / alpha)) );

        xm += ellipse.getAlpha();
        ym += ellipse.getBeta();

        points.add( new Point( 0, b ) );
        points.add( new Point( xm, ym ) );
        points.add( new Point( a, 0 ) );
        points.add( new Point( xm, -ym ) );
        points.add( new Point( 0, -b ) );
        points.add( new Point( -xm, -ym ) );
        points.add( new Point( -a, 0 ) );
        points.add( new Point( -xm, ym ) );
    }
}
