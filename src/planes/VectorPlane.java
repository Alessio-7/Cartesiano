package planes;

import funcs.Function;
import main.ColorUtils;
import primitives.Vec2;

import java.awt.*;

public class VectorPlane extends Plane<Vec2> {

    private final InfoVec[][] vecs;
    private final double space = 20;

    public VectorPlane( int SIZE, double scale ) {
        super( SIZE, scale );

        int l = (int) ((SIZE / space)+1);
        vecs = new InfoVec[l][l];
    }

    @Override
    protected void update() {

        if( funzioni.isEmpty() ) return;

        Function<Vec2> f = funzioni.get( 0 );

        Vec2 v;

        int i = 0, j;

        for( int x = (int) ((SIZE % space) / 2); x < SIZE; x += space ) {
            j = 0;
            for( int y = (int) ((SIZE % space) / 2); y < SIZE; y += space ) {

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
    protected void paintChild( Graphics2D g ) {
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
