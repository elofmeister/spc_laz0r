import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class SoundPlayer {

	private Clip clip = null;
	private AudioInputStream audioIn = null;
	private boolean isEnabled = true;
	
	public SoundPlayer(String file) {
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
		try {
			audioIn = AudioSystem.getAudioInputStream(SoundPlayer.class.getResource(file));
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			clip.open(audioIn);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		if (isEnabled) {
			if (clip.isRunning()) {
				clip.stop();
			}
			clip.setFramePosition(0);
			clip.start();
		}
	}
	
	public void setEnabled(boolean enabled) {
		isEnabled = enabled;
	}
}
