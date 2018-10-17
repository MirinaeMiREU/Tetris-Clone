package model;

public class ActiveTetrisPiece {
	private TetrisPiece piece;
	private Point[] points;
	private Point location;
	
	public ActiveTetrisPiece(TetrisPiece tp, Point p) {
		piece = tp;
		points = tp.getPoints();
		location = p;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public Point[] getPoints() {
		Point[] pts = points.clone();
		for (int i = 0 ; i < pts.length; i++) {
			pts[i] = pts[i].transform(location);
		}
		
		return pts;
	}
	
	public TetrisPiece getPiece() {
		return piece;
	}
	
	public void rotate(int dir) {
		if (dir == 0) {
			for (int i = 0; i < points.length; i++) {
				points[i] = new Point(piece.getLength()-points[i].getY()-1, points[i].getX());
			}
		} else if (dir == 1) {
			for (int i = 0; i < points.length; i++) {
				points[i] = new Point(points[i].getY(), piece.getLength()-points[i].getX()-1);
			}
		} else {
			System.out.println("Only 1 or 0 allowed (0 for clockwise, 1 for counter-clockwise)");
		}
	}
	
	public Point[] getRotatedPoints(int dir) {
		Point[] pts = points.clone();
		if (dir == 0) {
			for (int i = 0; i < pts.length; i++) {
				pts[i] = new Point(piece.getLength()-pts[i].getY()-1, pts[i].getX());
			}
		} else if (dir == 1) {
			for (int i = 0; i < pts.length; i++) {
				pts[i] = new Point(pts[i].getY(), piece.getLength()-pts[i].getX()-1);
			}
		} else {
			System.out.println("Only 1 or 0 allowed (0 for clockwise, 1 for counter-clockwise)");
		}
		for (int i = 0; i < pts.length; i++) {
			pts[i] = pts[i].transform(location);
		}
		return pts;
	}
	
	public Point[] getTranslatedPoints(Point translatePoint) {
		Point[] pts = getPoints();
		for (int i = 0; i < pts.length; i++) {
			pts[i] = pts[i].transform(translatePoint.getX(), translatePoint.getY());
		}
		
		return pts;
	}
	
	public void freeze() {
		
	}
	
	public void wallKick(int magnitude, int dir) {
		if (magnitude >= -2 && magnitude <= 2) {
			location = location.transform(magnitude, 0);
			rotate(dir);
		}
			
	}
	
	protected void left() {
		location = location.transform(-1, 0);
	}
	
	protected void right() {
		location = location.transform(1, 0);
	}
	
	protected void down() {
		location = location.transform(0, 1);
	}
}
