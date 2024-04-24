package main;

import funcs.Function;
import planes.ComplexPlane;
import planes.Plane;
import primitives.Complex;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JFrame {

    static int posX;
    static int posY;
    static ParametersPanel panel;

    public Main( Plane plane, double speed ) {
        super();

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLayout( new BorderLayout() );

        setResizable( false );
        add( plane );
        pack();

        //int space = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getWidth();
        int space = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getWidth() / 2;

        if( posX == 2 ) {
            posX = 0;
            posY++;
        }

        setLocation( posX++ * getWidth() + space, posY * getHeight() );
        //setLocation( Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getWidth() / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getHeight() / 2 );
        setVisible( true );

        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                plane.nextFrame( speed );
            }
        };
        timer.scheduleAtFixedRate( task, 0, 17 );
        plane.nextFrame( 0 );
    }


    public static void main( String[] args ) {

        panel = new ParametersPanel();


        int SIZE = 1000;
        double scale = 100; // quanti pixel sono una unita'
        double speed = 0.01d;

        /*
        RealPlane plane = new RealPlane( SIZE, scale );
        panel.addParameter( "a", 3d );
        panel.addParameter( "b", 2d );
        panel.addParameter( "m", 1d );
        plane.addFunction( new Function<Double>() {
            double m, q;

            @Override
            public Double f( Double x ) {
                return m * x + q;
            }

            @Override
            public void update( double time ) {
                m = panel.getParameter( "m" );
                double a = panel.getParameter( "a" ), b = panel.getParameter( "b" );
                q = Math.sqrt( (a * a * m * m) + (b * b) );
            }
        } );
        plane.addParametric( new Ellipse( 2, 3 ) {
            @Override
            public void update( double time ) {
                setA( panel.getParameter( "a" ) );
                setB( panel.getParameter( "b" ) );
            }
        } );
        */

        panel.addParameter( "p", 2d );
        ComplexPlane plane = new ComplexPlane( SIZE, scale );
        plane.addFunction( new Function<Complex>() {
            @Override
            public Complex f( Complex x ) {
                return x.pow( panel.getParameter( "p" ) ).sum( new Complex( 1, 1 ) );
            }

            @Override
            public void update( double time ) {

            }
        } );


        new Main( plane, speed );

        //new Main( RealPlane.getSample( SIZE, scale ), speed );
        //new Main( ParametricPlane.getSample( SIZE, scale ), speed );
        //new Main( ComplexPlane.getSample( SIZE, scale ), speed );
        //new Main( VectorPlane.getSample( SIZE, scale ), speed );

        panel.setVisible( true );
    }

}