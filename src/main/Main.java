package main;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JFrame {

    int SIZE = 1000;
    int HALF_SIZE = SIZE / 2;

    public Main() {
        super();
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize( SIZE, SIZE );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLayout( new BorderLayout() );

        setLocationRelativeTo( null );
        setLocation( -1108, 4 );
        RealPlane p = new RealPlane( SIZE, 100 );
        add( p );
        pack();
        setVisible( true );

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                p.nextFrame();
            }
        };
        timer.scheduleAtFixedRate( task, 0, 10 );


        p.addFunzione( new Sin( 1, 1 ) );
    }

    public static void main( String[] args ) {
        new Main();
    }

}