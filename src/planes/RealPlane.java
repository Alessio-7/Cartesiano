package planes;

import funcs.Function;

import java.awt.*;

public class RealPlane extends Plane<Double> {

    private int[][] ys;

    public RealPlane( int SIZE, double scale ) {
        super( SIZE, scale );
    }

    @Override
    protected void update() {
        ys = new int[funzioni.size()][SIZE];

        for( int x = 0; x < SIZE; x++ ) {
            double x1 = pixelToCord( x );
            int i = 0;
            for( Function<Double> funz : funzioni ) {
                int y = -cordToPixel( funz.f( x1 ) );
                ys[i][x] = y;
                i++;
            }
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
        for( int f = 0; f < funzioni.size(); f++ ) {
            for( int x = 1; x < SIZE; x++ ) {
                g.drawLine( x - 1, ys[f][x - 1], x, ys[f][x] );
            }
        }
    }
}
