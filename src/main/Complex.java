package main;

public record Complex(double a, double b) {

    public Complex times( Complex z ) {
        return new Complex( (a * z.a) - (b * z.b), (a * z.b) + (b * z.b) );
    }

    public Complex sum( Complex z ) {
        return new Complex( a + z.a, b + z.b );
    }

    public Complex sub( Complex z ) {
        return new Complex( a - z.a, b - z.b );
    }

    public double phase() {
        return Math.toDegrees( Math.atan( b / a ) );
    }

    public double mod() {
        return Math.sqrt( Math.pow( a, 2 ) + Math.pow( b, 2 ) );
    }

    @Override
    public String toString() {
        return a + (b < 0 ? "" : "+") + b + "i";
    }
}
