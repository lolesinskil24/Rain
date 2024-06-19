package com.olesinskil24.rain.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {

	private String path;
	public final int SIZE;
	public int[] pixels;
	
	public static Spritesheet tiles = new Spritesheet("/textures/spritesheet.png", 256);
	
	public Spritesheet(String path, int size) {
		this.path = path;
		this.SIZE = size;
		pixels = new int[SIZE * SIZE];
		load();
	}
	
	public void load() {
		try {
			BufferedImage image  = ImageIO.read(Spritesheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			
			//translates image above into pixels array
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
