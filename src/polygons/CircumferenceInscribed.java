package polygons;

import parametrics.Circumference;
import primitives.Point;

public class CircumferenceInscribed extends Polygon {
    final Circumference c;
    int nSides;
    double alpha;

    public CircumferenceInscribed( Circumference c, int nSides ) {
        this( c, nSides, 0 );
    }

    public CircumferenceInscribed( Circumference c, int nSides, double alpha ) {
        super( false, (Point) null );
        this.c = c;
        this.nSides = nSides;
        this.alpha = alpha;
    }


    @Override
    public void update( double time ) {

        points.clear();

        double r = c.getR();
        double s;
        for( int i = 0; i < nSides; i++ ) {
            s = alpha + (Math.PI * 2 * i) / nSides;
            points.add( new Point( r * Math.cos( s ) + c.getAlpha(), r * Math.sin( s ) + c.getBeta() ) );
        }
    }

    public Circumference getC() {
        return c;
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
