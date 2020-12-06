package model;

public class Moon {

	public static final int STEP_MOVE = 1;
	private double x;
	private double w;
	private double max;
	public Moon(double xx, double rr) {
		x = xx;
		w = rr;
	}
	public double getX() {
		return x;
	}
	public void setMax(double mx) {
		max = mx;
	}
	public void move() {
		x += STEP_MOVE;
		if (x>max) {
			x = -w;
		}
	}
}
