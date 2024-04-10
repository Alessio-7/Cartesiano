package parametrics;

public class Ellipse extends Lissajous {

    public Ellipse( double a, double b ) {
        this( 0, 0, a, b );
    }

    public Ellipse( double alpha, double beta, double a, double b ) {
        super( alpha, beta, a, b, 1, 1 );
    }
}
