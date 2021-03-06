/*
 * TCSS 305 - Assignment 6 Tetris
 */
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;

/**
 * Constructs a Menu Bar item to be used in my tetris game.
 * @author Jon Anderson
 * @version May 18
 *
 */
public class TetrisMenu extends JMenuBar implements Observer {

    /**
     * Auto Generated serial ID number.
     */
    private static final long serialVersionUID = 6523545150132007076L;

    /**
     * Max level for tetris game.
     */
    private static final int MAX_DIFFICULTY = 10;

    /**
     * Tick space for level difficulty.
     */
    private static final int MAJOR_TICK_SPACE = 3;

    /**
     * The slider for difficulty.
     */
    private final JSlider mySlider = new JSlider(1, MAX_DIFFICULTY, 1);

    /**
     * My TetrisGUI and access to its methods.
     */
    private final TetrisGUI myGUI;

    /**
     * My score panel for this game.
     */
    private final ScorePanel myScorePanel;
    
    /**
     * The mute button.
     */
    private final JMenuItem myMute = new JMenuItem("Mute for Game");
    
    /**
     * My new game menu item.
     */
    private final JMenuItem myNewGame = new JMenuItem("New Game");
    
    /**
     * My end game menu item.
     */
    private final JMenuItem myEndGame = new JMenuItem("End Game");

    /**
     * Constructs a menu bar for the Tetris Game.
     * @param theGUI connection to the tetris GUI.
     * @param theScorePanel panel for the game.
     */
    public TetrisMenu(final TetrisGUI theGUI,
                      final ScorePanel theScorePanel) {
        super();
        myGUI = theGUI;
        myScorePanel = theScorePanel;
        add(createFileMenu());
        add(createOptionsMenu());
        add(createHelpMenu());
        myScorePanel.setLevel(1, mySlider);
    }

    /**
     * Creates a file menu in my menu bar which contains the option to quit the program.
     * @return a completed file menu.
     */
    private JMenu createFileMenu() {
        final JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        final JMenuItem quit = new JMenuItem("Quit");
        quit.setMnemonic(KeyEvent.VK_Q);
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                System.exit(ABORT);
            }
        });
        myEndGame.setEnabled(false);
        myNewGame.setMnemonic(KeyEvent.VK_N);
        myNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theAction) {
                try {
                    myGUI.startNewGame();
                    myMute.setEnabled(true);
                } catch (final UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (final IOException e) {
                    e.printStackTrace();
                } catch (final LineUnavailableException e) {
                    e.printStackTrace();
                }
                myEndGame.setEnabled(true);
                myNewGame.setEnabled(false);
            }
        });
        myEndGame.setMnemonic(KeyEvent.VK_E);
        myEndGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theAction) {
                myGUI.endGame();
                myEndGame.setEnabled(false);
                myNewGame.setEnabled(true);
            }
        });
        file.add(myNewGame);
        file.add(myEndGame);
        file.addSeparator();
        file.add(quit);
        return file;
    }

    /**
     * Creates an options menu for the Tetris game.
     * @return an options menu to my menu bar.
     */
    private JMenu createOptionsMenu() {
        final JMenu options = new JMenu("Options");
        options.setMnemonic(KeyEvent.VK_O);
        myMute.setMnemonic(KeyEvent.VK_M);
        myMute.setEnabled(false);
        myMute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                myGUI.muteMusic();
                myMute.setEnabled(false);
            }
        });
        final JMenu level = new JMenu("Select Level");
        level.setMnemonic(KeyEvent.VK_S);
        final JMenuItem displaySize = new JMenuItem("Display Size...");
        displaySize.setMnemonic(KeyEvent.VK_D);
        displaySize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myGUI.pauseGame();
                myGUI.start();
            }
        });
        options.add(myMute);
        options.addSeparator();
        options.add(level);
        options.add(displaySize);
        level.add(sliderBuilder());
        return options;
    }

    /**
     * Constructs a Help Menu for the Tetris menu bar.
     * @return the constructed Help Menu.
     */
    private JMenu createHelpMenu() {
        final String title = "About...";
        final JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        final JMenuItem about = new JMenuItem(title);
        about.setMnemonic(KeyEvent.VK_A);
        helpMenu.add(about);
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                JOptionPane.showMessageDialog(null, "Music Credits: \n"
                                + "Background Music - StreetFighter II - Capcom inc. \n"
                                + "Sound Effect #1 - StreetFighter II - Capcom inc. \n"
                                + "Sound Effect #2 - South Park \n"
                                + "Image Credit: \n"
                                + "DeviantArt - user: El-Tor0");
            }
        });
        final JMenuItem scoring = new JMenuItem("Scoring...");
        scoring.setMnemonic(KeyEvent.VK_S);
        scoring.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                JOptionPane.showMessageDialog(null, "Scoring Formula: \n"
                                + "4 Points per block placed \n"              
                                + "One Line: 40 * Level \n"
                                + "Two Lines: 100 * Level \n"
                                + "Three Lines: 300 * Level \n"
                                + "Four Lines: 1200 * Level \n");
            }

        });
        helpMenu.add(scoring);
        return helpMenu;
    }

    /**
     * Creates a slider which allows the user to change the level difficulty.
     * @return a slider to select level difficulty.
     */
    private JSlider sliderBuilder() {
        mySlider.setValue(1);
        mySlider.setMajorTickSpacing(MAJOR_TICK_SPACE);
        mySlider.setMinorTickSpacing(1);
        mySlider.setPaintTicks(true);
        mySlider.setPaintLabels(true);
        mySlider.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent arg0) {
                myScorePanel.setLevel(mySlider.getValue(), mySlider);
            }
        });
        return mySlider;
    }

    @Override
    public void update(final Observable theObservable, final Object theObject) {
        if (theObject instanceof Boolean) {
            myNewGame.setEnabled(true);
            myEndGame.setEnabled(false);
            myMute.setEnabled(false);
        }
        
    }

}
