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

    public void addFunzione( Function<T> f ) {
        functions.add( f );
    }
}
