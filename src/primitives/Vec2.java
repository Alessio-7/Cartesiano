package primitives;

public class Vec2 extends Vec {

    public Vec2( double i, double j ) {
        super( i, j );
    }

    public static Vec2 up() {
        return new Vec2( 0, 1 );
    }

    public static Vec2 down() {
        return new Vec2( 0, -1 );
    }

    public static Vec2 right() {
        return new Vec2( 1, 0 );
    }

    public static Vec2 left() {
        return new Vec2( -1, 0 );
    }

    public double phaseRad() {
        return Math.atan2( j(), i() );
    }

    public double phaseDeg() {
        return Math.toDegrees( phaseRad() );
    }
}
