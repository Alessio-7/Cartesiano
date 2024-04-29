package polygons;

import funcs.Function;
import parametrics.Parametric;
import primitives.Point;
import study.Study;

public abstract class ParametricInscribed extends Polygon {

	private Parametric parametric;
	protected double d;
	protected Point point1;

	public ParametricInscribed(Parametric parametric) {
		super(true);
		this.parametric = parametric;

		// findDistance();
	}

	public abstract double getCartesianY(double x);

	// public Function<Double> getIntersectionFunction1(Point point) {
	public Function<Double> getIntersectionFunction1() {
		return x -> {
			return intersectionFunction(x, d, point1, 1);
		};
	}

	// public Function<Double> getIntersectionFunction2(Point point) {
	public Function<Double> getIntersectionFunction2() {
		return x -> {
			return intersectionFunction(x, d, point1, -1);
		};
	}

	public void findDistance() {
		findDistance(0);
	}

	public void findDistance(double t) {

		Point point = new Point(parametric.getX(t), parametric.getY(t));

		Function<Double> intersectionFunction1 = x -> {
			return intersectionFunction(x, d, point, 1);
		};

		Function<Double> intersectionFunction2 = x -> {
			return intersectionFunction(x, d, point, -1);
		};

		Function<Double> derivate1 = new Study(intersectionFunction1).derivate();

		Function<Double> derivate2 = new Study(intersectionFunction2).derivate();

		double xi = -intersectionFunction1.f(point.x) / derivate1.f(point.x);
		xi -= intersectionFunction1.f(xi) / derivate1.f(xi);
		xi -= intersectionFunction1.f(xi) / derivate1.f(xi); // newton iterations :D

		double xj = -intersectionFunction2.f(point.x) / derivate2.f(point.x);
		xj -= intersectionFunction2.f(xj) / derivate2.f(xj);
		xj -= intersectionFunction2.f(xj) / derivate2.f(xj);

		double d1 = Point.distance(point, new Point(xi, getCartesianY(xi)));
		double d2 = Point.distance(point, new Point(xj, getCartesianY(xj)));

		System.out.println(point);
		System.out.println(
				intersectionFunction(point.x, d, point, 1) + "\n" + intersectionFunction(point.x, d, point, -1));
		System.out.println();
		System.out.println(xi + "\n" + xj);
		System.out.println();
		System.out.println(d1 + "\n" + d2);

	}

	private double intersectionFunction(double x, double r, Point c, int signGamma) {
		double gamma = signGamma * getCartesianY(x);
		return Math.pow(gamma - c.y, 2) + Math.pow(x - c.x, 2) - (r * r);
	}

	private Point getNextPoint(Point point) {
		// TODO
		return new Point(0, 0);
	}

}
