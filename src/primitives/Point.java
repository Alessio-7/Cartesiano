package primitives;

public class Point {
	public final double x, y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point negativeX() {
		return new Point(-x, y);
	}

	public Point negativeY() {
		return new Point(x, -y);
	}

	public Point negative() {
		return new Point(-x, -y);
	}

	@Override
	public String toString() {
		return "(" + x + "; " + y + ')';
	}

	public static double distance(Point p1, Point p2) {
		return Math.hypot(p2.x - p1.x, p2.y - p1.y);
	}
}
