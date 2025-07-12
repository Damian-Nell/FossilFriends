package Main;

import java.io.File;
import javax.sound.sampled.*;

public class SoundManager {

    private Clip clip;

    public void play(String SoundFile, float volume) {
        try {
            File file = new File(SoundFile);
            AudioInputStream AS = AudioSystem.getAudioInputStream(file);
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
