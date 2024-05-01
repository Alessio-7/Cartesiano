package planes;

import javax.swing.*;
import java.awt.*;

public abstract class Plane extends JPanel {

    protected final int SIZE;
    protected final int HALF_SIZE;
    protected final int scaleInt;
    protected double scale;
    protected double time = 0;

    protected Plane( int SIZE, double scale ) {
        this.SIZE = SIZE;
        this.HALF_SIZE = SIZE / 2;
        this.scale = scale;
        scaleInt = (int) scale;
    }

    @Override
    public void paint( Graphics gr ) {
        super.paint( gr );

        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        g.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );

        /*g.setRenderingHint( RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY );
        g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        g.setRenderingHint( RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY );
        g.setRenderingHint( RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE );
        g.setRenderingHint( RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON );
        g.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );
        g.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
        g.setRenderingHint( RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE );*/

        paintChild( g );
    }

    protected abstract void update();

    protected abstract void paintChild( Graphics2D g );

    protected abstract void updateChilds();

    protected double pixelToCord( int p, int halfSize ) {
        return (p - halfSize) / scale;
    }

    protected double pixelToCord( int p ) {
        return pixelToCord( p, HALF_SIZE );
    }

    protected int cordXToPixel( double c ) {
        return (int) ((c * scale) + HALF_SIZE);
    }

    protected int cordYToPixel( double c ) {
        return (int) -((c * scale) - HALF_SIZE);
    }

    public void nextFrame( double speed ) {
        if( time > Double.MAX_VALUE - 300 ) {
            time = 0;
        } else {
            time += speed;
        }
        updateChilds();
        update();
        repaint();
    }

    public double minCord() {
        return pixelToCord( 0 );
    }

    public double maxCord() {
        return pixelToCord( SIZE );
    }

    public double cordStep() {
        return Math.abs( pixelToCord( 1 ) - pixelToCord( 0 ) );
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension( SIZE, SIZE );
    }

    public double getTime() {
        return time;
    }
}
