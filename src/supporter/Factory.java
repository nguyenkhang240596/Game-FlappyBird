package supporter;

import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Factory {
	
	public static Image readImageFromSrc(String src) {
		Image img = null;
		try {
			img = ImageIO.read(new File(src));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static void playSound(String src) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(src).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static String readScore() {
		String res = "";
		try {
			FileInputStream f = new FileInputStream(Config.scorePath);
			DataInputStream dos = new DataInputStream(f);
			if (dos.available() > 0) {
				res = dos.readUTF();
			}
			dos.close();
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static void writeScore(String score) {
		try {
			FileOutputStream f = new FileOutputStream(Config.scorePath);
			DataOutputStream dos = new DataOutputStream(f);
			dos.writeUTF(score);
			dos.close();
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
