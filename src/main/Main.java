package main;

import parametrics.Ellipse;
import planes.Plane;
import planes.RealPlane;
import polygons.EllipseInscribed;

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


        RealPlane plane = new RealPlane( SIZE, scale );
        panel.addParameter( "a", 3d );
        panel.addParameter( "b", 2d );

        Ellipse e = new Ellipse( 2, 3 ) {
            /*@Override
            public double getX( double t ) {
                return 0;
            }

            @Override
            public double getY( double t ) {
                return 0;
            }

             */

            @Override
            public void update( double time ) {
                setA( panel.getParameter( "a" ) );
                setB( panel.getParameter( "b" ) );
            }
        };

        plane.addParametric( e );
        plane.addPolygon( new EllipseInscribed( e, 4 ) );

        new Main( plane, speed );

        //new Main( RealPlane.getSample( SIZE, scale ), speed );
        //new Main( ParametricPlane.getSample( SIZE, scale ), speed );
        //new Main( ComplexPlane.getSample( SIZE, scale ), speed );
        //new Main( VectorPlane.getSample( SIZE, scale ), speed );

        panel.setVisible( true );
    }

}