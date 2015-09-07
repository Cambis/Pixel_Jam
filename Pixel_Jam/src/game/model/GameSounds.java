package game.model;

import java.io.File;
import java.io.IOException;

import game.view.MainFrame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GameSounds {



	public Clip bounce0Clip;
	public Clip Bounce1Clip;
	public Clip Bounce2Clip;
	public Clip Bounce3Clip;


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



	        	clip.stop();

	        	clip.close();

		      } catch (Exception e) {
		        System.err.println("sound error:" + e.getMessage());
		      }
		    }
		}).start();
	}


//	public GameSounds() {
//
//        try {
//        	bounce0Clip = AudioSystem.getClip();
//        	File audioFile = new File("Sounds/bounce0.wav");
//			bounce0Clip.open(AudioSystem.getAudioInputStream(audioFile));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
