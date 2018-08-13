package framework;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

/**
 * This class is used to load the background music that is used in this game.
 * 
 * @author Aleksandar Spasic
 *
 */
public class MusicPlayer {

	private Clip clip;
	
	public MusicPlayer(){}
	
	public void play(String fileName){
		try{
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
			clip = AudioSystem.getClip();
			clip.open(ais);
			
			if(clip != null){
				new Thread(){
					public void run(){
						synchronized(clip){
							clip.stop();
							clip.setFramePosition(0);
							clip.start();
						}
					}
				}.start();
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void loop(String fileName){
		try{
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
			clip = AudioSystem.getClip();
			clip.open(ais);
			
			if(clip != null){
				new Thread(){
					public void run(){
						synchronized(clip){
							clip.stop();
							clip.setFramePosition(0);
							clip.loop(Clip.LOOP_CONTINUOUSLY);
						}
					}
				}.start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void stop(){
		try{
			if(clip != null){
				clip.stop();
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public boolean isActive(){
		return clip.isActive();
	}
}
