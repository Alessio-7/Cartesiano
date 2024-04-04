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

    @Override
    public String toString() {
        return a + (b < 0 ? "" : "+") + b + "i";
    }
}
