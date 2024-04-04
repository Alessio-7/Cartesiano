package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ComplexPlane extends Plane<Complex> {

    BufferedImage plane;

    public ComplexPlane( int SIZE, double scale ) {
        super( SIZE, scale );

        plane = new BufferedImage( SIZE, SIZE, BufferedImage.TYPE_INT_RGB );
    }

    public static int hsvToRgb( float H, float S, float V ) {

        float R = 0, G = 0, B = 0;

        H /= 360f;
        //S /= 100f;
        //V /= 100f;

        if( S == 0 ) {
            R = V * 255;
            G = V * 255;
            B = V * 255;
        } else {
            float var_h = H * 6;
            if( var_h == 6 ) var_h = 0; // H must be < 1
            int var_i = (int) Math.floor( var_h ); // Or ... var_i =
            // floor( var_h )
            float var_1 = V * (1 - S);
            float var_2 = V * (1 - S * (var_h - var_i));
            float var_3 = V * (1 - S * (1 - (var_h - var_i)));

            float var_r;
            float var_g;
            float var_b;
            if( var_i == 0 ) {
                var_r = V;
                var_g = var_3;
                var_b = var_1;
            } else if( var_i == 1 ) {
                var_r = var_2;
                var_g = V;
                var_b = var_1;
            } else if( var_i == 2 ) {
                var_r = var_1;
                var_g = V;
                var_b = var_3;
            } else if( var_i == 3 ) {
                var_r = var_1;
                var_g = var_2;
                var_b = V;
            } else if( var_i == 4 ) {
                var_r = var_3;
                var_g = var_1;
                var_b = V;
            } else {
                var_r = V;
                var_g = var_1;
                var_b = var_2;
            }

            R = var_r * 255; // RGB results from 0 to 255
            G = var_g * 255;
            B = var_b * 255;
        }

        //System.out.println( Arrays.toString( new float[]{R, G, B} ) );

        return new Color( (int) R, (int) G, (int) B ).getRGB();
    }

    void grid() {

        if( funzioni.isEmpty() ) return;

        Function<Complex> f = funzioni.get( 0 );
        Complex z;

        for( int x = (SIZE % scaleInt) / 2; x < SIZE; x += scaleInt ) {
            for( int y = 0; y < SIZE; y++ ) {

                double a = (x - HALF_SIZE) / scale;
                double b = (y - HALF_SIZE) / scale;

                z = f.f( new Complex( a, b ) );

                int x1 = (int) -((z.a() * scale) - HALF_SIZE);
                int y1 = (int) -((z.b() * scale) - HALF_SIZE);

                if( x1 < SIZE && x1 > 0 && y1 < SIZE && y1 > 0 ) {
                    plane.setRGB( x1, y1, (a == 0 ? Color.cyan : Color.gray).getRGB() );
                }
            }
        }

        for( int y = (SIZE % scaleInt) / 2; y < SIZE; y += scaleInt ) {
            for( int x = 0; x < SIZE; x++ ) {

                double a = (x - HALF_SIZE) / scale;
                double b = (y - HALF_SIZE) / scale;

                z = f.f( new Complex( a, b ) );

                int x1 = (int) -((z.a() * scale) - HALF_SIZE);
                int y1 = (int) -((z.b() * scale) - HALF_SIZE);

                if( x1 < SIZE && x1 > 0 && y1 < SIZE && y1 > 0 ) {
                    plane.setRGB( x1, y1, (b == 0 ? Color.cyan : Color.gray).getRGB() );
                }
            }
        }


    }

    void color() {
        if( funzioni.isEmpty() ) return;

        Function<Complex> f = funzioni.get( 0 );

        Complex z;

        for( int x = 0; x < SIZE; x++ ) {
            for( int y = 0; y < SIZE; y++ ) {

                double a = (x - HALF_SIZE) / scale;
                double b = (y - HALF_SIZE) / scale;

                z = f.f( new Complex( a, b ) );
                //plane.setRGB( x, y, hsvToRgb( (float) z.phase(), 1f, (float) (Math.atan( z.mod() ) * (2 / Math.PI)) ) );
                plane.setRGB( x, y, hsvToRgb( (float) z.phase(), 1f, (float) (Math.atan( z.mod() ) * 2 / Math.PI) ) );
            }
        }
    }

    @Override
    void update() {

        color();

    }

    @Override
    void paintChild( Graphics2D g ) {
        g.drawImage( plane, 0, 0, null );
    }
}
