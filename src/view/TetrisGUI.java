package view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import control.Controller;
import model.Board;

public class TetrisGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 106474944905625785L;
	
    /**
     *  A Toolkit to get the screen size. 
     */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    
    /** 
     * The Dimension of the screen.
     */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
    
    /**
     * Minimum size for the JFrame.
     */
    private static final Dimension MINIMUM_SIZE = new Dimension(375, 471);
    
    /**
     * Minimum size for the JFrame.
     */
    private static final Dimension INITIAL_SIZE = new Dimension(800, 600);
	
	private Controller control;
	private Board board;
	private TetrisBoard boardPanel;
	
	public TetrisGUI() {
		super("Tetris");
		control = new Controller();
		board = new Board();
		boardPanel = new TetrisBoard();
		this.addKeyListener(control);
		control.addObserver(board);
		board.addObserver(boardPanel);
		this.add(boardPanel);
		
		setSize(INITIAL_SIZE);
		setLocation(SCREEN_SIZE.width/2 - getWidth()/2,
				    SCREEN_SIZE.height/2 - getHeight()/2);
		this.setVisible(true);
	}

}
