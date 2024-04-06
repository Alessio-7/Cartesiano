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
        //setUndecorated( true );
        setResizable( false );
        add( plane );
        pack();
        setLocation( Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getWidth() / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getHeight() / 2 );
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

    public static Plane setupReal( int SIZE, double scale ) {
        RealPlane p = new RealPlane( SIZE, scale );
        //p.addFunzione( new Sin( 1, 1 ) );
        //p.addFunzione( new OndaQuadra( 0.2 ) );

        //Particle prt = new Particle( 3.14, 16, 28 );

        //p.addFunzione( prt );
        //p.addFunzione( prt.getImaginaryPart() );

        p.addFunzione( new OndaQuadra( 1 ) );


        return p;
    }

    public static Plane setupComplex( int SIZE, double scale ) {
        ComplexPlane p = new ComplexPlane( SIZE, scale );

        //p.addFunzione( new Mandelbrot( 1000, -5 ) );
        p.addFunzione( new Function<Complex>() {

            double x, y;

            @Override
            Complex f( Complex x ) {
                return x.pow( 5 ).sum( new Complex( this.x, y ) );
                //return Complex.exp( x );
            }

            @Override
            void update( double t ) {
                x = Math.cos( t );
                y = Math.sin( t );
            }
        } );

        return p;
    }


    public static Plane setupVector( int SIZE, double scale ) {

        VectorPlane p = new VectorPlane( SIZE, scale );

        p.addFunzione( new Function<Vec2>() {

            double x, y;

            @Override
            Vec2 f( Vec2 v ) {
                Complex z = Complex.fromVec2( v );
                return z.pow( 7 ).sum( new Complex( x, y ) ).toVec2();
            }

            @Override
            void update( double t ) {
                x = Math.cos( t );
                y = Math.sin( t );
            }
        } );

        return p;

    }

    public static void main( String[] args ) {

        int SIZE = 1000;
        double scale = 200; // quanti pixel sono una unita'
        double speed = 0.1d;

        //new Main( setupReal( SIZE, scale ), speed );
        //new Main( setupComplex( 700, scale ), speed );
        new Main( setupVector( SIZE, scale ), speed );

        VectorPlane p = new VectorPlane( 0, 0 );
    }

}