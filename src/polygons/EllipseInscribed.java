package polygons;

import parametrics.Ellipse;
import primitives.Point;

public class EllipseInscribed extends Polygon {
    private final Ellipse ellipse;

    public EllipseInscribed( Ellipse ellipse ) {
        super( true, (Point) null );
        this.ellipse = ellipse;
        update( 0 );
    }

    @Override
    public void update( double time ) {
        points.clear();

        double a = ellipse.getA() * 2;
        double b = ellipse.getB() * 2;
        double alpha = a * a;
        double beta = b * b;

        double betaSquaredOverAlphaSquared = (Math.pow( beta, 2 ) / Math.pow( alpha, 2 ));

        double A = 16d * (alpha + betaSquaredOverAlphaSquared);
        double B = 8 * a * (beta - alpha);
        double sqrtDelta = 8 * beta * Math.sqrt( (16 * alpha) + (2 * beta / alpha) + (15 * betaSquaredOverAlphaSquared) - 1 );

        double xm = (-B + sqrtDelta) / (2 * A) - a / 2;
        //double ym = Math.sqrt( beta * (1 - (xm * xm / alpha)) ) - b / 2;
        double ym = (4 * xm * a - alpha + beta) / (4 * b);

        /*double m = ym / xm;
        xm = Math.sqrt( (alpha * beta) / (beta + (alpha * m * m)) );
        ym = Math.sqrt( beta * (1 - (xm * xm / alpha)) );*/

        xm += ellipse.getAlpha();
        ym += ellipse.getBeta();

        points.add( new Point( 0, b / 2 ) );
        points.add( new Point( xm, ym ) );
        points.add( new Point( a / 2, 0 ) );
        /*points.add( new Point( xm, -ym ) );
        points.add( new Point( 0, -b / 2 ) );
        points.add( new Point( -xm, -ym ) );
        points.add( new Point( -a / 2, 0 ) );
        points.add( new Point( -xm, ym ) );*/
    }
}
