package main;

import java.awt.*;

public class VectorComplexPlane extends Plane<Complex> {


    Vec3[][] vecs;
    double space = 30;

    public VectorComplexPlane( int SIZE, double scale ) {
        super( SIZE, scale );

        int l = (int) (SIZE / space);
        System.out.println( l );
        vecs = new Vec3[l + 1][l + 1];
    }

    public static int interpolateColor( Color color1, Color color2, double alpha ) {
        double resultRed = color1.getRed() + alpha * (color2.getRed() - color1.getRed());
        double resultGreen = color1.getGreen() + alpha * (color2.getGreen() - color1.getGreen());
        double resultBlue = color1.getBlue() + alpha * (color2.getBlue() - color1.getBlue());

        return new Color( (int) resultRed, (int) resultGreen, (int) resultBlue ).getRGB();
    }


    @Override
    void update() {

        if( funzioni.isEmpty() ) return;

        Function<Complex> f = funzioni.get( 0 );

        Complex z;

        int i = 0, j;

        for( int x = 0; x < SIZE; x += space ) {
            j = 0;
            for( int y = 0; y < SIZE; y += space ) {

                double a = (x - HALF_SIZE) / scale;
                double b = -(y - HALF_SIZE) / scale;

                z = f.f( new Complex( a, b ) );

                double phase = z.phaseRad();


                vecs[i][j] = new Vec3(
                        (int) (x + Math.cos( phase ) * (space - 5)),
                        (int) (y + Math.sin( phase ) * (space - 5)),
                        new Color( interpolateColor( Color.blue, Color.yellow, (Math.atan( z.mod() ) * 2 / Math.PI) ) ).getRGB()
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

                Vec3 v = vecs[i][j];

                g.setColor( new Color( v.z ) );
                g.drawLine( x, y, v.x, v.y );
                g.fillRect( v.x - 1, v.y - 1, 3, 3 );
                j++;
            }
            i++;
        }
    }

    private class Vec3 {
        int x, y, z;

        public Vec3( int x, int y, int z ) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
