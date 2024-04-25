package polygons;

import primitives.Point;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Polygon {

    protected final ArrayList<Point> points;
    private final boolean open;

    public Polygon( Point... points ) {
        this( true, points );
    }

    public Polygon( boolean open, Point... points ) {
        this.open = open;
        this.points = new ArrayList<>( Arrays.asList( points ) );
    }

    public Polygon( boolean open ) {
        this.open = open;
        this.points = new ArrayList<>();
    }

    public boolean isOpen() {
        return open;
    }

    public Point[] getPoints() {
        return points.toArray( new Point[0] );
    }

    public abstract void update( double time );

}
