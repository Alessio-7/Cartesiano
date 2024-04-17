package study;

import funcs.Function;

public class Study {

    private final Function<Double> f;
    private final double h = 0.0000001d;

    public Study(Function<Double> f) {
        this.f = f;
    }

    public Function<Double> getF(){
        return f;
    }

    public Function<Double> derivate() {
        return new Function<Double>() {
            @Override
            public Double f(Double x) {
                return (f.f(x + h) - f.f(x)) / h;
            }

            @Override
            public void update(double time) {

            }

        };
    }
}
