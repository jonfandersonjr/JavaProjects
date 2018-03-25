/*
 * TCSS 305 - Tetris
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import model.Block;
import model.Board;

/**
 * Constructs a panel where the user will play the Tetris game.
 * 
 * @author Jon Anderson
 * @version May 18
 *
 */
public class GamePanel extends JPanel implements Observer {

    /**
     * Auto generated serial ID number.
     */
    private static final long serialVersionUID = 8522478904937779609L;
    
    /**
     * Wide of stroke for border.
     */
    private static final int STROKE_SIZE = 3;
    
    /**
     * Color of MURICA (blue).
     */
    private static final Color MURICA_BLUE = new Color(0, 82, 165);
    
    /**
     * Color of MURICA (red).
     */
    private static final Color MURICA_RED = new Color(224, 22, 43);
    
    /**
     * Size of the block.
     */
    private final int myBlockSize;
    
    /**
     * The height of the board.
     */
    private final int myBoardHeight;
    
    /**
     * The X value to draw onto.
     */
    private int myX;
    
    /**
     * The Y value to draw to.
     */
    private int myY;
    
    /**
     * My tetris pieces.
     */
    private List<Block[]> myPieces = new ArrayList<Block[]>();

    /**
     * Creates the tetris game area with default features.
     * @param theBlockSize in this game.
     * @param theBoardHeight of this board.
     */
    public GamePanel(final int theBlockSize, final int theBoardHeight) {
        super();
//        BackGroundImage background = new BackGroundImage();
//        background.setOpaque(true);
//        add(background);
        //pack();
        myBlockSize = theBlockSize;
        myBoardHeight = theBoardHeight;
        final Dimension preferredSize = new Dimension(theBlockSize * 10, theBlockSize * 21);
        setPreferredSize(preferredSize);
        setBorder(BorderFactory.createStrokeBorder(new BasicStroke(STROKE_SIZE)));
        setBackground(Color.LIGHT_GRAY);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void update(final Observable theObserv, final Object theObject) {

        if (theObserv instanceof Board) {
            if (theObject instanceof ArrayList) {
                myPieces = (ArrayList<Block[]>) theObject;
            }
            repaint();
        }
       
    }

    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        iterateLists(g2d);
    }
    
    /**
     * Goes through the coordinates provides and draws the appropriate squares.
     * @param theGraphics I will be painting.
     */
    private void iterateLists(final Graphics theGraphics) {
        for (int i = myPieces.size() - 1; i >= 0; i--) {
            final Block[] blockArrays;
            blockArrays = myPieces.get(i);
            myY = i;
            for (int j = 0; j <= blockArrays.length - 1; j++) {
                myX = j * myBlockSize;
                if (blockArrays[j] != null) {
                    determineColor(blockArrays[j], theGraphics);
                    theGraphics.setColor(Color.BLACK);
                    theGraphics.drawRect(myX, (myBoardHeight - myY) * myBlockSize, 
                                         myBlockSize, myBlockSize);
                }
            }   
        }   
    }
    
    /**
     * Determines the color for this block.
     * @param thePiece of we will check.
     * @param theGraphics to draw.
     */
    private void determineColor(final Block thePiece, final Graphics theGraphics) {
        
        switch (thePiece) {
            case I:
                theGraphics.setColor(Color.WHITE);
                theGraphics.fillRect(myX, (myBoardHeight - myY) * myBlockSize, 
                                     myBlockSize, myBlockSize);
                break;
            case O:
                theGraphics.setColor(Color.WHITE);
                theGraphics.fillRect(myX, (myBoardHeight - myY) * myBlockSize, 
                                     myBlockSize, myBlockSize);
                break;
            case J:
                theGraphics.setColor(MURICA_BLUE);
                theGraphics.fillRect(myX, (myBoardHeight - myY) * myBlockSize, 
                                                     myBlockSize, myBlockSize);
                break;
            case L:
                theGraphics.setColor(MURICA_RED);
                theGraphics.fillRect(myX, (myBoardHeight - myY) * myBlockSize, 
                                     myBlockSize, myBlockSize);
                break;
            case S:
                theGraphics.setColor(MURICA_BLUE);
                theGraphics.fillRect(myX, (myBoardHeight - myY) * myBlockSize, 
                                                     myBlockSize, myBlockSize);
                break;
            case Z:
                theGraphics.setColor(MURICA_RED);
                theGraphics.fillRect(myX, (myBoardHeight - myY) * myBlockSize, 
                                                     myBlockSize, myBlockSize);
                break;
            case T:
                theGraphics.setColor(Color.YELLOW);
                theGraphics.fillRect(myX, (myBoardHeight - myY) * myBlockSize, 
                                                     myBlockSize, myBlockSize);
                break;
            default:
                break;
        }

    }

}
