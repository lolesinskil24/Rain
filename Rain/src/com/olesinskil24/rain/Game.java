package com.olesinskil24.rain;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.olesinskil24.rain.graphics.Screen;
import com.olesinskil24.rain.input.Keyboard;

public class Game extends Canvas implements Runnable {
	// What is this for??????
	private static final long serialVersionUID = 1L;
	
	//screen characteristics
	public static int width = 300;
	public static int height = width / 16 * 9;
	public static int scale = 3;
	public static String title = "Rain";
	
	//computer process
	private Thread thread;
	
	//interactable window
	private JFrame frame;
	
	private Keyboard key;
	
	private boolean running = false;
	
	//reference to screen class
	private Screen screen;
	
	//creating an image
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	
	//creating a way to access the image
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	//class constructor called once at start
	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		
		screen = new Screen(width, height);
		
		frame = new JFrame();
		
		key = new Keyboard();
		
		addKeyListener(key);
		
		//request focus for the Canvas
		setFocusable(true);
		requestFocus();
	}

	//synchronized so that the threads don't overlap?
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	//waits for other threads to finish running
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 0) {
				timer += 1000;
				frame.setTitle(title + " | " + updates + " ups, " + frames + " fps ");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}

	int x = 0, y = 0;
	
	public void update() {
		key.update();
		if (key.up) y--;
		if (key.down) y++;
		if (key.left) x--;
		if (key.right) x++;
		
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		screen.render(x, y);
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		//We have our thing to draw on, but now we actually have to draw to it..
		Graphics g = bs.getDrawGraphics();
		//g.setColor(Color.BLACK);
		//g.fillRect(0, 0,  getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Game game = new Game();
		//always use first
		game.frame.setResizable(false);
		game.frame.setTitle(Game.title);
		//adding a component to the window (game is a component because it extends canvas)
		game.frame.add(game);
		//sets frame to size of preferredSize()
		game.frame.pack();
		//we want to close our process when we close the window (not just close the window)
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//makes window go to the center of the screen relative to the size
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();
		
	}

}
