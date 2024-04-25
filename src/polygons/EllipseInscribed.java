package polygons;

import parametrics.Ellipse;
import primitives.Point;

import java.util.ArrayList;

public class EllipseInscribed extends Polygon {
    private final Ellipse ellipse;
    private final int nPoints;
    private double a;
    private double b;

    public EllipseInscribed( Ellipse ellipse, int nSides ) {
        super( false, (Point) null );
        this.ellipse = ellipse;
        this.nPoints = nSides;
        update( 0 );
    }

    private Point pointBetween( Point p1, Point p2 ) {
        double alpha = a * a;
        double beta = b * b;

        double m = (p1.x - p2.x) / (p2.y - p1.y);
        double q = ((p2.x * p2.x) + (p2.y * p2.y) - (p1.x * p1.x) - (p1.y * p1.y)) / (2 * (p2.y - p1.y));

        double A = (beta / alpha + m * m);
        double B = 2 * m * q;
        double C = q * q - beta;

        double xm = (-B + Math.sqrt( (B * B) - (4 * A * C) )) / (2 * A);
        //double ym = Math.sqrt( (alpha * beta - xm * xm * beta) / alpha );
        double ym = m * xm + q;

        return new Point( xm, ym );
    }

    private ArrayList<Point> recursive( int i, Point p1, Point p2 ) {

        if( i == 0 ) return new ArrayList<>( 1 );

        Point m = pointBetween( p1, p2 );
        ArrayList<Point> p = recursive( i - 1, p2, m );
        p.add( m );
        p.addAll( recursive( i - 1, m, p1 ) );
        return p;
    }

    @Override
    public void update( double time ) {

        this.a = ellipse.getA();
        this.b = ellipse.getB();

        points.clear();

        Point p1 = new Point( 0, b );
        Point p2 = new Point( a, 0 );

        ArrayList<Point> ms = recursive( nPoints / 2, p1, p2 );
        ms.sort( ( point1, point2 ) -> (int) ((point1.x - point2.x) * 1000) );


        points.add( p1 );
        points.addAll( ms );
        points.add( p2 );
        for( int i = ms.size() - 1; i >= 0; i-- )
            points.add( ms.get( i ).negativeY() );
        points.add( p1.negativeY() );
        for( Point m : ms )
            points.add( m.negative() );
        points.add( p2.negativeX() );
        for( int i = ms.size() - 1; i >= 0; i-- )
            points.add( ms.get( i ).negativeX() );
    }
}
