package game.model;

import java.io.File;

import game.view.MainFrame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GameSounds {


	public static synchronized void playSound(final String url) {
		new Thread(new Runnable() {
		  // The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.
		    public void run() {
		      try {
		        Clip clip = AudioSystem.getClip();
		        File audioFile = new File("Sounds/" + url);
		        clip.open(AudioSystem.getAudioInputStream(audioFile));

		        clip.start();

		        Thread.sleep(1000);

	        	clip.stop();

	        	clip.close();

		      } catch (Exception e) {
		        System.err.println("sound error:" + e.getMessage());
		      }
		    }
		}).start();
	}

}
