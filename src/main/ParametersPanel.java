package main;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ParametersPanel extends JFrame {
    private final HashMap<String, Double> parameters;
    private final JPanel layout;

    public ParametersPanel() {
        super( "Parameters panel" );

        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        } catch( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e ) {
            e.printStackTrace();
        }

        setMinimumSize( new Dimension( 350, 200 ) );
        parameters = new HashMap<>();
        layout = new JPanel( new GridBagLayout() );

        JScrollPane scrollPane = new JScrollPane( layout );
        scrollPane.setBorder( null );
        add( scrollPane );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
    }

    private GridBagConstraints gbc( int x, int y, int width ) {
        return gbc( x, y, width, 0 );
    }

    private GridBagConstraints gbc( int x, int y, int width, int insetBottom ) {
        return new GridBagConstraints( x, y, width, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 0, 0, insetBottom, 0 ), 0, 0 );
    }

    public void addParameter( String name, double value ) {
        addParameter( name, value, -10d, 10d );
    }

    public void addParameter( String name, double value, double min, double max ) {
        parameters.put( name, value );
        layout.add( new ParameterSlider( name, min, max ), gbc( 0, parameters.size() - 1, 1, 2 ) );
        //setSize( 300, parameters.size() < 4 ? parameters.size() * 150 : 500 );
        pack();
    }

    public double getParameter( String name ) {
        return parameters.get( name );
    }

    private class ParameterSlider extends JPanel {
        private final JSpinner spinner, minSelector, maxSelector;
        private final String name;
        private final JSlider slider;
        private double max, min;

        private ParameterSlider( String name, double min, double max ) {
            this.name = name;
            this.max = max;
            this.min = min;

            setBorder( BorderFactory.createMatteBorder( 0, 0, 2, 0, Color.lightGray ) );
            setLayout( new GridBagLayout() );

            JLabel l = new JLabel( name );
            l.setHorizontalAlignment( JLabel.CENTER );
            add( l, gbc( 0, 0, 1 ) );

            spinner = new JSpinner( new SpinnerNumberModel( (double) parameters.get( name ), -10000, 10000, 0.1d ) );
            spinner.addChangeListener( e -> updateValueSpinner() );
            add( spinner, gbc( 1, 0, 1 ) );

            slider = new JSlider( (int) (min * 100), (int) (max * 100), (int) (parameters.get( name ) * 100) );
            slider.addChangeListener( e -> updateValueSlider() );
            add( slider, gbc( 0, 1, 2 ) );

            minSelector = new JSpinner( new SpinnerNumberModel( min, -10000, 10000, 1d ) );
            minSelector.addChangeListener( e -> updateMin() );
            add( minSelector, gbc( 0, 2, 1 ) );

            maxSelector = new JSpinner( new SpinnerNumberModel( max, -10000, 10000, 1d ) );
            maxSelector.addChangeListener( e -> updateMax() );
            add( maxSelector, gbc( 1, 2, 1 ) );
        }

        private void updateMin() {
            min = (double) minSelector.getValue();
            slider.setMinimum( (int) min * 100 );
        }

        private void updateMax() {
            max = (double) maxSelector.getValue();
            slider.setMaximum( (int) max * 100 );
        }

        private void updateValueSpinner() {
            parameters.replace( name, (Double) spinner.getValue() );
            slider.setValue( (int) (((double) spinner.getValue()) * 100d) );
        }

        private void updateValueSlider() {
            parameters.replace( name, (double) slider.getValue() / 100d );
            spinner.setValue( slider.getValue() / 100d );
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension( 300, 100 );
        }
    }
}
