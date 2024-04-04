package main;

public record Complex(double a, double b) {

    public Complex times( Complex z ) {
        return new Complex( (a * z.getA()) - (b * z.getB()), (a * z.getB()) + (b * z.getB()) );
    }

    public Complex sum( Complex z ) {
        return new Complex( a + z.getA(), b + z.getB() );
    }


    public Complex sub( Complex z ) {
        return new Complex( a - z.getA(), b - z.getB() );
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

}
