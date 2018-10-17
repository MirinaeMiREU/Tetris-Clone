package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import javafx.scene.shape.Rectangle;

public class TetrisBoard extends JPanel implements Observer {
	private static int X_SIZE = 10;
	private static int Y_SIZE = 20;
	private static int SIZE_MULT = 25;
	
	private int xSize;
	private int ySize;
	
	private char[][] state;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4755541857335980006L;
	
	public TetrisBoard() {
		xSize = SIZE_MULT * 10;
		ySize = SIZE_MULT * 20;
	}
	
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D graphic = (Graphics2D) theGraphics;
        graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                  RenderingHints.VALUE_ANTIALIAS_ON);
        if (state != null) {
        	for (int i = 0; i < Y_SIZE; i++) {
            	for (int j = 0; j < X_SIZE; j++) {
            		switch(state[i][j]) {
            		case 'I':
            			graphic.setPaint(Color.CYAN);
            			break;
            		case 'J':
            			graphic.setPaint(Color.BLUE);
            			break;
            		case 'L':
            			graphic.setPaint(Color.ORANGE);
            			break;
            		case 'S':
            			graphic.setPaint(Color.GREEN);
            			break;
            		case 'Z':
            			graphic.setPaint(Color.RED);
            			break;
            		case 'O':
            			graphic.setPaint(Color.YELLOW);
            			break;
            		case 'T':
            			graphic.setPaint(Color.MAGENTA);
            			break;
            		default :
            			graphic.setPaint(Color.WHITE);
            			break;
            		}
            		Shape s = new Rectangle2D.Double(j*SIZE_MULT,i*SIZE_MULT,SIZE_MULT,SIZE_MULT);
            		graphic.fill(s);
            		graphic.setPaint(Color.BLACK);
            		graphic.draw(s);
            	}
            }
        }
        
        
//        for (final TetrisBlock tb : myTetrisBlocks) {
//            graphic.setPaint(tb.getFillColor());
//            graphic.fill(tb.getShape());
//            graphic.setPaint(tb.getLineColor());
//            graphic.setStroke(new BasicStroke(2));
//            graphic.draw(tb.getShape());
//        }
        
//        final Font font = new Font(Font.SERIF, Font.BOLD, mySizeMulti);
//        graphic.setFont(font);
//        graphic.setPaint(Color.WHITE);
//        graphic.drawString(myString, 
//                           (int) (getSize().getWidth() / 2) - mySizeMulti * myMessageOffset,
//                           (int) getSize().getHeight() / 2);
    }
    
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			parseData((String)arg);
		}
		repaint();
	}

	private void parseData(String data) {
		char[] chars = data.toCharArray();
		state = new char[Y_SIZE][X_SIZE];
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				state[i][j] = chars[i*(X_SIZE+1)+j];
			}
		}
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				System.out.print(state[i][j]);
			}
			System.out.println();
		}
		
	}
	
	
}
