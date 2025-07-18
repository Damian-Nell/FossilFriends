package Main.src.Managers;

import java.net.URL;
import javax.sound.sampled.*;

public class SoundManager {

    //single clip file to save memory
    private Clip clip;

    //loads the specified sound file and volume settings and play the sound at that volume.
    public void play(String SoundFile, float volume) {
        try {
            URL url = getClass().getResource("/Main/res/snds/" + SoundFile);
            AudioInputStream AS = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(AS);
            
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = gain.getMinimum();
            float max = gain.getMaximum();
            float db = min + (max - min) * volume;
            gain.setValue(db);
            
            clip.start();
            
        } catch (Exception e) {
            System.out.println("failed to play sound");
        }
    }
}
