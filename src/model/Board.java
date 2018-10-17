package model;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.Random;

import javax.swing.Timer;

import org.omg.CosNaming.IstringHelper;

import control.Controller;

public class Board extends Observable implements Observer {
	private static int SINGLE = 100;
	private static int DOUBLE = 300;
	private static int TRIPLE = 600;
	private static int TETRIS = 1000;
	
	private static int DEFAULT_BUFFER = 14;
	private static int NUM_OF_PIECES = 7;
	private static int DEFAULT_X = 10;
	private static int DEFAULT_Y = 20;
	private static int SHUFFLE_AMOUNT = 4;
	private static int LINES_PER_LEVEL = 5;
	private static int NUM_OF_LEVELS = 21;
	private static int INITIAL_DELAY = 1000;
	private static Random RAND = new Random();
	
	private List<Block[]> state;
	private Queue<TetrisPiece> nextPieces;
	private TetrisPiece heldPiece;
	private boolean usedHold;
	private ActiveTetrisPiece activePiece;
	private int xSize;
	private int ySize;
	private int mode;
	private Timer timer;
	private boolean isPlaying;
	private boolean isPaused;
	private boolean isOver;
	
	private int score;
	private int level;
	private int linesCleared;
	private int[] linesPerLevel;
	
	
	public Board() {
		state = new LinkedList<Block[]>();
		nextPieces = new LinkedList<TetrisPiece>();
		xSize = DEFAULT_X;
		ySize = DEFAULT_Y;
		usedHold = false;
		mode = 0;
		setup();
	}
	
	public int getX() {
		return xSize;
	}
	
	public int getY() {
		return ySize;
	}
	
	private void command(String command) {
		setChanged();
		if (isPlaying && !isPaused) {
			switch (command) {
				case "left": // left
					left();
					break;
				case "right": // right
					right();
					break;
				case "down": // down
					down();
					break;
				case "hold": // hold
					hold();
					break;
				case "clock": // clockwise turn
					rotate(0);
					break;
				case "counter": // counter clockwise turn
					rotate(1);
					break;
				case "drop": // drop
					drop();
					break;
				case "pause": // pause
					pauseGame();
			}
//			System.out.println(this);
//			System.out.println("level: " + level + " score: " + score + " lines: " + linesCleared);
		} else {
			switch(command) {
				case "new game": // start new game
					newGame();
					break;
				case "pause": // resume
					resumeGame();
					break;
			}
		}
	}
	
	public void rotate(int dir) {
		Point[] pts = activePiece.getRotatedPoints(dir);
		if (isLegal(pts)) {
			activePiece.rotate(dir);
			notifyObservers(toString());
		} else {
			attemptWallkick(pts, dir);
		}
	}
	
	private void left() {
		if (isLegal(activePiece.getTranslatedPoints(new Point(-1, 0)))) {
			activePiece.left();
			System.out.println("left");
			notifyObservers(toString());
		}
	}
	
	private void right() {
		if (isLegal(activePiece.getTranslatedPoints(new Point(1, 0)))) {
			activePiece.right();
			System.out.println("right");
			notifyObservers(toString());
		}
	}
	private void down() {
		if (isLegal(activePiece.getTranslatedPoints(new Point(0, 1)))) {
			activePiece.down();
			System.out.println("down");
		} else {
			freeze();
		}
		notifyObservers(toString());
	}
	private void hold() {
		if (!usedHold) {
			TetrisPiece temp = activePiece.getPiece();
			if (heldPiece == null) {
				heldPiece = temp;
				getNextPiece();
			} else {
				activePiece = new ActiveTetrisPiece(heldPiece, 
													new Point((int)Math.ceil((double)xSize/2)-2,-4));
				heldPiece = temp;
			}
				
			System.out.println("held: " + heldPiece);
			usedHold = true;
		}
	}
	
	private void drop() {
		while(isLegal(activePiece.getTranslatedPoints(new Point(0, 1)))) {
			activePiece.down();
		}
		down();
		System.out.println("drop");
	}
	
	private void freeze() {
		Point[] pts = activePiece.getPoints();
		Block b = activePiece.getPiece().getBlock();
		for (Point p : pts) {
			try {
			state.get(p.getY())[p.getX()] = b;
			} catch (IndexOutOfBoundsException e) {
				endGame();
			}
		}
		checkClear();
		getNextPiece();
		usedHold = false;
	}
	
	private void checkClear() {
		int clearedLines = 0;
		for (int i = 0; i < state.size(); i++) {
			boolean full = true;
			Block[] blks = state.get(i);
			for (int j = 0; j < blks.length && full; j++) {
				if (blks[j] == null) {
					full = false;
				}
			}
			if (full) {
				state.remove(blks);
				state.add(0, new Block[xSize]);
				clearedLines++;
			}
		}
		addScore(clearedLines);
		linesCleared += clearedLines;
		level = calcLevel();
		timer.setDelay(calcSpeed());
	}
	
	private void addScore(int lines) {
		switch(lines) {
			case 1:
				score += SINGLE * level;
				break;
			case 2:
				score += DOUBLE * level;
				break;
			case 3:
				score += TRIPLE * level;
				break;
			case 4: 
				score += TETRIS * level;
				break;
		}
	}
	
	private int calcLevel() {
		int lines = linesCleared;
		int lvl = 0;
		while (lines >= 0) {
			lines -= linesPerLevel[lvl];
			lvl++;
		}
		
		return lvl;
	}
	
	private int calcSpeed() {
		int delay = INITIAL_DELAY;
		if (level < 6) {
			delay -= level * 100;
		} else if (level < 11) {
			delay -= 250 + level * 50;
		} else if (level < 16) {
			delay -= 500 + level * 25;
		} else {
			delay -= 800 + level * 5;
		}
		
		return delay;
	}
	
	private void getNextPiece() {
		activePiece = new ActiveTetrisPiece(nextPieces.poll(), 
											new Point((int)Math.ceil((double)xSize/2)-2,-4));
		if (nextPieces.size() < DEFAULT_BUFFER) {
			nextPieces.addAll(shuffle(1));
		}
	}
	
	private void newGame() {
		state.clear();
		for (int i = 0; i < DEFAULT_Y; i++) {
			state.add(new Block[DEFAULT_X]);
		}
		
		switch (mode) {
			case 0:
				nextPieces = shuffle(3);
				break;
			case 1:
				nextPieces = new LinkedList<TetrisPiece>();
				nextPieces.add(TetrisPiece.I);
		}
		getNextPiece();
		heldPiece = null;
		usedHold = false;

		level = 1;
		score = 0;
		linesCleared = 0;
		
		isPlaying = true;
		isPaused = false;
		isOver = false;
		timer.setDelay(INITIAL_DELAY);
		timer.start();
	}
	
	private void pauseGame() {
		isPaused = true;
		timer.stop();
	}
	
	private  void resumeGame() {
		isPaused = false;
		timer.restart();
	}
	
	private void endGame() {
		isPlaying = false;
		isOver = true;
		timer.stop();
	}
	
	private void attemptWallkick(Point[] points, int dir) {
		int adjust = 0;
		for (Point p : points) {
			int x = p.getX();
			if (x < 0 && x < adjust) {
				adjust = -x;
			} else if (x >= xSize && x - xSize + 1 > adjust) {
				adjust = xSize - x - 1;
			}
		}
		// TODO: gotta see if the adjusted points are legal first
		Point[] pts = activePiece.getRotatedPoints(dir);
		for (int i = 0; i < pts.length; i++) {
			pts[i] = pts[i].transform(adjust, 0);
		}
		if (isLegal(pts)) {
			activePiece.wallKick(adjust, dir);
			notifyObservers(toString());
		}
	}
	private boolean isLegal(Point[] points) {
		for (Point p : points) {
			if (p.getX() < 0 || p.getX() >= xSize ||
				p.getY() >= ySize) {
				return false;
			}
			if (p.getY() >= 0 && state.get(p.getY())[p.getX()] != null) {
				return false;
			}
		}
		
		return true;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < ySize; i++) {
			for (int j = 0; j < xSize; j++) {
				boolean is = false;
				for (Point p : activePiece.getPoints()) {
					if (p.getY() == i && p.getX() == j) {
						is = true;
					}
				}
				if (is) {
					for (Point p : activePiece.getPoints()) {
						if (p.getY() == i && p.getX() == j) {
							sb.append(activePiece.getPiece().getBlock());
						}
					}
				} else if (state.get(i)[j] == null) {
					sb.append(" ");
				} else {
					sb.append(state.get(i)[j]);
				}
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}

	@Override
	public void update(Observable o, Object arg) {
		command((String)arg);
	}
	
	private void setup() {
		for (int i = 0; i < DEFAULT_Y; i++) {
			state.add(new Block[DEFAULT_X]);
		}
		timer = new Timer(1000, (ActionEvent ae)->
		{
			command("down");
		});
		linesPerLevel = new int[NUM_OF_LEVELS];
		for (int i = 0; i < NUM_OF_LEVELS; i++) {
			linesPerLevel[i] = LINES_PER_LEVEL;
		}
		isPlaying = false;
		isPaused = false;
		isOver = true;
	}
	
	private Queue<TetrisPiece> shuffle(int times) {
		Queue<TetrisPiece> queue = new LinkedList<TetrisPiece>();
		for (int i = 0; i < times; i++) {
			int[] set = new int[NUM_OF_PIECES];
			for (int j = 0; j < set.length; j++) {
				set[j] = j;
			}
			for (int j = 0; j < SHUFFLE_AMOUNT; j++) {
				int num1 = RAND.nextInt(7);
				int num2 = RAND.nextInt(7);
				while (num1 == num2) {
					num2 = RAND.nextInt(7);
				}
				int temp = set[num1];
				set[num1] = set[num2];
				set[num2] = temp;
			}
			for (int j = 0; j < set.length; j++) {
				queue.add(TetrisPiece.getPiece(set[j]));
			}
		}
		return queue;
	}
}
