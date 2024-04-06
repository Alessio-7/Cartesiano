package main;

import funcs.Function;
import funcs.OndaQuadra;
import planes.ComplexPlane;
import planes.Plane;
import planes.RealPlane;
import planes.VectorPlane;
import primitives.Complex;
import primitives.Vec2;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JFrame {

    public Main( Plane plane, double speed ) {
        super();

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLayout( new BorderLayout() );

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
        p.addFunzione( new OndaQuadra( 1 ) );


        return p;
    }

    public static Plane setupComplex( int SIZE, double scale ) {
        ComplexPlane p = new ComplexPlane( SIZE, scale );

        p.addFunzione( new Function<Complex>() {

            double x, y;

            @Override
            public Complex f( Complex x ) {
                return x.pow( 5 ).sum( new Complex( this.x, y ) );
            }

            @Override
            public void update( double t ) {
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
            public Vec2 f( Vec2 v ) {
                Complex z = Complex.fromVec2( v );
                return z.pow( 7 ).sum( new Complex( x, y ) ).toVec2();
            }

            @Override
            public void update( double t ) {
                x = Math.cos( t );
                y = Math.sin( t );
            }
        } );

        return p;

    }

    public static void main( String[] args ) {

        int SIZE = 1000;
        double scale = 200; // quanti pixel sono una unita'
        double speed = 0.01d;

        new Main( setupReal( SIZE, scale ), speed );
        //new Main( setupComplex( 700, scale ), speed );
        //new Main( setupVector( SIZE, scale ), speed );
    }

}