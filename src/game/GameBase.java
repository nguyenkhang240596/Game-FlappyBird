package game;

import javax.swing.JFrame;

import supporter.Config;
import supporter.Factory;

public class GameBase implements Config {
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.add(new Game());
		f.setSize(F_WIDTH, F_HEIGHT);
		f.setTitle(title);
		f.setResizable(false);
		f.setLocationRelativeTo(null); // cho frame hiên thị ơ giua man hinh
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setIconImage(Factory.readImageFromSrc("src/images/icon.png"));
		f.setVisible(true);
	}
}
