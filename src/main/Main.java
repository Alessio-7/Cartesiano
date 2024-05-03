package main;

import funcs.Function;
import parametrics.Circumference;
import parametrics.Ellipse;
import parametrics.Parametric;
import planes.Plane;
import planes.RealPlane;
import planes.VectorPlane;
import polygons.ParametricInscribed;
import polygons.Polygon;
import primitives.Point;
import primitives.Vec;
import primitives.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
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

        panel.addParameter( "t", 0 );
        panel.addParameter( "r", 1 );
        panel.addParameter( "a", 3 );
        panel.addParameter( "b", 2 );

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
            }
        };


        ParametricInscribed pi = new ParametricInscribed( e, 4 ) {

            double a;
            double b;

            @Override
            public void update( double time ) {
                a = e.getA();
                b = e.getB();
                double d = panel.getParameter( "r" );
                tpk = panel.getParameter( "t" );


                double tpk1 = solution( d, tpk );
                double tpk2 = solution( d, -tpk );


                inspectPanel.update( "tpk1", tpk1 );
                inspectPanel.update( "tpk2", tpk2 );

                Point pk1 = new Point( e.getX( tpk1 ), e.getY( tpk1 ) );
                Point pk2 = new Point( e.getX( tpk2 ), e.getY( tpk2 ) );

                points.clear();
                points.add( pk2 );
                points.add( new Point( e.getX( tpk ), e.getY( tpk ) ) );
                points.add( pk1 );

            }

            @Override
            protected Function<Double> derivateParametricGetX() {
                return t -> e.getA() * Math.sin( t );
            }

            @Override
            protected Function<Double> derivateParametricGetY() {
                return t -> e.getB() * Math.cos( t );
            }
        };

        plane.addParametric( e );
        plane.addParametric( c );
        plane.addPolygon( pi );


        VectorPlane plane2 = new VectorPlane( SIZE, scale, VectorPlane.VECTOR_MODE );

        Function<Vec2> f = v -> new Vec( e.getX( v.i() ) - panel.getParameter( "r" ) * Math.cos( v.j() ) - e.getX( panel.getParameter( "t" ) ),
                e.getY( v.i() ) - panel.getParameter( "r" ) * Math.sin( v.j() ) - e.getY( panel.getParameter( "t" ) ) ).toVec2();

        plane2.addFunction( f );

        new Main( plane, speed );
        Main m = new Main( plane2, speed );
        addMouseTracker( m, plane2 );

        //new Main( RealPlane.getSample( SIZE, scale ), speed );
        //new Main( ParametricPlane.getSample( SIZE, scale ), speed );
        //new Main( ComplexPlane.getSample( SIZE, scale ), speed );
        //new Main( VectorPlane.getSample( SIZE, scale ), speed );

        panel.setVisible( true );
        inspectPanel.setVisible( true );
    }

    static void addMouseTracker( Main m, Plane plane ) {
        m.addMouseMotionListener( new MouseMotionListener() {
            @Override
            public void mouseDragged( MouseEvent e ) {
            }

            @Override
            public void mouseMoved( MouseEvent e ) {

                Vec2 v = new Vec2( plane.pixelToCord( e.getX() - 8 ), -plane.pixelToCord( e.getY() - 30 ) );

                inspectPanel.update( "mouseCords" + posX, v );
            }
        } );

    }

}