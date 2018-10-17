package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class NextPiecesPanel extends JPanel implements Observer {
//    @Override
//    public void paintComponent(final Graphics theGraphics) {
//        super.paintComponent(theGraphics);
//        final Graphics2D graphic = (Graphics2D) theGraphics;
//        graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                                  RenderingHints.VALUE_ANTIALIAS_ON);
//        
//        for (final TetrisBlock tb : myTetrisBlocks) {
//            graphic.setPaint(tb.getFillColor());
//            graphic.fill(tb.getShape());
//            graphic.setPaint(tb.getLineColor());
//            graphic.setStroke(new BasicStroke(2));
//            graphic.draw(tb.getShape());
//        }
//        
//        final Font font = new Font(Font.SERIF, Font.BOLD, mySizeMulti);
//        graphic.setFont(font);
//        graphic.setPaint(Color.WHITE);
//        graphic.drawString(myString, 
//                           (int) (getSize().getWidth() / 2) - mySizeMulti * myMessageOffset,
//                           (int) getSize().getHeight() / 2);
//    }
	/**
	 * 
	 */
	private static final long serialVersionUID = -4325474754229828760L;

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
}
