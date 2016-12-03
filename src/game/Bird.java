package game;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import supporter.Config;
import supporter.Factory;

public class Bird implements Config,Runnable {
	
	private int w;
	private int h;

	private int x, y, yMotion;
	private Image[] animations;
	private int index = 0;
	Thread threadAnimation;

	public Bird() {

		animations = new Image[birdAnimations.length];

		for (int i = 0; i < birdAnimations.length; i++) {
			animations[i] = Factory.readImageFromSrc(birdAnimations[i]);
		}

		w = animations[0].getWidth(null);
		h = animations[0].getHeight(null);

		x = (F_WIDTH / 2) - (w / 2);
		y = ((F_HEIGHT-bgHeight) / 2) - (h / 2);
		yMotion = 0;
	}

	public void flappy() {
		yMotion = 0;
			
		try {
			yMotion -= 3;
			Thread.sleep(10);
			yMotion -= 5;
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if (threadAnimation != null) {
			threadAnimation.stop();
		} 
		threadAnimation = new Thread(this);
		threadAnimation.start();
	}

	@Override
	public void run() {
		try {
			index = 3;
			Thread.sleep(300);
			index = 4;
			Thread.sleep(50);
			index = 5;
			Thread.sleep(50);
			index = 6;
			Thread.sleep(400);
			index = 7;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		this.y += yMotion;
	}
	
	public void stopAnimation() {
		if (threadAnimation != null) {
			threadAnimation.stop();
		} 
	}

	public Image getImg() {
		return animations[index];
	}
	
	public void updateYMotion() {
		this.yMotion++;
	}

	public int getyMotion() {
		return yMotion;
	}

	public void setyMotion(int yMotion) {
		this.yMotion = yMotion;
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

	public Image getAnimations(int i) {
		return animations[i];
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
