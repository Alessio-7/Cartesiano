package main;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ParametersPanel extends JFrame {
    private final HashMap<String, Double> parameters;
    private final JPanel layout;

    public ParametersPanel() {
        super( "Parameters panel" );

        /*try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        } catch( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e ) {
            e.printStackTrace();
        }*/

        parameters = new HashMap<>();
        layout = new JPanel( new GridBagLayout() );

        JScrollPane scrollPane = new JScrollPane( layout );
        scrollPane.setBorder( null );
        add( scrollPane );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
    }

    public void addParameter( String name, double value ) {
        parameters.put( name, value );
        layout.add( new ParameterSlider( name ), new GridBagConstraints( 0, parameters.size() - 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
        setSize( 300, parameters.size() < 4 ? parameters.size() * 150 : 500 );
    }

    public double getParameter( String name ) {
        return parameters.get( name );
    }

    private class ParameterSlider extends JPanel {

        private ParameterSlider( String name ) {

            setLayout( new GridLayout( 2, 1 ) );
            JLabel l = new JLabel( name );
            l.setHorizontalAlignment( JLabel.CENTER );
            add( l );
            JSlider slider = new JSlider( -500, 500, (int) (parameters.get( name ) * 100) );
            add( slider );
            slider.addChangeListener( e -> {
                parameters.replace( name, (double) slider.getValue() / 100d );
            } );
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension( 250, 100 );
        }
    }
}
