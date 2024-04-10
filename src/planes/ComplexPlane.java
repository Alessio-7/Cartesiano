package planes;

import funcs.Function;
import main.Utils;
import primitives.Complex;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ComplexPlane extends FunctionPlane<Complex> {

    private final BufferedImage plane;


    public ComplexPlane( int SIZE, double scale ) {
        super( SIZE, scale );
        int pixeledScale = 1;

        if( SIZE > 99 )
            pixeledScale = (int) (0.005667d * SIZE + 1.533d);


        this.scale /= pixeledScale;
        plane = new BufferedImage( SIZE / pixeledScale, SIZE / pixeledScale, BufferedImage.TYPE_INT_RGB );
    }

    public static ComplexPlane getSample( int SIZE, double scale ) {
        ComplexPlane p = new ComplexPlane( SIZE, scale );

        p.addFunzione( new Function<Complex>() {

            double x, y;

            @Override
            public Complex f( Complex x ) {
                return x.pow( 2 ).sum( new Complex( this.x, y ) );
            }

            @Override
            public void update( double time ) {
                x = Math.cos( time );
                y = Math.sin( time );
            }
        } );

        return p;
    }

    private BufferedImage scalePlane() {
        int w = plane.getWidth();
        int h = plane.getHeight();

        double scaleP = ((double) SIZE) / w;
        int w2 = (int) (w * scaleP);
        int h2 = (int) (h * scaleP);
        BufferedImage after = new BufferedImage( w2, h2, BufferedImage.TYPE_INT_ARGB );
        AffineTransform scaleInstance = AffineTransform.getScaleInstance( scaleP, scaleP );
        AffineTransformOp scaleOp
                = new AffineTransformOp( scaleInstance, AffineTransformOp.TYPE_BILINEAR );

        scaleOp.filter( plane, after );
        return after;
    }

    private void grid() {

        if( functions.isEmpty() ) return;

        Function<Complex> f = functions.get( 0 );
        Complex z;

        for( int x = (SIZE % scaleInt) / 2; x < SIZE; x += scaleInt ) {
            for( int y = 0; y < SIZE; y++ ) {

                double a = (x - HALF_SIZE) / scale;
                double b = (y - HALF_SIZE) / scale;

                z = f.f( new Complex( a, b ) );

                int x1 = (int) -((z.a * scale) - HALF_SIZE);
                int y1 = (int) -((z.b * scale) - HALF_SIZE);

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

                int x1 = (int) -((z.a * scale) - HALF_SIZE);
                int y1 = (int) -((z.b * scale) - HALF_SIZE);

                if( x1 < SIZE && x1 > 0 && y1 < SIZE && y1 > 0 ) {
                    plane.setRGB( x1, y1, (b == 0 ? Color.cyan : Color.gray).getRGB() );
                }
            }
        }


    }

    private void color() {
        if( functions.isEmpty() ) return;

        Function<Complex> f = functions.get( 0 );

        Complex z;

        for( int x = 0; x < plane.getWidth(); x++ ) {
            for( int y = 0; y < plane.getHeight(); y++ ) {

                double a = pixelToCord( x, plane.getWidth() / 2 );
                double b = -pixelToCord( y, plane.getHeight() / 2 );

                z = f.f( new Complex( a, b ) );

                plane.setRGB( x, y,
                        Utils.hslToRgb(
                                (float) z.phaseDeg(),
                                1f,
                                (float) Utils.scaleAtan( z.mod() ) )
                );

            }
        }
    }

    @Override
    protected void update() {
        color();
    }

    @Override
    protected void paintChild( Graphics2D g ) {
        g.drawImage( scalePlane(), 0, 0, null );
    }
}
