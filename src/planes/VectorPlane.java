package planes;

import funcs.Function;
import main.Utils;
import primitives.Complex;
import primitives.Vec2;

import java.awt.*;

public class VectorPlane extends FunctionPlane<Vec2> {

    private final InfoVec[][] vecs;
    private final double space;

    public VectorPlane( int SIZE, double scale ) {
        super( SIZE, scale );

        //space = scale / 2;
        space = 25;

        int l = (int) ((SIZE / space) + 1);
        vecs = new InfoVec[l][l];
    }

    public static VectorPlane getSample( int SIZE, double scale ) {

        VectorPlane p = new VectorPlane( SIZE, scale );

        p.addFunzione( new Function<Vec2>() {

            double x, y;

            @Override
            public Vec2 f( Vec2 v ) {
                Complex z = Complex.fromVec2( v );
                return z.pow( 2 ).sum( new Complex( x, y ) ).toVec2();
            }

            @Override
            public void update( double time ) {
                x = Math.cos( time );
                y = Math.sin( time );
            }
        } );

        return p;
    }

    @Override
    protected void update() {

        Function<Vec2> f = getFirstFunction();

        Vec2 v;

        int i = 0, j;

        for( int x = (int) ((SIZE % space) / 2); x < SIZE; x += space ) {
            j = 0;
            for( int y = (int) ((SIZE % space) / 2); y < SIZE; y += space ) {

                double a = pixelToCord( x );
                double b = -pixelToCord( y );

                v = f.f( new Vec2( a, b ) );

                double phase = v.phaseRad();
                double mod = v.mod();
                double len = Utils.scaleAtan( 0.5, 1, mod );

                vecs[i][j] = new InfoVec(
                        (int) (x + Math.cos( phase ) * (space - 5) * len),
                        (int) (y + Math.sin( phase ) * (space - 5) * len),
                        new Color( Utils.interpolateColor( Color.blue, Color.yellow, Utils.scaleAtan( mod ) ) ).getRGB()
                        //new Color( Utils.interpolateColor( Color.blue, Color.red, Utils.scaleAtan( mod ) ) ).getRGB()
                );
                j++;
            }
            i++;
        }

    }

    @Override
    protected void paintChild( Graphics2D g ) {

        int strokeSize = (int) ((BasicStroke) g.getStroke()).getLineWidth() + 2;
        int vecHeadSize = (int) (strokeSize * 1.9);

        g.setColor( Color.white );
        g.setStroke( new BasicStroke( strokeSize ) );
        g.fillRect( 0, 0, SIZE, SIZE );

        int i = 0, j;
        for( int x = 0; x < SIZE; x += space ) {
            j = 0;
            for( int y = 0; y < SIZE; y += space ) {

                InfoVec v = vecs[i][j];

                g.setColor( new Color( v.c ) );
                g.drawLine( x, y, v.x, v.y );
                g.fillRect( v.x - (vecHeadSize / 2), v.y - (vecHeadSize / 2), vecHeadSize, vecHeadSize );
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
