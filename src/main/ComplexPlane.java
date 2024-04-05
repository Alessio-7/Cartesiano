package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ComplexPlane extends Plane<Complex> {

    BufferedImage plane;

    public ComplexPlane( int SIZE, double scale ) {
        super( SIZE, scale );

        plane = new BufferedImage( SIZE, SIZE, BufferedImage.TYPE_INT_RGB );
    }

    public static int hslToRgb( float h, float s, float l ) {
        if( s < 0.0f || s > 100.0f ) {
            String message = "Color parameter outside of expected range - Saturation";
            throw new IllegalArgumentException( message );
        }

        if( l < 0.0f || l > 100.0f ) {
            String message = "Color parameter outside of expected range - Luminance";
            throw new IllegalArgumentException( message );
        }

        //  Formula needs all values between 0 - 1.

        if( h < 0 )
            h += 360;

        h = h % 360.0f;
        h /= 360f;
        //s /= 100f;
        //l /= 100f;

        float q = 0;

        if( l < 0.5 )
            q = l * (1 + s);
        else
            q = (l + s) - (s * l);

        float p = 2 * l - q;

        float r = Math.max( 0, HueToRGB( p, q, h + (1.0f / 3.0f) ) );
        float g = Math.max( 0, HueToRGB( p, q, h ) );
        float b = Math.max( 0, HueToRGB( p, q, h - (1.0f / 3.0f) ) );


        r = Math.min( r, 1.0f );
        g = Math.min( g, 1.0f );
        b = Math.min( b, 1.0f );

        return new Color( r, g, b ).getRGB();
    }

    private static float HueToRGB( float p, float q, float h ) {
        if( h < 0 ) h += 1;

        if( h > 1 ) h -= 1;

        if( 6 * h < 1 ) {
            return p + ((q - p) * 6 * h);
        }

        if( 2 * h < 1 ) {
            return q;
        }

        if( 3 * h < 2 ) {
            return p + ((q - p) * 6 * ((2.0f / 3.0f) - h));
        }

        return p;
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
                double b = -(y - HALF_SIZE) / scale;

                z = f.f( new Complex( a, b ) );


                //plane.setRGB( x, y, hsvToRgb( (float) z.phase(), 1f, (float) (Math.atan( z.mod() ) * (2 / Math.PI)) ) );
                plane.setRGB( x, y, hslToRgb( (float) (z.phase() + ((2 * Math.PI) / 3)), 1f, (float) (Math.atan( z.mod() ) * 2 / Math.PI) ) );
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
