package model;

public enum TetrisPiece {
	I(Block.I, 4, new Point(0,1), new Point(1,1), 
			      new Point(2,1), new Point(3,1)),
	
	J(Block.J, 3, new Point(0,1), new Point(1,1),
			      new Point(2,1), new Point(2,2)),
	
	L(Block.L, 3, new Point(0,1), new Point(1,1),
			      new Point(2,1), new Point(0,2)),
	
	S(Block.S, 3, new Point(1,1), new Point(2,1),
			      new Point(0,2), new Point(1,2)),
	
	Z(Block.Z, 3, new Point(0,1), new Point(1,1),
			      new Point(1,2), new Point(2,2)),
	
	O(Block.O, 2, new Point(1,1), new Point(2,1),
			      new Point(1,2), new Point(2,2)),
	
	T(Block.T, 3, new Point(0,1), new Point(1,1),
			      new Point(2,1), new Point(1,2));
	
	private Block block;
	private int length;
	private Point[] points;
	
	
	private TetrisPiece(Block b, int l, Point... pts) {
		block = b;
		length = l;
		points = pts.clone();
	}
	
	public Block getBlock() {
		return block;
	}
	
	public Point[] getPoints() {
		return points.clone();
	}
	
	public int getLength() {
		return length;
	}
	
	public static TetrisPiece getPiece(int piece) {
		TetrisPiece tp = TetrisPiece.O;
		switch(piece) {
		case 0:
			tp = TetrisPiece.I;
			break;
		case 1:
			tp = TetrisPiece.J;
			break;
		case 2:
			tp = TetrisPiece.L;
			break;
		case 3:
			tp = TetrisPiece.S;
			break;
		case 4:
			tp = TetrisPiece.Z;
			break;
		case 5:
			tp = TetrisPiece.O;
			break;
		case 6:
			tp = TetrisPiece.T;
		}
		
		return tp;
	}
}
