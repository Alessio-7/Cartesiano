package planes;

import main.Utils;
import parametrics.Parametric;
import primitives.Point;

import java.awt.*;

public class ParametricPlane extends Plane {

    private final int[][] points;
    private final int iterations;
    protected Parametric parametric;

    public ParametricPlane( int SIZE, double scale ) {
        super( SIZE, scale );
        iterations = 200;
        points = new int[iterations + 1][2];
    }

    public static ParametricPlane getSample( int SIZE, double scale ) {

        ParametricPlane p = new ParametricPlane( SIZE, scale );

        //p.setParametric( new Lissajous( 3, 2 ) );
        //p.setParametric( new Hypotrochoid( 6, 4, 1 ) );
        p.setParametric( new Parametric() {
            @Override
            public double getX( double t ) {
                return Math.pow( Math.cos( t ), 3 );
            }

            @Override
            public double getY( double t ) {
                return Math.pow( Math.sin( t ), 3 );
            }

            @Override
            public double getStart() {
                return -Math.PI;
            }

            @Override
            public double getEnd() {
                return Math.PI;
            }

            @Override
            public Point getCenter() {
                return new Point( 0, 0 );
            }

            @Override
            public void update( double time ) {

            }
        } );

        return p;

    }

    @Override
    protected void update() {

        double t;
        for( int i = 0; i <= iterations; i++ ) {
            t = Utils.lerp( parametric.getStart(), parametric.getEnd(), (double) i / iterations );
            points[i][0] = cordXToPixel( parametric.getX( t ) );
            points[i][1] = cordYToPixel( parametric.getY( t ) );
        }

    }

    @Override
    protected void paintChild( Graphics2D g ) {

        g.setColor( Color.black );
        g.fillRect( 0, 0, SIZE, SIZE );

        g.setStroke( new BasicStroke( 1 ) );

        g.setColor( Color.gray );
        g.drawLine( 0, HALF_SIZE, SIZE, HALF_SIZE );
        for( int x = (SIZE % scaleInt) / 2; x < SIZE; x += scaleInt ) {
            g.drawLine( x, HALF_SIZE - 3, x, HALF_SIZE + 3 );
        }

        g.drawLine( HALF_SIZE, 0, HALF_SIZE, SIZE );
        for( int y = (SIZE % scaleInt) / 2; y < SIZE; y += scaleInt ) {
            g.drawLine( HALF_SIZE - 3, y, HALF_SIZE + 3, y );
        }

        g.setStroke( new BasicStroke( 2 ) );
        g.setColor( Color.white );
        for( int i = 1; i < points.length; i++ ) {
            g.drawLine( points[i - 1][0], points[i - 1][1], points[i][0], points[i][1] );
        }

        double t = Utils.lerp( parametric.getStart(), parametric.getEnd(), (time % parametric.getEnd()) / parametric.getEnd() );

        g.setColor( Color.gray );
        g.drawLine( cordXToPixel( parametric.getCenter().x ), cordYToPixel( parametric.getCenter().y ), cordXToPixel( parametric.getX( t ) ), cordYToPixel( parametric.getY( t ) ) );

    }

    @Override
    protected void updateChilds() {
        parametric.update( time );
    }

    public void setParametric( Parametric parametric ) {
        this.parametric = parametric;
    }
}
