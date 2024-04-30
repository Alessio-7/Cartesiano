package study;

import funcs.Function;
import planes.RealPlane;

public class Study {

    private final Function<Double> f;
    private final RealPlane p;
    private final double h = 0.0000001d;

    public Study( Function<Double> f ) {
        this.p = null;
        this.f = f;
    }

    public Study( RealPlane p, Function<Double> f ) {
        this.p = p;
        this.f = f;
    }

    public Function<Double> getF() {
        return f;
    }

    public Function<Double> derivate() {
        return new Function<Double>() {
            @Override
            public Double f( Double x ) {
                return (f.f( x + h ) - f.f( x )) / h;
            }

            @Override
            public void update( double time ) {
                f.update( time );
            }
        };
    }

    private Function<Double> integrate( double c ) {
        return new Function<Double>() {

            double a;

            @Override
            public Double f( Double x ) {

                a += f.f( x ) * p.cordStep();

                return a + c;
            }

            @Override
            public void update( double time ) {
                f.update( time );
                a = 0;
            }

        };
    }

    public Function<Double> integrate() {
        double c = 0;

        for( double step = p.minCord(); step < 0; step += p.cordStep() ) {
            c += f.f( step ) * p.cordStep();
        }

        return integrate( -c );
    }

    public Function<Double> integrateCentered() {

        double c = 0, min = 0, max = 0;

        for( double step = p.minCord(); step < p.maxCord(); step += p.cordStep() ) {
            c += f.f( step ) * p.cordStep();

            if( c < min )
                min = c;
            if( c > max )
                max = c;
        }

        System.out.println( max + " " + min );

        double finalC = (max - min) / 2 * (min == 0 ? -1 : 1);

        return integrate( finalC );
    }
}
