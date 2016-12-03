package game;

import java.awt.Image;

import supporter.Config;
import supporter.Factory;

public class BackGround implements Config {
	private int bgX, bgY;
	private Image img;

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public BackGround(int x, int y, String src) {
		this.bgX = x;
		this.bgY = y;

		this.img = Factory.readImageFromSrc(src);
	}

	public void updateBG() {
		bgX -= gameSpeed;
		if (bgX <= -1343) {
			bgX = 1336;
		}
	}

	public int getBgX() {
		return bgX;
	}

	public void setBgX(int bgX) {
		this.bgX = bgX;
	}

	public int getBgY() {
		return bgY;
	}

	public void setBgY(int bgY) {
		this.bgY = bgY;
	}

}
