/*
 * TCSS 305 - Tetris
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import model.Block;
import model.Point;
import model.TetrisPiece;

/**
 * Class to display the next piece in a panel.
 * 
 * @author Jon Anderson
 * @version May 26
 */
public class NextPiecePanel extends JPanel implements Observer {

    /**
     * Auto generated serial ID number.
     */
    private static final long serialVersionUID = -3254087614079880441L;

    /**
     * Modifier for the X value of block size.
     */
    private static final int X_MOD = 3;
    /**
     * Modifier for Y value of block size.
     */
    private static final double Y_MOD = 2.5;

    /**
     * Modifier for placing O blocks in the panel correctly.
     */
    private static final double O_MOD = 2.2;

    /**
     * Modifier for adjusting Y-Coordinate value in panel.
     */
    private static final int Y_COORD = 7;
    
    /**
     * Color of MURICA (red).
     */
    private static final Color MURICA_RED = new Color(224, 22, 43);
    
    /**
     * Color of MURICA (blue).
     */
    private static final Color MURICA_BLUE = new Color(0, 82, 165);

    /**
     * Checks to see if the object is an I block.
     */
    private boolean myI;

    /**
     * Checks to see if the object is an O block.
     */
    private boolean myO;

    /**
     * Stores the points for a given block.
     */
    private Point[] myDrawPoint;

    /**
     * Determines if the user is playing the game.
     */
    private boolean myPlaying;

    /**
     * The block size for this game.
     */
    private final int myBlockSize;

    /**
     * The color of my block.
     */
    private Color myBlockColor;

    /**
     * Creates a next piece panel which will display the appropriate next piece in the
     * center of the panel.
     * 
     * @param theBlockSize of this game.
     */
    public NextPiecePanel(final int theBlockSize) {
        super();
        myBlockSize = theBlockSize;
        setBorder(BorderFactory.createStrokeBorder(new BasicStroke(X_MOD)));
    }

    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        if (myPlaying) {
            for (final Point p : myDrawPoint) {
                if (myO) {
                    g2d.setPaint(myBlockColor);
                    theGraphics.fillRect((p.x() * myBlockSize) + (int) (O_MOD * myBlockSize),
                                 (Y_COORD - p.y()) * myBlockSize - (int) (Y_MOD * myBlockSize),
                                         myBlockSize, myBlockSize);
                    g2d.setPaint(Color.BLACK);
                    theGraphics.drawRect((p.x() * myBlockSize) + (int) (O_MOD * myBlockSize),
                                 (Y_COORD - p.y()) * myBlockSize - (int) (Y_MOD * myBlockSize),
                                         myBlockSize, myBlockSize);
                } else if (myI) {
                    g2d.setPaint(myBlockColor);
                    theGraphics.fillRect((p.x() * myBlockSize) + (int) (Y_MOD * myBlockSize),
                                 (Y_COORD - p.y()) * myBlockSize - (int) (2.0 * myBlockSize),
                                         myBlockSize, myBlockSize);
                    g2d.setPaint(Color.BLACK);
                    theGraphics.drawRect((p.x() * myBlockSize) + (int) (Y_MOD * myBlockSize),
                                 (Y_COORD - p.y()) * myBlockSize - (int) (2.0 * myBlockSize),
                                         myBlockSize, myBlockSize);
                } else {
                    g2d.setPaint(myBlockColor);
                    theGraphics.fillRect((p.x() * myBlockSize) + (X_MOD * myBlockSize),
                                 (Y_COORD - p.y()) * myBlockSize - (int) (Y_MOD * myBlockSize),
                                         myBlockSize, myBlockSize);
                    g2d.setPaint(Color.BLACK);
                    theGraphics.drawRect((p.x() * myBlockSize) + (X_MOD * myBlockSize),
                                 (Y_COORD - p.y()) * myBlockSize - (int) (Y_MOD * myBlockSize),
                                         myBlockSize, myBlockSize);
                }
            }
        }
    }

    @Override
    public void update(final Observable theObserv, final Object theObject) {
        if (theObject instanceof TetrisPiece) {
            determineColor(((TetrisPiece) theObject).getBlock());
            myDrawPoint = ((TetrisPiece) theObject).getPoints();
            if (((TetrisPiece) theObject).getBlock() == Block.I) {
                myI = true;
                myO = false;
            } else if (((TetrisPiece) theObject).getBlock() == Block.O) {
                myO = true;
                myI = true;
            }
            myPlaying = true;
            repaint();
        }
    }

    /**
     * Determines the color of the block.
     * @param theBlock we will color.
     */
    private void determineColor(final Block theBlock) {
        switch (theBlock) {
            case I:
                myBlockColor = Color.WHITE;
                break;
            case O:
                myBlockColor = Color.WHITE;

                break;
            case J:
                myBlockColor = MURICA_BLUE;

                break;
            case L:
                myBlockColor = MURICA_RED;

                break;
            case S:
                myBlockColor = MURICA_BLUE;

                break;
            case Z:
                myBlockColor = MURICA_RED;

                break;
            case T:
                myBlockColor = Color.YELLOW;

                break;
            default:
                break;
        }
    }
}
