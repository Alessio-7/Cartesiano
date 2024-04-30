package main;

import funcs.Function;
import planes.Plane;
import planes.RealPlane;
import polygons.Polygon;
import primitives.Point;
import study.RootsFinder;

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

        // int space = Toolkit.getDefaultToolkit().getScreenSize().width / 2 -
        // getWidth();
        int space = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getWidth() / 2;

        if( posX == 2 ) {
            posX = 0;
            posY++;
        }

        setLocation( posX++ * getWidth() + space, posY * getHeight() );
        // setLocation( Toolkit.getDefaultToolkit().getScreenSize().width / 2 -
        // getWidth() / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 -
        // getHeight() / 2 );
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

        int SIZE = 600;
        double scale = 70; // quanti pixel sono una unita'
        double speed = 0.01d;

        RealPlane plane = new RealPlane( SIZE, scale );


        /*
        panel.addParameter( "t", 1.54 );
        panel.addParameter( "r", 1 );
        panel.addParameter( "a", 3 );
        panel.addParameter( "b", 2 );

        Ellipse e = new Ellipse( 3, 2 ) {


            @Override
            public void update( double time ) {
                super.update( time );
                setA( panel.getParameter( "a" ) );
                setB( panel.getParameter( "b" ) );
            }
        };

        plane.addParametric( e );

        plane.addParametric( new Circumference( 1 ) {

            @Override
            public boolean display() {
                return true
                        ;
            }

            @Override
            public void update( double time ) {
                super.update( time );
                setR( panel.getParameter( "r" ) );
                setAlpha( e.getX( panel.getParameter( "t" ) ) );
                setBeta( e.getY( panel.getParameter( "t" ) ) );
            }
        } );

        ParametricInscribed pi = new ParametricInscribed( e ) {

            double a;
            double b;

            @Override
            public double inverseGetX( double x ) {
                // System.out.println(a + " " + b);
                return Math.acos( x / a );
            }

            @Override
            public void update( double time ) {
                a = e.getA();
                b = e.getB();

                double t = panel.getParameter( "t" );

                pk = new Point( e.getX( t ), e.getY( t ) );

                double xpk1 = solution( panel.getParameter( "r" ) );

                Point pk1 = new Point( xpk1, gamma( xpk1 ) );
                //Point p = new Point( 1, 0 );

                points.clear();
                points.add( pk );

                points.add( pk1 );


            }
        };


        plane.addPolygon( pi );
        //plane.addFunction( x -> pi.polynome( x, panel.getParameter( "r" ) ) );

         */


        panel.addParameter( "i", 1, 0, 100 );
        panel.addParameter( "a", 1 );
        panel.addParameter( "b", 1 );
        panel.addParameter( "c", 1 );
        panel.addParameter( "d", 1 );
        panel.addParameter( "e", 1 );

        Function<Double> f = new Function<Double>() {

            double a, b, c, d, e;

            @Override
            public Double f( Double x ) {
                return a * Math.pow( x, 4 ) + b * Math.pow( x, 3 ) + c * Math.pow( x, 2 ) + d * x + e;
            }

            @Override
            public void update( double time ) {
                a = panel.getParameter( "a" );
                b = panel.getParameter( "b" );
                c = panel.getParameter( "c" );
                d = panel.getParameter( "d" );
                e = panel.getParameter( "e" );
            }
        };

        plane.addFunction( f );

        plane.addPolygon( new Polygon() {
            @Override
            public void update( double time ) {
                points.clear();
                double root = new RootsFinder( f ).steffen( 0, (int) panel.getParameter( "i" ) );

                points.add( new Point( 0, 0 ) );
                points.add( new Point( root, 0 ) );
            }
        } );


        new Main( plane, speed );

        // new Main( RealPlane.getSample( SIZE, scale ), speed );
        //new Main( ParametricPlane.getSample( SIZE, scale ), speed );
        //new Main( ComplexPlane.getSample( SIZE, scale ), speed );
        //new Main( VectorPlane.getSample( SIZE, scale ), speed );

        panel.setVisible( true );
    }

}