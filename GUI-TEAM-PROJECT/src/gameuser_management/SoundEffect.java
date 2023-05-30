package gameuser_management;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffect 
{
	Clip clip;
	AudioInputStream loginSuccessAudioStream;
	AudioInputStream loginFailedAudioStream;
	
	String path = "/Users/kimminseo/git/GUI-userdb/GUI-TEAM-PROJECT/src/gameuser_management/";
	
	File loginSuccessSoundFile = new File(path+"로그인성공.wav");
	File loginFailedSoundFile = new File(path+"로그인실패.wav");
	
	public SoundEffect() throws UnsupportedAudioFileException, IOException, LineUnavailableException 
	{
		loginSuccessAudioStream = AudioSystem.getAudioInputStream(loginSuccessSoundFile); 
		loginFailedAudioStream = AudioSystem.getAudioInputStream(loginFailedSoundFile);
		clip = AudioSystem.getClip();
	}
	
	public void playLoginSuccess() throws LineUnavailableException, IOException
	{
		clip.close();
		clip.open(loginSuccessAudioStream); 	
		
		clip.setFramePosition(0);
		clip.start();
		}
	
	public void playLoginFailed() throws LineUnavailableException, IOException
	{
		clip.close();
		clip.open(loginFailedAudioStream); 	
		clip.setFramePosition(0);
		clip.start();
	}
}
