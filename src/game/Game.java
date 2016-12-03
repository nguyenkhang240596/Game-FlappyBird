package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import supporter.Config;
import supporter.Factory;

public class Game extends Canvas implements Config {


	public static GameState gameState;

	private Random rand;

	private ArrayList<Column> columns;
	private Timer loop;
	private Bird bird;

	private static BackGround bg1, bg2, bg3;

	public int ticks, score, visualizeAnimation;

	public Game() {
		rand = new Random();
		bird = new Bird();
		columns = new ArrayList<>();
		bg1 = new BackGround(0, F_HEIGHT - bgHeight, bgUrl);
		bg2 = new BackGround(1342, F_HEIGHT - bgHeight, bgUrl);
		gameState = GameState.VISUALIZING;
		newGame();
	}

	private void newGame() {
		if (columns.size() > 0) // remove cac cot o lan choi truoc neu co
			columns.removeAll(columns);
		createColumns();
		score = 0;
		visualizeAnimation = 0;
		bg3 = new BackGround(0, 0, bgSkyUrl[rand.nextInt(2)]);
		if (gameState != GameState.VISUALIZING) {
			gameState = GameState.WAITING;
			bird.setX(F_WIDTH/2 - bird.getW()/2); 
			bird.setY(((F_HEIGHT - bgHeight) / 2) - (bird.getH() / 2));
		}
		gameLoop();
	}

	private void gameLoop() {
		if (loop == null) {
			loop = new Timer(20, this);
			loop.start();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ticks++;

		switch (gameState) {
		case VISUALIZING:
			visualizingGame();
			break;
			
		case WAITING:
			waitingGame();
			break;
			
		case PLAYING:
			playingGame();
			break;
		case PAUSE:
			break;
		case GAMEOVER:
			gameOver();
			break;

		default:
			break;
		}

		repaint();
	}

	private void waitingGame() {
		if (ticks % 3 == 0) {
			if (visualizeAnimation >= 3)
				visualizeAnimation = 0;
			bird.setIndex(visualizeAnimation++);
		}
		bg1.updateBG();
		bg2.updateBG();
	}

	private void gameOver() {
		if (ticks % 2 == 0)
		{
			if (bird.getyMotion() < maxYMotion)
				bird.updateYMotion();
		}

		bird.setIndex(birdAnimations.length-1);
		if (bird.getY() < (F_HEIGHT-bgHeight-((4*bird.getH())/5))) bird.update(); // hieu ung chim dap dau xuong dat
	}

	private void visualizingGame() {

		if (ticks % 3 == 0) {
			if (visualizeAnimation >= 3)
				visualizeAnimation = 0;
			bird.setIndex(visualizeAnimation++);
			bird.setX((F_WIDTH * 20) / 100);
			bird.setY(((F_HEIGHT - bgHeight) / 2) - (bird.getH() / 2));
		}
		bg1.updateBG();
		bg2.updateBG();

	}

	private void addColumn(boolean start) {
		int height = 50 + rand.nextInt(250);
		if (start) {
			columns.add(new Column(0, F_WIDTH + pipeWidth + columns.size() * distanceTwoPipes,
					F_HEIGHT - height - bgHeight, pipeWidth, height));
			columns.add(new Column(1, F_WIDTH + pipeWidth + (columns.size() - 1) * distanceTwoPipes,
					0, pipeWidth, F_HEIGHT - height - bgHeight - spaceTwoPipes));     
		} else {
			columns.add(new Column(0, columns.get(columns.size() - 1).getX() + 300,
					F_HEIGHT - height - bgHeight, pipeWidth, height));
			columns.add(new Column(1, columns.get(columns.size() - 1).getX(), 0, pipeWidth,
					F_HEIGHT - height - bgHeight - spaceTwoPipes));
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			flappyBird();
		}
	}

	private void flappyBird() {
		switch (gameState) {
		case VISUALIZING:
			gameState = GameState.WAITING;
			bird.setX((F_WIDTH / 2) - (bird.getW() / 2));
			bird.setY(((F_HEIGHT - bgHeight) / 2) - (bird.getH() / 2));
			break;
		case WAITING:
			gameState = GameState.PLAYING;
			Factory.playSound(flapSound);
			bird.flappy();
			break;
		case PLAYING:
			Factory.playSound(flapSound);
			bird.flappy();
			break;
		case GAMEOVER:
			newGame();
			break;
		default:
			break;
		}

	}

	private void playingGame() {
		bg1.updateBG();
		bg2.updateBG();
		for (int i = 0; i < columns.size(); i++) {
			Column column = columns.get(i);
			if (column.getX() + column.getW() < 0) {
				columns.remove(column);

				if (column.getY() == 0) {
					addColumn(false);
				}
			} else {
				column.update();				
			}
		}
		if (ticks % 2 == 0) // && bird.getyMotion() < maxYMotion)
		{
			if (bird.getyMotion() < maxYMotion)
				bird.updateYMotion();
		}

		checkBird();
		if (bird.getY() < 0) {
			bird.setyMotion(2);
		}

		bird.update();
	}
	
	private void pauseGame() {
		gameState = GameState.PAUSE;
		bird.stopAnimation();
	}
	
	private void resumeGame() {
		gameState = GameState.PLAYING;
	}
	
	private void checkBird() {
		int birdX1 = bird.getX() + 5;
		int birdX2 = bird.getX() + bird.getW() - 5;
		int birdY1 = bird.getY() + 5;
		int birdY2 = bird.getY() + bird.getH() - 5;

		for (int i = 0; i < columns.size(); i++) {
			Column column = columns.get(i);

			if (
					((bird.getX() + bird.getW() / 2) >= ((column.getX() + column.getW() / 2))
					&& (bird.getX() + bird.getW() / 2) <= ((column.getX() + column.getW() / 2)))
					&& column.getY() == 0) {
				score++;
				Factory.playSound(badingSound);
			}
			

			int colX1 = column.getX() + 5;
			int colX2 = column.getX() + column.getW() - 10;
			int colY1 = column.getY() + 5;
			int colY2 = column.getY() + column.getH() - 5;

			if (birdX1 < colX2 && birdX2 > colX1 && birdY1 < colY2 && birdY2 > colY1) {
				gameState = GameState.GAMEOVER;
				Factory.playSound(smackSound);
				return; // sound delay
			} else if (birdY2 >= (F_HEIGHT-bgHeight)) {
				gameState = GameState.GAMEOVER;
				Factory.playSound(smackSound);
				return; // sound delay
			}
		}
	}

	public void paintColumns(Graphics g) {
		for (Column column:columns) {
			if (column.getRotate() == 1) {
				g.drawImage(column.getImg(), column.getX(), column.getH() - column.getImg().getHeight(this), this);
			} else {
				g.drawImage(column.getImg(), column.getX(), column.getY(), this);
			}
		}
	}

	@Override
	public void Draw(Graphics g) {
		g.drawImage(bg3.getImg(), bg3.getBgX(), bg3.getBgY(), this);		
		paintColumns(g);
   		g.drawImage(bird.getImg(), bird.getX(), bird.getY(), this);		
		g.drawImage(bg1.getImg(), bg1.getBgX(), bg1.getBgY(), this);
		g.drawImage(bg2.getImg(), bg2.getBgX(), bg2.getBgY(), this);
		switch (gameState) {
		case VISUALIZING:
			drawFlappyBirdTittle(g);
			drawStartMessage(g);
			break;
		case WAITING:
			drawGetReady(g);
			break;
		case PLAYING:
			drawScore(g);
			drawPauseButton(g);
			break;
		case PAUSE:
			drawScore(g);
			drawPauseMessage(g);
			drawPauseButton(g);
			break;
		case GAMEOVER:
			drawGameOver(g);
			break;
		default:
			break;
		}

	}
	
	private void drawFlappyBirdTittle(Graphics g) {
		Image getReady = Factory.readImageFromSrc(Config.flappyBirdBanner);
		g.drawImage(getReady, (F_WIDTH/2 - getReady.getWidth(this)/2), (F_HEIGHT/10	), this);
	}

	private void drawPauseButton(Graphics g) {
		Image pauseImg = null;
		if (gameState == GameState.PLAYING) {
			pauseImg = Factory.readImageFromSrc(Config.pauseButton);
		} else {
			pauseImg = Factory.readImageFromSrc(Config.playingButton);
		}
		g.drawImage(pauseImg, btnPausePaddingLeft, btnPausePaddingTop, this);
	}

	private void drawGetReady(Graphics g) {
		Image getReady = Factory.readImageFromSrc(Config.getReady);
		g.drawImage(getReady, (F_WIDTH/2 - getReady.getWidth(this)/2), (F_HEIGHT/6), this);
	}

	private void createColumns() {
		for(int i = 0;i < 4 ;i++) {
			addColumn(true);
		}
	}

	private void drawScore(Graphics g) {
		g.setFont(playingFont);
		g.drawString(String.valueOf(score), F_WIDTH / 2 - 25, 100);
	}

	private void drawStartMessage(Graphics g) {
		g.setFont(font);
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
		int tw = (int) (font.getStringBounds(startGame, frc).getWidth());
		int th = (int) (font.getStringBounds(startGame, frc).getHeight());
		g.drawString(startGame, (F_WIDTH / 2) - tw / 2, (F_HEIGHT / 2) - th / 2 - bird.getH() / 2);
	}

	private void drawPauseMessage(Graphics g) {
		g.setFont(font);
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
		int tw = (int) (font.getStringBounds(pauseGame, frc).getWidth());
		int th = (int) (font.getStringBounds(pauseGame, frc).getHeight());
		g.setColor(Color.red);
		g.drawString(pauseGame, (F_WIDTH / 2) - tw / 2, (F_HEIGHT / 2) - th / 2 - bird.getH() / 2);
	}
	
	private void drawGameOver(Graphics g) {
		int moreW = 100;
		Font font2 = new Font("Monospaced", Font.BOLD, 35);
		g.setFont(font2);
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
		
		int tw = (int) (font.getStringBounds(gameOver, frc).getWidth());
		int th = (int) (font.getStringBounds(gameOver, frc).getHeight());

		g.drawString(Config.score, (F_WIDTH / 4) - tw / 2 + moreW+30, (F_HEIGHT / 2) - th / 2 + 50);
		g.drawString(Config.highScore, (F_WIDTH / 4) - tw / 2 + moreW+30, (F_HEIGHT /2) - th / 2 - bird.getH() / 2);
		
		String readScore = Factory.readScore();
		int scoreData = (readScore != "" ) ? Integer.parseInt(readScore) : 0;
		int highscore;
		if (score > scoreData) {
			Factory.writeScore(String.valueOf(score));
			highscore = score;
		} else {
			highscore = scoreData;
		}
		g.drawString(score+"", (F_WIDTH / 4) - tw / 2 + moreW*4+30, (F_HEIGHT / 2) - th / 2 + 50);
		g.drawString(highscore+"", (F_WIDTH / 4) - tw / 2 + moreW*4+30, (F_HEIGHT /2) - th / 2 - bird.getH() / 2);		
		
		g.setFont(font);
		g.setColor(Color.red);
		g.drawString(Config.gameOver, (F_WIDTH / 2) - tw / 2, (F_HEIGHT / 3) - th / 2 - bird.getH() / 2);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX(), y = e.getY();
		if (x >= btnPausePaddingLeft && x <= btnPausePaddingLeft + btnPauseWidth
				&& y >= btnPausePaddingTop && y <= btnPausePaddingTop + btnPauseHeight) {
			if (gameState == GameState.PLAYING) {
				pauseGame();
			} else if (gameState == GameState.PAUSE){
				resumeGame();
			}
		}
		
	}

}
