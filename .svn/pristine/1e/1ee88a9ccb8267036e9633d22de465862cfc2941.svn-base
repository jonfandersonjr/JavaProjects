/*
 * TCSS 305 - Tetris
 */
package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Guile picture for the game frame.
 * @author Jon Anderson
 * @version June 2
 *
 */
public class BackGroundImage extends JLabel {

    /**
     * Auto generated serial ID.
     */
    private static final long serialVersionUID = 4355218397733380113L;
    
    /**
     * Block size in small game.
     */
    private static final int SMALL = 25;
    
    /**
     * Block size in large game.
     */
    private static final int MEDIUM = 35;
    
    /**
     * Image for small game size.
     */
    private final ImageIcon myIcon1 = new ImageIcon("images/guile1.png");
    
    /**
     * Image for medium game size.
     */
    private final ImageIcon myIcon2 = new ImageIcon("images/guile2.png");
    
    /**
     * Image for large game size.
     */
    private final ImageIcon myIcon3 = new ImageIcon("images/guile3.png");

    /**
     * Sets the appropriate guile picture into the frame.
     * @param theIcon size to choose.
     */
    public BackGroundImage(final int theIcon) {
        super();
        if (theIcon == SMALL) {
            setIcon(myIcon1);
        } else if (theIcon == MEDIUM) {
            setIcon(myIcon2);
        } else {
            setIcon(myIcon3);
        }

        
    }
}
