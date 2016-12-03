package supporter;

import java.awt.Font;

public interface Config {
	public final static int F_WIDTH = 800;
	public final static int F_HEIGHT = 700;
	public final static String title = "Flappy Bird";
	
	public final static String bgUrl = "src/images/bg.png";
	public final static String bgSkyUrl[] = {"src/images/sky.png","src/images/sky_night.png"};
	public final static String flappyBirdBanner = "src/images/flappy-bird.png";
	public final static String getReady = "src/images/get_ready.png";
	public final static String pauseButton = "src/images/pause.png";
	public final static String playingButton = "src/images/playing.png";
	
	public final static String [] birdAnimations = {
			"src/images/bird.png",
			"src/images/bird11.png",
			"src/images/bird12.png",
			"src/images/bird2.png",
			"src/images/bird3.png",
			"src/images/bird4.png",
			"src/images/bird5.png",
			"src/images/bird6.png"
	};
	
	public final static String [] pipesUrl = {
			"src/images/pipe.png",
			"src/images/pipe2.png"
	};
	
	public final static String badingSound = "src/sounds/bading.wav";
	public final static String flapSound = "src/sounds/flap.wav";
	public final static String smackSound = "src/sounds/smack.wav";
	
	// Store score
	public final static String scorePath = "src/data/score.txt";
	
	public final static int bgHeight = 205;
	
    public static enum GameState{STARTING, WAITING, PAUSE, VISUALIZING, PLAYING, GAMEOVER};
    
    public final static String startGame = "Space to flap";
    public final static String pauseGame = "GAME IS PAUSED";
    public final static String gameOver = "GAME OVER";
    public final static String score = "SCORE :";
    public final static String highScore = "HIGH SCORE:";
    
    public static Font font = new Font("Monospaced", Font.BOLD, 40);
    public static Font playingFont = new Font("Monospaced", Font.BOLD, 50);
    
	public static int gameSpeed = 5;
	
    public static int maxYMotion = 9;
    
    public final static int pipeWidth = 100;
    public final static int distanceTwoPipes = 150;
    public final static int spaceTwoPipes = 150;
    
    public final static int btnPausePaddingTop = 50;
    public final static int btnPausePaddingLeft = 50;
    public final static int btnPauseWidth = 50;
    public final static int btnPauseHeight = 50; 
    
}
