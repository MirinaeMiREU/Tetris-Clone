package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicBoolean;

public class Controller extends Observable implements KeyListener{
	private AtomicBoolean ab = new AtomicBoolean(true);
	
	private static int DEFAULT_LEFT = KeyEvent.VK_LEFT;
	private static int DEFAULT_RIGHT = KeyEvent.VK_RIGHT;
	private static int DEFAULT_DOWN = KeyEvent.VK_DOWN;
	private static int DEFAULT_HOLD = KeyEvent.VK_C;
	private static int DEFAULT_CLOCKWISE = KeyEvent.VK_UP;
	private static int DEFAULT_COUNTER_CLOCKWISE = KeyEvent.VK_Z;
	private static int DEFAULT_DROP = KeyEvent.VK_SPACE;
	private static int DEFAULT_NEW_GAME = KeyEvent.VK_N;
	private static int DEFAULT_PAUSE = KeyEvent.VK_P;
	
	private int left;
	private int right;
	private int down;
	private int hold;
	private int clockwise;
	private int counterClockwise;
	private int drop;
	private int newGame;
	private int pause;
	
	public Controller() {
		super();
		left = DEFAULT_LEFT;
		right = DEFAULT_RIGHT;
		down = DEFAULT_DOWN;
		hold = DEFAULT_HOLD;
		clockwise = DEFAULT_CLOCKWISE;
		counterClockwise = DEFAULT_COUNTER_CLOCKWISE;
		drop = DEFAULT_DROP;
		newGame = DEFAULT_NEW_GAME;
		pause = DEFAULT_PAUSE;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (ab.get()) {
			setChanged();
			if (e.getKeyCode() == left) {
				notifyObservers("left");
			} else if (e.getKeyCode() == right) {
				notifyObservers("right");
			} else if (e.getKeyCode() == down) {
				notifyObservers("down");
			} else if (e.getKeyCode() == hold) {
				notifyObservers("hold");
			} else if (e.getKeyCode() == clockwise) {
				notifyObservers("clock");
			} else if (e.getKeyCode() == counterClockwise) {
				notifyObservers("counter");
			} else if (e.getKeyCode() == drop) {
				notifyObservers("drop");
			} else if (e.getKeyCode() == newGame) {
				notifyObservers("new game");
			} else if (e.getKeyCode() == pause) {
				notifyObservers("pause");
			} 
		}
		// TODO Auto-generated method stub
		ab.set(false);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		ab.set(true);
	}

}
