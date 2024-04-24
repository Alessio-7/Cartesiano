package planes;

import funcs.Function;
import main.Utils;
import parametrics.Ellipse;
import parametrics.Parametric;
import study.Study;

import java.awt.*;
import java.util.ArrayList;

public class RealPlane extends FunctionPlane<Double> {

    private final int iterations;
    protected ArrayList<Parametric> parametrics;
    private int[][] ys;
    private int[][][] points;

    public RealPlane( int SIZE, double scale ) {
        super( SIZE, scale );
        parametrics = new ArrayList<>();
        iterations = 200;
    }

    public static Plane getSample( int SIZE, double scale ) {
        RealPlane p = new RealPlane( SIZE, scale );

        //p.addFunction( new OndaQuadra( 1 ) );
        //p.addFunction( new Sin( 1d, 1d ) );

        Study s = new Study( p, new Function<Double>() {
            @Override
            public Double f( Double x ) {
                return 3 * Math.pow( x, 2 ) - 3;
            }

            @Override
            public void update( double time ) {

            }
        } );

        p.addFunction( s.integrate() );
        p.addParametric( new Ellipse( 4, 2 ) );

        return p;
    }

    @Override
    protected void update() {
        ys = new int[functions.size()][SIZE];

        for( int x = 0; x < SIZE; x++ ) {
            double x1 = pixelToCord( x );
            int i = 0;
            for( Function<Double> funz : functions ) {
                int y = cordYToPixel( funz.f( x1 ) );
                ys[i][x] = y;
                i++;
            }
        }

        points = new int[parametrics.size()][iterations + 1][2];

        double t;
        for( int tIter = 0; tIter <= iterations; tIter++ ) {
            int i = 0;
            for( Parametric parametric : parametrics ) {
                t = Utils.lerp( parametric.getStart(), parametric.getEnd(), (double) tIter / iterations );
                points[i][tIter][0] = cordXToPixel( parametric.getX( t ) );
                points[i][tIter][1] = cordYToPixel( parametric.getY( t ) );
                i++;
            }
        }
    }

    @Override
    protected void paintChild( Graphics2D g ) {
        g.setColor( Color.black );
        g.fillRect( 0, 0, SIZE, SIZE );

        g.setStroke( new BasicStroke( 1 ) );

        g.setColor( Color.darkGray.darker().darker() );
        for( int x = (SIZE % scaleInt) / 2; x < SIZE; x += scaleInt ) {
            //g.drawLine( x, HALF_SIZE - 3, x, HALF_SIZE + 3 );
            g.drawLine( x, 0, x, SIZE );
        }

        for( int y = (SIZE % scaleInt) / 2; y < SIZE; y += scaleInt ) {
            //g.drawLine( HALF_SIZE - 3, y, HALF_SIZE + 3, y );
            g.drawLine( 0, y, SIZE, y );
        }

        g.setColor( Color.gray );
        g.drawLine( 0, HALF_SIZE, SIZE, HALF_SIZE );
        g.drawLine( HALF_SIZE, 0, HALF_SIZE, SIZE );

        g.setStroke( new BasicStroke( 2 ) );
        g.setColor( Color.white );
        for( int f = 0; f < functions.size(); f++ ) {
            for( int x = 1; x < SIZE; x++ ) {

                int y1 = ys[f][x - 1];
                int y2 = ys[f][x];

                if( y1 < 1 || y2 < 1 ) continue;

                g.drawLine( x - 1, y1, x, y2 );
            }
        }

        for( int p = 0; p < parametrics.size(); p++ ) {
            for( int i = 1; i < points[p].length; i++ ) {
                g.drawLine( points[p][i - 1][0], points[p][i - 1][1], points[p][i][0], points[p][i][1] );
            }
        }
    }

    public void addParametric( Parametric p ) {
        parametrics.add( p );
    }

    @Override
    protected void updateChilds() {
        super.updateChilds();
        for( Parametric p : parametrics )
            p.update( time );
    }
}
