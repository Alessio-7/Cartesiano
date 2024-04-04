package main;

import java.awt.*;

public class RealPlane extends Plane<Double> {

    int[][] ys;

    public RealPlane( int SIZE, double scale ) {
        super( SIZE, scale );
    }

    @Override
    void update() {
        ys = new int[funzioni.size()][SIZE];

        for( int x = 0; x < SIZE; x++ ) {
            double x1 = (x - HALF_SIZE) / scale;
            int i = 0;
            for( Function<Double> funz : funzioni ) {
                funz.update( t );
                int y = (int) -((funz.f( x1 ) * scale) - HALF_SIZE);
                ys[i][x] = y;
                i++;
            }
        }
    }

    @Override
    public void paintChild( Graphics2D g ) {
        g.setStroke( new BasicStroke( 2 ) );
        g.setColor( Color.white );
        for( int f = 0; f < funzioni.size(); f++ ) {
            for( int x = 1; x < SIZE; x++ ) {
                g.drawLine( x - 1, ys[f][x - 1], x, ys[f][x] );
            }
        }
    }
}
