package main;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class InspectPanel extends JFrame {
    private final JPanel layout;
    //private final HashMap<String, InspectObject> inspectObjects;
    private final HashMap<String, InspectComponent> inspectComponents;

    public InspectPanel() {
        super( "Inspect panel" );

        setLocation( 350, 20 );

        setMinimumSize( new Dimension( 350, 200 ) );
        inspectComponents = new HashMap<>();
        layout = new JPanel( new GridBagLayout() );

        JScrollPane scrollPane = new JScrollPane( layout );
        scrollPane.setBorder( null );
        add( scrollPane );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
    }

    private GridBagConstraints gbc( int x, int y ) {
        return gbc( x, y, 1, 0 );
    }

    private GridBagConstraints gbc( int x, int y, int width, int insetBottom ) {
        return new GridBagConstraints( x, y, width, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 0, 5, insetBottom, 5 ), 0, 0 );
    }

    public void addInspectObject( String name ) {

        InspectComponent component = new InspectComponent( name );

        inspectComponents.put( name, component );
        layout.add( component, gbc( 0, inspectComponents.size() - 1, 1, 2 ) );

        pack();
    }

    public void update( String name, Object value ) {
        inspectComponents.get( name ).update( value );
    }

    private class InspectComponent extends JPanel {

        private JLabel label;

        private InspectComponent( String name ) {
            super( new GridBagLayout() );

            setMinimumSize( new Dimension( 0, 125 ) );
            setBorder( BorderFactory.createMatteBorder( 0, 0, 2, 0, Color.lightGray ) );

            label = new JLabel( name );
            label.setHorizontalTextPosition( JLabel.CENTER );
            add( label, gbc( 0, 0 ) );

            label = new JLabel( "" );
            add( label, gbc( 1, 0 ) );
        }

        private void update( Object value ) {
            label.setText( value.toString() );
            repaint();
        }
    }
}
