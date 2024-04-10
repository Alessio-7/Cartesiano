package parametrics;

public class Circumference extends Ellipse {

    public Circumference( double r ) {
        this( 0, 0, r );
    }

    public Circumference( double alpha, double beta, double r ) {
        super( alpha, beta, r, r );
    }
}
