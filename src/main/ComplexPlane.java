package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ComplexPlane extends Plane<Complex> {

    BufferedImage plane;

    public ComplexPlane( int SIZE, double scale ) {
        super( SIZE, scale );

        plane = new BufferedImage( SIZE, SIZE, BufferedImage.TYPE_INT_RGB );
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

    @Override
    void update() {

        grid();

    }

    @Override
    void paintChild( Graphics2D g ) {
        g.drawImage( plane, 0, 0, null );
    }
}
