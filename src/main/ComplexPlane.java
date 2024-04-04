package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ComplexPlane extends Plane<Complex> {

    BufferedImage plane;

    public ComplexPlane( int SIZE, double scale ) {
        super( SIZE, scale );

        plane = new BufferedImage( SIZE, SIZE, BufferedImage.TYPE_INT_RGB );
    }

    @Override
    void update() {

        if( funzioni.isEmpty() )
            return;

        Function<Complex> f = funzioni.get( 0 );

        for( int x = 0; x < SIZE; x++ ) {
            for( int y = 0; y < SIZE; y++ ) {

                double a = (x - HALF_SIZE) / scale;
                double b = (y - HALF_SIZE) / scale;


            }
        }

    }

    @Override
    void paintChild( Graphics2D g ) {
        g.drawImage( plane, 0, 0, null );
    }
}
