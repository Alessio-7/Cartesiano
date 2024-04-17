package planes;

import funcs.Function;
import main.Utils;
import primitives.Complex;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

public class ComplexPlane extends FunctionPlane<Complex> {

    private final BufferedImage plane;
    private RenderThread[] threads;


    public ComplexPlane( int SIZE, double scale ) {
        super( SIZE, scale );
        int pixeledScale = 1;

        if( SIZE > 99 ) pixeledScale = (int) (0.005667d * SIZE + 1.533d);

        pixeledScale = 16;

        //this.scale /= pixeledScale;
        //plane = new BufferedImage( SIZE / pixeledScale, SIZE / pixeledScale, BufferedImage.TYPE_INT_RGB );
        plane = new BufferedImage( SIZE, SIZE, BufferedImage.TYPE_INT_RGB );

        setupThreads( pixeledScale );
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
        AffineTransformOp scaleOp = new AffineTransformOp( scaleInstance, AffineTransformOp.TYPE_BILINEAR );

        scaleOp.filter( plane, after );
        return after;
    }

    private void setupThreads( int nThreads ) {
        threads = new RenderThread[nThreads];

        int nRows = (int) Math.ceil( Math.sqrt( nThreads ) );
        int nCols = nThreads / nRows;

        int regionLenX = plane.getWidth() / nRows;
        int regionLenY = plane.getHeight() / nCols;

        //System.out.println( nRows + " " + nCols );

        int x = 0, y = 0;
        for( int i = 0; i < threads.length; i++ ) {

            if( x == nCols ) {
                x = 0;
                y++;
            }

            int regionX = x * regionLenX;
            int regionY = y * regionLenY;


            //System.out.print( x + ", " + y + " : " );
            threads[i] = new RenderThread( regionX, regionY, regionX + regionLenX, regionY + regionLenY );
            x++;
        }
    }

    private void color() {

        Function<Complex> f = getFirstFunction();

        Complex z;

        for( int x = 0; x < plane.getWidth(); x++ ) {
            for( int y = 0; y < plane.getHeight(); y++ ) {

                double a = pixelToCord( x, plane.getWidth() / 2 );
                double b = -pixelToCord( y, plane.getHeight() / 2 );

                z = f.f( new Complex( a, b ) );

                plane.setRGB( x, y, Utils.hslToRgb( (float) z.phaseDeg(), 1f, (float) Utils.scaleAtan( z.mod() ) ) );

            }
        }
    }

    @Override
    protected void update() {
        //color();

        for( RenderThread t : threads ) {
            t.startNew();
        }

    }

    @Override
    protected void paintChild( Graphics2D g ) {
        //g.drawImage( scalePlane(), 0, 0, null );
        g.drawImage( plane, 0, 0, null );
    }


    private class RenderThread {

        private final Runnable t;

        public RenderThread( int startRegionX, int startRegionY, int endRegionX, int endRegionY ) {

            //System.out.println( startRegionX + " - " + endRegionX + " ; " + startRegionY + " - " + endRegionY );
            t = () -> {

                Function<Complex> f = getFirstFunction();
                Complex z;

                for( int x = startRegionX; x < endRegionX; x++ ) {
                    for( int y = startRegionY; y < endRegionY; y++ ) {

                        double a = pixelToCord( x, plane.getWidth() / 2 );
                        double b = -pixelToCord( y, plane.getHeight() / 2 );

                        z = f.f( new Complex( a, b ) );

                        plane.setRGB( x, y, Utils.hslToRgb( (float) z.phaseDeg(), 1f, (float) Utils.scaleAtan( z.mod() ) ) );

                    }
                }
            };
        }

        public void startNew() {
            try {
                SwingUtilities.invokeAndWait( t );
            } catch( InterruptedException | InvocationTargetException e ) {
                e.printStackTrace();
            }
        }
    }
}
