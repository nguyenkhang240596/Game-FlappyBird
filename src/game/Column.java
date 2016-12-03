package game;

import java.awt.Image;

import supporter.Config;
import supporter.Factory;

public class Column implements Config {
	
	private int x, y;
	private int w, h;
	private Image img;
	private int rotate;
	
	public Column(int rotate,int x,int y,int width,int height) {
		this.x = x;
		this.y = y;
		this.w = width;
		this.h = height;
		this.rotate = rotate;
		img = Factory.readImageFromSrc(pipesUrl[rotate]);
	}

	public void update() {
		x -= gameSpeed;
	}

	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public Image getImg() {
		return img;
	}

	public int getRotate() {
		return rotate;
	}

	public void setRotate(int rotate) {
		this.rotate = rotate;
	}


}
