package parametrics;


import primitives.Point;

public interface Parametric {

    default boolean display() {
        return true;
    }

    double getX( double t );

    double getY( double t );

    double getStart();

    double getEnd();

    Point getCenter();

    default void update( double time ) {

    }
}
