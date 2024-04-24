package planes;

import funcs.Function;

import java.util.ArrayList;

public abstract class FunctionPlane<T> extends Plane {

    protected ArrayList<Function<T>> functions;

    protected FunctionPlane( int SIZE, double scale ) {
        super( SIZE, scale );
        functions = new ArrayList<>();
    }

    @Override
    protected void updateChilds() {
        for( Function<T> f : functions )
            f.update( time );
    }

    protected Function<T> getFirstFunction() {
        return functions.get( 0 );
    }

    public void addFunction( Function<T> f ) {
        functions.add( f );
    }
}
