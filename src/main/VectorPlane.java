package main;

import java.awt.*;

public class VectorPlane extends Plane<Vec2> {

    InfoVec[][] vecs;
    double space = 20;

    public VectorPlane( int SIZE, double scale ) {
        super( SIZE, scale );

        int l = (int) (SIZE / space);
        vecs = new InfoVec[l + 1][l + 1];
    }

    @Override
    void update() {

        if( funzioni.isEmpty() ) return;

        Function<Vec2> f = funzioni.get( 0 );

        Vec2 v;

        int i = 0, j;

        for( int x = 0; x < SIZE; x += space ) {
            j = 0;
            for( int y = 0; y < SIZE; y += space ) {

                double a = pixelToCord( x );
                double b = -pixelToCord( y );

                v = f.f( new Vec2( a, b ) );

                double phase = v.phaseRad();

                vecs[i][j] = new InfoVec(
                        (int) (x + Math.cos( phase ) * (space - 5)),
                        (int) (y + Math.sin( phase ) * (space - 5)),
                        new Color( ColorUtils.interpolateColor( Color.blue, Color.yellow, (Math.atan( v.mod() ) * 2 / Math.PI) ) ).getRGB()
                );
                j++;
            }
            i++;
        }

    }

    @Override
    void paintChild( Graphics2D g ) {
        g.setColor( Color.black );
        g.fillRect( 0, 0, SIZE, SIZE );

        int i = 0, j;
        for( int x = 0; x < SIZE; x += space ) {
            j = 0;
            for( int y = 0; y < SIZE; y += space ) {

                InfoVec v = vecs[i][j];

                g.setColor( new Color( v.c ) );
                g.drawLine( x, y, v.x, v.y );
                g.fillRect( v.x - 1, v.y - 1, 3, 3 );
                j++;
            }
            i++;
        }
    }

    private class InfoVec {
        int x, y, c;

        public InfoVec( int x, int y, int c ) {
            this.x = x;
            this.y = y;
            this.c = c;
        }
    }

}
