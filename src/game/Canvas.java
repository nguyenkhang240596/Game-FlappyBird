package game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

abstract public class Canvas extends JPanel implements KeyListener, MouseListener, ActionListener {
	
	public Canvas()
    {
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setBackground(Color.black);     
        this.addKeyListener(this);
        this.addMouseListener(this);
    }
	    
	public abstract void Draw(Graphics g);
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);        
        Draw(g);
    }
    
	@Override
	public void keyPressed(KeyEvent arg0) { }

	@Override
	public void keyTyped(KeyEvent arg0) { }

    @Override
    public void mouseReleased(MouseEvent e) { }
    
    @Override
	public void mousePressed(MouseEvent arg0) {}
    @Override
    public void mouseEntered(MouseEvent e) { }
    
    @Override
    public void mouseExited(MouseEvent e) { }
    
    
}
