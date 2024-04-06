package planes;

import funcs.Function;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Plane<T> extends JPanel {

    protected final int SIZE;
    protected final int HALF_SIZE;
    protected final double scale;
    protected final int scaleInt;
    protected double t = 0;
    protected ArrayList<Function<T>> funzioni;

    protected Plane( int SIZE, double scale ) {
        this.SIZE = SIZE;
        this.HALF_SIZE = SIZE / 2;
        this.scale = scale;
        scaleInt = (int) scale;

        funzioni = new ArrayList<>();

    }

    @Override
    public void paint( Graphics gr ) {
        super.paint( gr );

        Graphics2D g = (Graphics2D) gr;

        g.setRenderingHint( RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY );
        g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        g.setRenderingHint( RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY );
        g.setRenderingHint( RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE );
        g.setRenderingHint( RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON );
        g.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );
        g.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
        g.setRenderingHint( RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE );

        paintChild( g );
    }

    protected abstract void update();

    protected abstract void paintChild( Graphics2D g );

    protected double pixelToCord( int p ) {
        return (p - HALF_SIZE) / scale;
    }

    protected int cordToPixel( double c ) {
        return (int) ((c * scale) - HALF_SIZE);
    }

    public void nextFrame( double speed ) {
        if( t > Double.MAX_VALUE - 300 ) {
            t = 0;
        } else {
            t += speed;
        }

        for( Function<T> f : funzioni )
            f.update( t );
        update();
        repaint();
    }

    public void addFunzione( Function<T> f ) {
        funzioni.add( f );
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension( SIZE, SIZE );
    }
}
