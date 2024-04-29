package main;

import parametrics.Circumference;
import parametrics.Ellipse;
import planes.Plane;
import planes.RealPlane;

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
        double scale = 50; // quanti pixel sono una unita'
        double speed = 0.01d;


        RealPlane plane = new RealPlane( SIZE, scale );
        panel.addParameter( "t", 0 );
        panel.addParameter( "r", 1 );
        panel.addParameter( "a", 3 );
        panel.addParameter( "b", 2 );
        //panel.addParameter( "alpha", 2 );
        //panel.addParameter( "beta", -2 );

        Ellipse e = new Ellipse( 0, 0 ) {

            @Override
            public void update( double time ) {
                super.update( time );
                setA( panel.getParameter( "a" ) );
                setB( panel.getParameter( "b" ) );
            }
        };

        plane.addParametric( e );

        plane.addParametric( new Circumference( 0 ) {

            @Override
            public void update( double time ) {
                super.update( time );
                setR( panel.getParameter( "r" ) );
                setAlpha( e.getX( panel.getParameter( "t" ) ) );
                setBeta( e.getY( panel.getParameter( "t" ) ) );
            }
        } );


        plane.addFunction( x -> {
            double r = panel.getParameter( "r" );
            double t = panel.getParameter( "t" );
            double alpha = e.getX( t );
            double beta = e.getY( t );
            double a = panel.getParameter( "a" );
            double b = panel.getParameter( "b" );

            //double gamma = b * Math.sin( Math.acos( x / a ) );
            double gamma = b * Math.sqrt( 1 - ((x * x) / (a * a)) );

            return Math.pow( gamma - beta, 2 ) + Math.pow( x - alpha, 2 ) - (r * r);
        } );

        plane.addFunction( x -> {
            double r = panel.getParameter( "r" );
            double t = panel.getParameter( "t" );
            double alpha = e.getX( t );
            double beta = e.getY( t );
            double a = panel.getParameter( "a" );
            double b = panel.getParameter( "b" );

            //double gamma = -b * Math.sin( Math.acos( x / a ) );
            double gamma = -b * Math.sqrt( 1 - ((x * x) / (a * a)) );

            return Math.pow( gamma - beta, 2 ) + Math.pow( x - alpha, 2 ) - (r * r);
        } );


        new Main( plane, speed );

        //new Main( RealPlane.getSample( SIZE, scale ), speed );
        //new Main( ParametricPlane.getSample( SIZE, scale ), speed );
        //new Main( ComplexPlane.getSample( SIZE, scale ), speed );
        //new Main( VectorPlane.getSample( SIZE, scale ), speed );

        panel.setVisible( true );
    }

}