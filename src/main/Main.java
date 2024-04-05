package main;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JFrame {

    public Main( Plane plane, double speed ) {
        super();
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setSize( plane.SIZE, plane.SIZE );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLayout( new BorderLayout() );

        //setLocationRelativeTo( null );
        //setLocation( -1108, 4 );
        setLocation( 100, 10 );
        add( plane );
        pack();
        setVisible( true );

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                plane.nextFrame( speed );
            }
        };
        timer.scheduleAtFixedRate( task, 0, 10 );
    }

    public static void main( String[] args ) {

        int SIZE = 1000;
        double scale = 100; // quanti pixel sono una unita'
        double speed = 0.1d;

        //RealPlane p = new RealPlane( SIZE, scale );
        //p.addFunzione( new Sin( 1, 1 ) );
        //p.addFunzione( new OndaQuadra( 0.2 ) );

        System.out.println( new Complex( -1, 1 ).timesIm( 1 ) );

        ComplexPlane p = new ComplexPlane( SIZE, scale );

        p.addFunzione( new Function<Complex>() {

            double v;

            @Override
            Complex f( Complex x ) {
                return x.times( x ).times( x ).sum( new Complex( 1, 0 ) );
                //System.out.println( tempo );
                //return x.times( new Complex( 5, 2 ) );
            }

            @Override
            void update( double t ) {
                v = Math.atan( t );
            }
        } );

        new Main( p, speed );
    }

}