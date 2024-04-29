package polygons;

import parametrics.Ellipse;
import primitives.Point;

import java.util.ArrayList;

public class EllipseInscribed extends Polygon {
    private final Ellipse e;
    private int nSides;
    private double a;
    private double b;
    private double alpha;

    public EllipseInscribed( Ellipse e, int nSides ) {
        this( e, nSides, 0 );
    }

    public EllipseInscribed( Ellipse e, int nSides, double alpha ) {
        super( false, (Point) null );
        this.e = e;
        this.nSides = nSides;
        this.alpha = alpha;
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
        ArrayList<Point> p = recursive( i / 2, p2, m );
        p.add( m );
        p.addAll( recursive( i / 2, m, p1 ) );
        return p;
    }

    @Override
    public void update( double time ) {

        this.a = e.getA();
        this.b = e.getB();

        points.clear();
/*
        double s;
        for( int i = 0; i < nSides; i++ ) {
            s = alpha + (Math.PI * 2 * i) / nSides;
            points.add( new Point( a * Math.cos( s ) + e.getAlpha(), b * Math.sin( s ) + e.getBeta() ) );
        }

 */


        Point p1 = new Point( 0, b );
        Point p2 = new Point( a, 0 );

        ArrayList<Point> ms = recursive( nSides / 2, p1, p2 );
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

    public int getnSides() {
        return nSides;
    }

    public void setnSides( int nSides ) {
        this.nSides = nSides;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha( double alpha ) {
        this.alpha = alpha;
    }
}
