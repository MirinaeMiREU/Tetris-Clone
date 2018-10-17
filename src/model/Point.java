package model;
/**
 * Point used in the position within the Tetris board.
 * 0,0 is the upper left corner.
 * @author Peter Bae
 * @version 1.0
 */
public class Point {
	private int xPos;
	private int yPos;
	
	public Point(int x, int y) {
		xPos = x;
		yPos = y;
	}
	
	public Point(Point p) {
		this(p.xPos, p.yPos);
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
	public Point transform(int x, int y) {
		return new Point(xPos + x, yPos + y);
	}
	
	public Point transform(Point p) {
		return transform(p.xPos, p.yPos);
	}
	
	public String toString() {
		return String.format("%d, %d", xPos, yPos);
	}
}
