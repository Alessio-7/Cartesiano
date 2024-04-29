package main;

import parametrics.Circumference;
import parametrics.Ellipse;
import planes.Plane;
import planes.RealPlane;
import polygons.ParametricInscribed;
import primitives.*;
import primitives.Point;

import javax.swing.*;

import funcs.Function;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JFrame {

	static int posX;
	static int posY;
	static ParametersPanel panel;

	public Main(Plane plane, double speed) {
		super();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		setResizable(false);
		add(plane);
		pack();

		// int space = Toolkit.getDefaultToolkit().getScreenSize().width / 2 -
		// getWidth();
		int space = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getWidth() / 2;

		if (posX == 2) {
			posX = 0;
			posY++;
		}

		setLocation(posX++ * getWidth() + space, posY * getHeight());
		// setLocation( Toolkit.getDefaultToolkit().getScreenSize().width / 2 -
		// getWidth() / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 -
		// getHeight() / 2 );
		setVisible(true);

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				plane.nextFrame(speed);
			}
		};
		timer.scheduleAtFixedRate(task, 0, 17);
		plane.nextFrame(0);
	}

	public static void main(String[] args) {

		panel = new ParametersPanel();

		int SIZE = 600;
		double scale = 50; // quanti pixel sono una unita'
		double speed = 0.01d;

		RealPlane plane = new RealPlane(SIZE, scale);
		panel.addParameter("t", 1.54);
		panel.addParameter("r", 1);
		panel.addParameter("a", 3);
		panel.addParameter("b", 2);
		// panel.addParameter( "alpha", 2 );
		// panel.addParameter( "beta", -2 );

		Ellipse e = new Ellipse(3, 2) {

			@Override
			public void update(double time) {
				super.update(time);
				setA(panel.getParameter("a"));
				setB(panel.getParameter("b"));
			}
		};

		plane.addParametric(e);

		plane.addParametric(new Circumference(1) {

			@Override
			public void update(double time) {
				super.update(time);
				setR(panel.getParameter("r"));
				setAlpha(e.getX(panel.getParameter("t")));
				setBeta(e.getY(panel.getParameter("t")));
			}
		});

		ParametricInscribed pi = new ParametricInscribed(e) {

			double a;
			double b;

			@Override
			public double getCartesianY(double x) {
				// System.out.println(a + " " + b);
				return b * Math.sqrt(1 - ((x * x) / (a * a)));
			}

			@Override
			public void update(double time) {
				a = e.getA();
				b = e.getB();
				d = panel.getParameter("r");
				point1 = new Point(e.getX(panel.getParameter("t")), e.getY(panel.getParameter("t")));

				findDistance(panel.getParameter("t"));
			}
		};

		// Point point = new Point(e.getX(panel.getParameter("t")),
		// e.getY(panel.getParameter("t")));

		plane.addFunction(pi.getIntersectionFunction1());
		plane.addFunction(pi.getIntersectionFunction2());
		plane.addPolygon(pi);

		plane.addFunction(new Function<Double>() {

			@Override
			public boolean display() {
				return false;
				// return true;
			}

			@Override
			public Double f(Double x) {
				double r = panel.getParameter("r");
				double t = panel.getParameter("t");
				double alpha = e.getX(t);
				double beta = e.getY(t);
				double a = panel.getParameter("a");
				double b = panel.getParameter("b");

				double gamma = b * Math.sqrt(1 - ((x * x) / (a * a)));

				return Math.pow(gamma - beta, 2) + Math.pow(x - alpha, 2) - (r * r);
			}
		});

		plane.addFunction(new Function<Double>() {

			@Override
			public boolean display() {
				return false;
				// return true;
			}

			@Override
			public Double f(Double x) {
				double r = panel.getParameter("r");
				double t = panel.getParameter("t");
				double alpha = e.getX(t);
				double beta = e.getY(t);
				double a = panel.getParameter("a");
				double b = panel.getParameter("b");

				double gamma = -b * Math.sqrt(1 - ((x * x) / (a * a)));

				return Math.pow(gamma - beta, 2) + Math.pow(x - alpha, 2) - (r * r);
			}
		});

		new Main(plane, speed);

		// new Main( RealPlane.getSample( SIZE, scale ), speed );
		// new Main( ParametricPlane.getSample( SIZE, scale ), speed );
		// new Main( ComplexPlane.getSample( SIZE, scale ), speed );
		// new Main( VectorPlane.getSample( SIZE, scale ), speed );

		panel.setVisible(true);
	}

}