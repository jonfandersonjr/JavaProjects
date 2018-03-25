/*
 * TCSS 305 - Tetris
 */
package view;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.Timer;
import model.TetrisPiece;

/**
 * Panel which will hold and keep track of the score and other 
 * information pertaining to the game.
 * 
 * @author Jon Anderson
 * @version June 1
 */
public class ScorePanel extends JSplitPane implements Observer {

    /**
     * Auto generated serial ID.
     */
    private static final long serialVersionUID = 118141119856970403L;

    /**
     * Amount of plain font JLabel's.
     */
    private static final int PLAIN_FONTS = 3;

    /**
     * Increments score by this much when a piece is dropped.
     */
    private static final int SCORE_MOD = 4;

    /**
     * Modifier for the size of the panels.
     */
    private static final int PANEL_MOD = 7;

    /**
     * Modifier for the location of the split panel divider.
     */
    private static final int DIVIDER_MOD = 6;

    /**
     * Modifies the size of the labels using the block size.
     */
    private static final double LABEL_SIZE_MODIFIER = 1.25;

    /**
     * How much the game needs to speed up per level up.
     */
    private static final int INCREMENT_TIMER = 75;

    /**
     * Multiplier for one line completed.
     */
    private static final int ONE_MULT = 40;

    /**
     * Multiplier for two lines completed.
     */
    private static final int TWO_MULT = 100;

    /**
     * Multiplier for three lines completed.
     */
    private static final int THREE_MULT = 300;

    /**
     * Multiplier for four lines completed.
     */
    private static final int FOUR_MULT = 1200;

    /**
     * List of the labels in this panel.
     */
    private final List<JLabel> myLabels = new ArrayList<JLabel>();

    /**
     * Label for displaying the score.
     */
    private final JLabel myScore = addLabel("0");

    /**
     * Label for lines currently cleared.
     */
    private final JLabel myCleared = addLabel("0 ");

    /**
     * Label displaying the current level.
     */
    private final JLabel myCurrentLevel = addLabel("1");

    /**
     * Amount of lines to get to the next level.
     */
    private final JLabel myNextLevel;

    /**
     * The block size for this game.
     */
    private final int myBlockSize;

    /**
     * myRequirement for the amount of lines that need to be cleared for next level.
     */
    private final int myRequirement;

    /**
     * The amount of lines the user has cleared.
     */
    private int myLinesCleared;

    /**
     * Counts how many games have been played.
     */
    private int myGamesPlayed;

    /**
     * The level the user is on.
     */
    private int myLevel = 1;

    /**
     * Keeps track of how many lines the user has completed in respect to the amount required.
     */
    private int myCounter;

    /**
     * Int which keeps track of the user's score.
     */
    private int myScoreValue = -SCORE_MOD;

    /**
     * Int array holding the scores per line value.
     */
    private final int[] myScores = {0, ONE_MULT, TWO_MULT, THREE_MULT, FOUR_MULT};

    /**
     * The timer for the game.
     */
    private final Timer myTimer;

    /**
     * The delay of the timer.
     */
    private final int myTimerDelay;

    /**
     * The slider for the difficulty.
     */
    private JSlider mySlider;

    /**
     * The music and sound effects for the game.
     */
    private final Music myMusic;

    /**
     * Constructs a score panel to keep track of various information pertaining to the game.
     * @param theBlockSize to base this panel size on.
     * @param theLineRequirement to move on to the next level.
     * @param theTimer for the game.
     * @param theTimerDelay of this game at the start.
     * @param theMusic for this game.
     */
    public ScorePanel(final int theBlockSize, final int theLineRequirement,
                      final Timer theTimer, final int theTimerDelay, final Music theMusic) {
        super();
        myNextLevel = addLabel(String.valueOf(theLineRequirement));
        myBlockSize = theBlockSize;
        myTimer = theTimer;
        myTimerDelay = theTimerDelay;
        myRequirement = theLineRequirement;
        myMusic = theMusic;
        modSize();
        setBorder(BorderFactory.createStrokeBorder(new BasicStroke(PLAIN_FONTS)));
    }

    /**
     * Modifies the size of the panel.
     */
    private void modSize() {
        setPreferredSize(new Dimension(myBlockSize * PANEL_MOD, 
                                       myBlockSize * PANEL_MOD));
        setDividerSize(0);
        setDividerLocation(myBlockSize * DIVIDER_MOD);
        setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        setLeftComponent(boldLabelPanel());
        setRightComponent(plainLabelPanel());
    }

    /**
     * Creates a panel with the bold labels for the west.
     * @return the created west panel.
     */
    private JPanel boldLabelPanel() {
        final JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.add(addLabel("Current Score:"));
        panel.add(addLabel("Lines Cleared:"));
        panel.add(addLabel("Current Level:"));
        panel.add(addLabel("For Next Level: "));
        return panel;
    }

    /**
     * Adds the plain labels to the east panel.
     * @return the created east panel.
     */
    private JPanel plainLabelPanel() {
        final JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.add(myScore);
        panel.add(myCleared);
        panel.add(myCurrentLevel);
        panel.add(myNextLevel);
        setFont();
        return panel;
    }

    /**
     * Creates a label and adds it to myLabel list.
     * @param theLabel name for the created label.
     * @return the created label.
     */
    private JLabel addLabel(final String theLabel) {
        final JLabel label = new JLabel(theLabel);
        myLabels.add(label);
        return label;
    }

    /**
     * Sets the fonts for all the labels.
     */
    private void setFont() {
        final Font boldFont = new Font(Font.SERIF, Font.BOLD,
                                       (int) (myBlockSize / LABEL_SIZE_MODIFIER));
        final Font plainFont = new Font("Osaka", Font.ITALIC,
                                        (int) (myBlockSize / LABEL_SIZE_MODIFIER));
        int count = 0;
        for (final JLabel label: myLabels) {
            label.setFont(plainFont);
            if (count > PLAIN_FONTS) {
                label.setFont(boldFont);
            }
            count++;
        }
    }

    @Override
    public void update(final Observable theObserv, final Object theObject) {
        if (theObject instanceof TetrisPiece) {
            myScoreValue += SCORE_MOD;
            myScore.setText(String.valueOf(myScoreValue));
        }
        if (theObject instanceof Integer[]) {
            updateDisplay(((Integer[]) theObject).length);
            calculateScore(((Integer[]) theObject).length);
        }
    }

    /**
     * Sets the level based on the slider.
     * @param theLevel of the game.
     * @param theSlider for the level.
     */
    protected void setLevel(final int theLevel, final JSlider theSlider) {
        myLevel = theLevel;
        mySlider = theSlider;
        mySlider.setValue(myLevel);
        myCurrentLevel.setText(String.valueOf(myLevel));
        myTimer.setDelay(myTimerDelay - INCREMENT_TIMER * myLevel);
    }

    /**
     * Updates all the labels except for the score.
     * @param theLines that have been cleared.
     */
    private void updateDisplay(final int theLines) {
        myCounter += theLines;
        myLinesCleared += theLines;
        myCleared.setText(String.valueOf(myLinesCleared));
        if (myCounter >= myRequirement) {
            myLevel++;
            setLevel(myLevel, mySlider);
            myCounter = myCounter % myRequirement;
        }
        myCurrentLevel.setText(String.valueOf(myLevel));
        myTimer.setDelay(myTimerDelay - INCREMENT_TIMER * myLevel);
        myNextLevel.setText(String.valueOf(myRequirement - myCounter));
    }

    /**
     * Calculates the score of the game after clearing lines.
     * @param theLines that were cleared.
     */
    private void calculateScore(final int theLines) {
        if (theLines < SCORE_MOD) {
            try {
                myMusic.playSonicBoom();
            } catch (final UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (final IOException e) {
                e.printStackTrace();
            } catch (final LineUnavailableException e) {
                e.printStackTrace();
            }
        } else {
            try {
                myMusic.playAmerica();
            } catch (final UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (final IOException e) {
                e.printStackTrace();
            } catch (final LineUnavailableException e) {
                e.printStackTrace();
            }
        }
        myScoreValue += myScores[theLines] * myLevel;
        myScore.setText(String.valueOf(myScoreValue));
    }

    /**
     * Resets the values and texts of the labels to their default state.
     */
    protected void resetValues() {
        myScoreValue = -SCORE_MOD;
        if (myGamesPlayed > 0) {
            myScoreValue -= SCORE_MOD;
        }
        myScore.setText("0   ");
        myTimer.setDelay(myTimerDelay);
        myCounter = 0;
        myLinesCleared = 0;
        myCleared.setText("0  ");
        myLevel = 1;
        mySlider.setValue(myLevel);
        myCurrentLevel.setText(String.valueOf(myLevel));
        myNextLevel.setText(String.valueOf(myRequirement));
        myGamesPlayed++;
    }

}