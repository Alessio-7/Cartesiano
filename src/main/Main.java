package main;

import planes.*;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JFrame {

    static int posX;
    static int posY;

    public Main( Plane plane, double speed ) {
        super();

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLayout( new BorderLayout() );

        setResizable( false );
        add( plane );
        pack();

        int space = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getWidth();

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

        int SIZE = 500;
        double scale = 100; // quanti pixel sono una unita'
        double speed = 0.01d;

        new Main( RealPlane.getSample( SIZE, scale ), speed );
        new Main( ParametricPlane.getSample( SIZE, scale ), speed );
        new Main( ComplexPlane.getSample( SIZE, scale ), speed );
        new Main( VectorPlane.getSample( SIZE, scale ), speed );
    }

}