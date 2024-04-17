package planes;

import funcs.Function;
import study.Study;

import java.awt.*;

public class RealPlane extends FunctionPlane<Double> {

    private int[][] ys;

    public RealPlane(int SIZE, double scale) {
        super(SIZE, scale);
    }

    public static Plane getSample(int SIZE, double scale) {
        RealPlane p = new RealPlane(SIZE, scale);
        //p.addFunzione( new OndaQuadra( 1 ) );

        //p.addFunzione(new Sin(1d, 1d));


        Study s = new Study(new Function<Double>() {
            @Override
            public Double f(Double x) {
                return x;
            }

            @Override
            public void update(double time) {

            }
        });

        p.addFunzione(s.derivate());

        return p;
    }

    @Override
    protected void update() {
        ys = new int[functions.size()][SIZE];

        for (int x = 0; x < SIZE; x++) {
            double x1 = pixelToCord(x);
            int i = 0;
            for (Function<Double> funz : functions) {
                int y = cordYToPixel(funz.f(x1));
                ys[i][x] = y;
                i++;
            }
        }
    }

    @Override
    protected void paintChild(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, SIZE, SIZE);

        g.setStroke(new BasicStroke(1));

        g.setColor(Color.gray);
        g.drawLine(0, HALF_SIZE, SIZE, HALF_SIZE);
        for (int x = (SIZE % scaleInt) / 2; x < SIZE; x += scaleInt) {
            g.drawLine(x, HALF_SIZE - 3, x, HALF_SIZE + 3);
        }

        g.drawLine(HALF_SIZE, 0, HALF_SIZE, SIZE);
        for (int y = (SIZE % scaleInt) / 2; y < SIZE; y += scaleInt) {
            g.drawLine(HALF_SIZE - 3, y, HALF_SIZE + 3, y);
        }


        g.setStroke(new BasicStroke(2));
        g.setColor(Color.white);
        for (int f = 0; f < functions.size(); f++) {
            for (int x = 1; x < SIZE; x++) {

                int y1 = ys[f][x - 1];
                int y2 = ys[f][x];

                if (y1 < 1 || y2 < 1) continue;

                g.drawLine(x - 1, y1, x, y2);
            }
        }
    }
}
