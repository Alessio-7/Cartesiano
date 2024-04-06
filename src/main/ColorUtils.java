package main;

import java.awt.*;

public class ColorUtils {

    public static int interpolateColor( Color color1, Color color2, double alpha ) {
        double resultRed = color1.getRed() + alpha * (color2.getRed() - color1.getRed());
        double resultGreen = color1.getGreen() + alpha * (color2.getGreen() - color1.getGreen());
        double resultBlue = color1.getBlue() + alpha * (color2.getBlue() - color1.getBlue());

        return new Color( (int) resultRed, (int) resultGreen, (int) resultBlue ).getRGB();
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

        float q;

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

}