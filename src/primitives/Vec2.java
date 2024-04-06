package primitives;

public class Vec2 {

    public final double a;
    public final double b;

    public Vec2( double a, double b ) {
        this.a = a;
        this.b = b;
    }

    public static Vec2 up() {
        return new Vec2( 0, 1 );
    }

    public static Vec2 down() {
        return new Vec2( 0, -1 );
    }

    public static Vec2 raght() {
        return new Vec2( 1, 0 );
    }

    public static Vec2 left() {
        return new Vec2( -1, 0 );
    }

    public Vec2 sum( Vec2 v ) {
        return new Vec2( a + v.a, b + v.b );
    }

    public Vec2 sub( Vec2 v ) {
        return new Vec2( a - v.a, b - v.b );
    }

    public Vec2 scale( double scalar ) {
        return new Vec2( a * scalar, b * scalar );
    }

    public double dot( Vec2 v ) {
        return a * v.a + b * v.b;
    }

    public double mod() {
        return Math.sqrt( a * a + b * b );
    }

    public double phaseRad() {
        return Math.atan2( b, a );
    }

    public double phaseDeg() {
        return Math.toDegrees( phaseRad() );
    }

    @Override
    public String toString() {
        return "[ " + a + ", " + b + "]";
    }
}
