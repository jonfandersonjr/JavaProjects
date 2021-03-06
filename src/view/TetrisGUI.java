/*
 * TCSS 305 - Tetris
 */
package view;

import com.sun.glass.events.KeyEvent;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Board;

/**
 * Class which holds my GUI and all the panels within.
 * 
 * @author Jon Anderson
 * @version May 19
 */
public class TetrisGUI implements Observer {

    /**
     * Delay for timer.
     */
    private static final int TIMER_DELAY = 750;
    
    /**
     * Lines required to be cleared for next level.
     */
    private static final int LINES_CLEARED = 5;

    /**
     * Default size of the game - medium - 35 pixels per square.
     */
    private static final int DEFAULT_SIZE = 35;
    
    /**
     * Square size for a small display.
     */
    private static final int SMALL_DISPLAY = 25;
    
    /**
     * Square size for a medium display.
     */
    private static final int MEDIUM_DISPLAY = 35;
    
    /**
     * Square size for a large display.
     */
    private static final int LARGE_DISPLAY = 45;
    
    /**
     * Game title.
     */
    private static final String TITLE = "Tetris";
    
    /**
     * Default size for the game set to medium.
     */
    private int myGameSize = DEFAULT_SIZE;

    /**
     * Swing timer for the game.
     */
    private final Timer myTimer = new Timer(TIMER_DELAY, new MyTimer());
    
    /**
     * The key listener for this tetris game.
     */
    private final MyKeyListener myKeyListener = new MyKeyListener();

    /**
     * Boolean which tracks whether or not the game is paused.
     */
    private boolean myPause;

    /**
     * My Icon for my frame.
     */
    private final ImageIcon myIcon = new ImageIcon("images/tetris.jpg");
    
    /**
     * The music for this game.
     */
    private final Music myMusic = new Music();

    /**
     * Label for Game Over JOptionPane.
     */
    private final JLabel myGameOver = new JLabel("Game Over!                ", JLabel.CENTER);
    
    /**
     * Creates the board that we will be playing on.
     */
    private final Board myBoard = new Board();

    /**
     * Creates the JFrame to hold all the panels for the game.
     */
    private JFrame myFrame;
    
    /**
     * The score panel for the tetris game.
     */
    private ScorePanel myScorePanel;
    
    /**
     * Field for if music is playing.
     */
    private boolean myPlaying;

    /**
     * Starts the Frame with all of the game panels.
     */
    public void start() {
        final int blockSize = displaySize(myGameSize);

        myScorePanel = new ScorePanel(blockSize, LINES_CLEARED,
                                      myTimer, TIMER_DELAY, myMusic);
        
        final TetrisMenu menuBar = new TetrisMenu(this, myScorePanel);
        
        if (blockSize == 0) {
            return;
        }

        myFrame = new JFrame(TITLE);

        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setResizable(false);

        final GamePanel gamePanel = new GamePanel(blockSize, myBoard.getHeight());
        final NextPiecePanel nextPiecePanel = new NextPiecePanel(blockSize);
        myBoard.addObserver(nextPiecePanel);
        myBoard.addObserver(myScorePanel);
        myBoard.addObserver(menuBar);
        final SidePanel sidePanel = new SidePanel(blockSize, nextPiecePanel,
                                                  myScorePanel);

        myBoard.addObserver(this);
        myBoard.addObserver(gamePanel);
        
        final JPanel temp = new JPanel();
        temp.add(gamePanel);
        temp.setOpaque(false);
        
        final BackGroundImage background = new BackGroundImage(blockSize);

        final JPanel sideTemp = new JPanel();
        sideTemp.add(sidePanel);
        
        myFrame.addKeyListener(myKeyListener);
        myFrame.add(temp, BorderLayout.CENTER);
       // myFrame.add(temp, BorderLayout.CENTER);
        myFrame.add(sideTemp, BorderLayout.WEST);
        myFrame.add(background, BorderLayout.EAST);
        myFrame.setJMenuBar(menuBar);
        myFrame.setIconImage(myIcon.getImage());
        myFrame.pack();
        final int height = myFrame.getHeight() + 2;
        myFrame.setSize(myFrame.getWidth(), height);
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
        gamePanel.repaint();
    }


    @Override
    public void update(final Observable theObserv, final Object theObject) {
        if (theObject instanceof Boolean) {
            endGame();
            JOptionPane.showMessageDialog(myFrame, myGameOver);
        }
    }
    
    /**
     * JOptionPane dialogue that determines the size for this game.
     * @param theDefaultSize of this game.
     * @return the size for this game.
     */
    private int displaySize(final int theDefaultSize) {
        final JLabel boardSize = new JLabel("What size board would you like "
                                            + "to play on?", JLabel.CENTER);
        int displaySize = theDefaultSize;
        final String[] options = new String[] {"Small", "Medium", "Large"};
        final int response = JOptionPane.showOptionDialog(null, boardSize, "Game Size",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
        if (response == 0) {
            displaySize = SMALL_DISPLAY;
            disposeOldFrame();
        } else if (response == 1) {
            displaySize = MEDIUM_DISPLAY;
            disposeOldFrame();
        } else if (response == 2) {
            displaySize = LARGE_DISPLAY;
            disposeOldFrame();
        } else {
            displaySize = 0;
            if (!isPause()) {
                System.exit(0);
            }
        }
        if (myPlaying) {
            myMusic.stop();
        }
        return displaySize;
    }

    /**
     * Resets the scoring values and starts a new game.
     * @throws LineUnavailableException 
     * @throws IOException 
     * @throws UnsupportedAudioFileException 
     */
    protected void startNewGame() throws UnsupportedAudioFileException, 
                                         IOException, LineUnavailableException {
        if (myFrame.getKeyListeners().length < 1) {
            myFrame.addKeyListener(myKeyListener);
        }
        myPause = false;
        myKeyListener.setDisabled(false);
        myTimer.restart();
        myScorePanel.resetValues();
        myBoard.newGame();
        myPlaying = true;
        myMusic.setMute(false);
        myMusic.startMusic();
    }
    
    /**
     * Ends the game and remove actions.
     */
    protected void endGame() {
        myPause = true;
        myMusic.stop();
        myTimer.stop();
        myFrame.removeKeyListener(myKeyListener);
    }
    
    /**
     * Sets the default size of this game.
     * @param theDefaultSize for the game.
     */
    protected void setDefaultSize(final int theDefaultSize) {
        myGameSize = theDefaultSize;
    }
    
    /**
     * Gets the amount of lines needed to reach the next level.
     * @return the amount of lines needed to reach next level.
     */
    protected int getNextLevel() {
        return LINES_CLEARED;
    }
    
    /**
     * Gets rid of the previous frame if the user changes their view size.
     */
    protected void disposeOldFrame() {
        if (myFrame == null) {
            return;
        }
        myFrame.dispose();
    }
    
    /**
     * Mutes the music for this game.
     */
    protected void muteMusic() {
        myMusic.stop();
        myMusic.setMute(true);
    }

    /**
     * Pauses or unpauses when called.
     */
    protected void togglePause() {
        myPause = !myPause;
        if (myPause) {
            pauseGame();
            myMusic.stop();
            myKeyListener.setDisabled(true);
        } else {
            startGame();
            myMusic.start();
            myKeyListener.setDisabled(false);
        }
    }

    /**
     * Starts the game and adjust appropriate variables.
     */
    protected void startGame() {
        myPause = false;
        myTimer.start();
        myKeyListener.setDisabled(false);
    }

    /**
     * Stops the game and adjust appropriate variables.
     */
    protected void pauseGame() {
        myPause = true;
        myTimer.stop();  
        myKeyListener.setDisabled(true);
    }

    /**
     * Gets the pause state of the game.
     * @return if the game is paused.
     */
    protected boolean isPause() {
        return myPause;
    }
    
    /**
     * Inner class with actions for Timer.
     * @author Jon Anderson
     */
    class MyTimer implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent theAction) {
            myBoard.step();
        }
    }

    /**
     * Inner class with actions for Keys.
     * @author Jon Anderson
     */
    class MyKeyListener implements KeyListener {
        
        /**
         * Instance field for if keys are disabled.
         */
        private boolean myDisabled;

        /**
         * Sets if non-pause keys are enabled or disabled.
         * @param theDisable for the keys.
         */
        protected void setDisabled(final boolean theDisable) {
            myDisabled = theDisable;
        }

        @Override
        public void keyPressed(final java.awt.event.KeyEvent arg0) {
            if (!myDisabled) {
                moveLeft(arg0);
                moveRight(arg0);
                moveDown(arg0);
                moveDrop(arg0);
                moveCW(arg0);
                moveCCW(arg0);
            }

            if (arg0.getKeyCode() == KeyEvent.VK_P) {
                togglePause();
            }
        }

        /**
         * Moves the piece left.
         * @param arg0 the key press.
         */
        private void moveLeft(final java.awt.event.KeyEvent arg0) {
            if (arg0.getKeyCode() == KeyEvent.VK_A
                            || arg0.getKeyCode() == KeyEvent.VK_LEFT) {
                myBoard.left();
            }
        }

        /**
         * Moves the piece right.         * @param arg0 the key press.
         */
        private void moveRight(final java.awt.event.KeyEvent arg0) {
            if (arg0.getKeyCode() == KeyEvent.VK_D
                            || arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
                myBoard.right();
            }
        }

        /**
         * Moves the piece down.
         * @param arg0 the key press.
         */
        private void moveDown(final java.awt.event.KeyEvent arg0) {
            if (arg0.getKeyCode() == KeyEvent.VK_S
                            || arg0.getKeyCode() == KeyEvent.VK_DOWN) {
                myBoard.down();
            }
        }

        /**
         * Drops the piece.
         * @param arg0 the key press.
         */
        private void moveDrop(final java.awt.event.KeyEvent arg0) {
            if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
                myBoard.drop();
            }
        }

        /**
         * Moves the piece Clockwise.
         * @param arg0 the key press.
         */
        private void moveCW(final java.awt.event.KeyEvent arg0) {
            if (arg0.getKeyCode() == KeyEvent.VK_W
                            || arg0.getKeyCode() == KeyEvent.VK_UP) {
                myBoard.rotateCW();
            }
        }

        /**
         * Moves the piece CounterClockwise.
         * @param arg0 the key press.
         */
        private void moveCCW(final java.awt.event.KeyEvent arg0) {
            if (arg0.getKeyCode() == KeyEvent.VK_TAB
                            || arg0.getKeyCode() == KeyEvent.VK_SHIFT) {
                myBoard.rotateCCW();
            }
        }

        @Override
        public void keyReleased(final java.awt.event.KeyEvent arg0) {
        }

        @Override
        public void keyTyped(final java.awt.event.KeyEvent e2) {
        }
    }

}
