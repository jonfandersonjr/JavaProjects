/*
 * TCSS 305 - Tetris.
 */
package view;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Music class for the tetris game.
 * @author Jon Anderson
 * @version 6/2/2017
 *
 */
public class Music {

    /**
     * The music playing in the background.
     */
    private final File myBackgroundMusic = new File("./music/gtheme.wav");

    /**
     * Sonic boom sound effect.
     */
    private final File mySonicBoom = new File("./music/sonicboom.wav");
    
    /**
     * America sound effect.
     */
    private final File myAmerica = new File("./music/america.wav");

    /**
     * Whether or not sounds are muted.
     */
    private boolean myMute;

    /**
     * The clip to play.
     */
    private Clip myClip;

    /**
     * Starts the music for the game.
     * @throws UnsupportedAudioFileException if unsupported.
     * @throws IOException if there is a problem.
     * @throws LineUnavailableException if it is unavailable.
     */
    protected void startMusic() throws UnsupportedAudioFileException, 
        IOException, LineUnavailableException {
        final AudioInputStream audio = AudioSystem.getAudioInputStream(myBackgroundMusic);
        myClip = AudioSystem.getClip();
        myClip.open(audio);
        myClip.loop(Clip.LOOP_CONTINUOUSLY);
        if (!myMute) {
            myClip.start();
        }
    }

    /**
     * Plays the sonic boom sound effect.
     * @throws UnsupportedAudioFileException for this.
     * @throws IOException for this.
     * @throws LineUnavailableException for this.
     */
    protected void playSonicBoom() throws UnsupportedAudioFileException, IOException,
        LineUnavailableException {
        if (!myMute) {
            final AudioInputStream audio = AudioSystem.getAudioInputStream(mySonicBoom);
            final Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        }
    }

    /**
     * Plays the clip for America.
     * @throws UnsupportedAudioFileException for this clip.
     * @throws IOException for this clip.
     * @throws LineUnavailableException for this clip.
     */
    protected void playAmerica() throws UnsupportedAudioFileException, IOException,
        LineUnavailableException {
        if (!myMute) {
            final AudioInputStream audio = AudioSystem.getAudioInputStream(myAmerica);
            final Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        }
    }

    /**
     * Stops the music.
     */
    protected void stop() {
        myClip.stop();
    }

    /**
     * Continues the music.
     */
    protected void start() {
        myClip.start();
    }

    /**
     * Sets the mute.
     * @param theMute for the sound effects.
     */
    protected void setMute(final boolean theMute) {
        myMute = theMute;
    }


}
