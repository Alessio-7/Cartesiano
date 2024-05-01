package parametrics;


import primitives.Point;

import java.awt.*;

public interface Parametric {

    default boolean display() {
        return true;
    }

    default Color getColor() {
        return Color.white;
    }

    double getX( double t );

    double getY( double t );

    double getStart();

    double getEnd();

    Point getCenter();

    default void update( double time ) {

    }
}
