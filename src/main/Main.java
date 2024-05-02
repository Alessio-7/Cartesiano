package main;

import funcs.Function;
import parametrics.Circumference;
import parametrics.Ellipse;
import parametrics.Parametric;
import planes.Plane;
import planes.RealPlane;
import polygons.ParametricInscribed;
import polygons.Polygon;
import primitives.Point;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JFrame {

    static int posX;
    static int posY;
    static ParametersPanel panel;
    static InspectPanel inspectPanel;

    static ArrayList<Object> toUpdate;

    public Main( Plane plane, double speed ) {
        super();

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLayout( new BorderLayout() );

        setResizable( false );
        add( plane );
        pack();

        int space = 690;

        if( posX == 2 ) {
            posX = 0;
            posY++;
        }

        setLocation( posX++ * getWidth() + space, posY * getHeight() + 20 );
        setVisible( true );

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                plane.nextFrame( speed );

                for( Object o : toUpdate ) {
                    if( o instanceof Function ) ((Function) o).update( plane.getTime() );
                    else if( o instanceof Parametric ) ((Parametric) o).update( plane.getTime() );
                    else if( o instanceof Polygon ) ((Polygon) o).update( plane.getTime() );
                }
            }
        };
        timer.scheduleAtFixedRate( task, 0, 17 );
        plane.nextFrame( 0 );
    }

    public static void main( String[] args ) {

        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        } catch( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e ) {
            e.printStackTrace();
        }

        panel = new ParametersPanel();
        inspectPanel = new InspectPanel();
        toUpdate = new ArrayList<>();

        int SIZE = 600;
        double scale = 70; // quanti pixel sono una unita'
        double speed = 0.01d;

        RealPlane plane = new RealPlane( SIZE, scale );
        //ComplexPlane plane2 = new ComplexPlane( SIZE, scale );


        panel.addParameter( "t", 1.54 );
        panel.addParameter( "r", 1 );
        panel.addParameter( "a", 3 );
        panel.addParameter( "b", 2 );

        inspectPanel.addInspectObject( "center" );
        inspectPanel.addInspectObject( "tpk1" );

        Ellipse e = new Ellipse( 3, 2 ) {

            @Override
            public boolean display() {
                return true;
            }

            @Override
            public void update( double time ) {
                super.update( time );
                setA( panel.getParameter( "a" ) );
                setB( panel.getParameter( "b" ) );
            }
        };

        Circumference c = new Circumference( 1 ) {

            @Override
            public boolean display() {
                return true;
            }

            @Override
            public Color getColor() {
                return Color.green;
            }

            @Override
            public void update( double time ) {
                super.update( time );
                setR( panel.getParameter( "r" ) );
                setAlpha( e.getX( panel.getParameter( "t" ) ) );
                setBeta( e.getY( panel.getParameter( "t" ) ) );

                inspectPanel.update( "center", new Point( getAlpha(), getBeta() ) );
            }
        };

        ParametricInscribed pi = new ParametricInscribed( e, 4 ) {

            double a;
            double b;

            @Override
            public void update( double time ) {
                a = e.getA();
                b = e.getB();

                tpk = panel.getParameter( "t" );

                double tpk1 = solution( panel.getParameter( "r" ) );
                inspectPanel.update( "tpk1", tpk1 );

                Point pk1 = new Point( e.getX( tpk1 ), e.getY( tpk1 ) );

                points.clear();
                points.add( new Point( e.getX( tpk ), e.getY( tpk ) ) );
                points.add( pk1 );

                /*
                double[] xs = allSolutions( panel.getParameter( "r" ) );

                Point pk1 = new Point( xs[0], gamma( xs[0] ) );
                Point pk2 = new Point( xs[1], gamma( xs[1] ) );

                inspectPanel.update( "root1", xs[0] );
                inspectPanel.update( "root2", xs[1] );
                inspectPanel.update( "gamma", gamma( pk.x ) );

                points.clear();
                points.add( pk1 );
                points.add( pk );
                points.add( pk2 );
                */
            }
        };

        plane.addParametric( e );
        plane.addParametric( c );
        plane.addPolygon( pi );

        //plane2.addFunction( x -> new Complex( e.getX( x.a ) - c.getX( x.b ), e.getY( x.a ) - c.getY( x.b ) ) );

        new Main( plane, speed );

        //new Main( RealPlane.getSample( SIZE, scale ), speed );
        //new Main( ParametricPlane.getSample( SIZE, scale ), speed );
        //new Main( ComplexPlane.getSample( SIZE, scale ), speed );
        //new Main( VectorPlane.getSample( SIZE, scale ), speed );

        panel.setVisible( true );
        inspectPanel.setVisible( true );
    }

}