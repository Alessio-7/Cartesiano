package parametrics;


import primitives.Point;

public interface Parametric {

    double getX( double t );

    double getY( double t );

    double getStart();

    double getEnd();

    Point getCenter();

    void update( double time );
}
