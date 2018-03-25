/*
 * TCSS 305 - Assignment 6: Tetris
 */

package view;

import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Runs Tetris by instantiating and starting the TetrisGUI.
 * 
 * @author Jon Anderson
 * @version May 18
 */
public final class TetrisMain {

    /**
     * Private constructor, to prevent instantiation of this class.
     */
    private TetrisMain() {
        throw new IllegalStateException();
    }

    /**
     * The main method, invokes the Tetris GUI. Command line arguments are
     * ignored.
     * 
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        } catch (final InstantiationException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        } catch (final UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TetrisGUI().start();
            }
        });
    }
}
