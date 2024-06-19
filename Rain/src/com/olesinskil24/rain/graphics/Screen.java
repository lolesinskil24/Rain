package com.olesinskil24.rain.graphics;

import java.util.Random;

import com.olesinskil24.rain.level.tile.Tile;

public class Screen {

	public int width, height;
	public int[] pixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	
	private Random random = new Random();
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		
		pixels = new int[width * height]; // 0 - 50,399 = 50,400
		
		for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
			tiles[i] = random.nextInt(0xffffff);
			tiles[0] = 0;
		}
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void render(int xOffset, int yOffset) {
		for (int y = 0; y < height; y++) {
			int yPixel = y + yOffset;
			if (yPixel < 0 || yPixel >= height) continue;
			for (int x = 0; x < width; x++) {
			int xPixel = x + xOffset;
				if (xPixel < 0 || xPixel >= width) continue;
//				int tileIndex = ((xx >> 4) & MAP_SIZE_MASK) + ((yy >> 4) & MAP_SIZE_MASK) * MAP_SIZE;
				pixels[xPixel + yPixel * width] = Sprite.grass.pixels[(x&15) + (y&15) * Sprite.grass.SIZE];
			}
		}
	}
	
	public void renderTile(int xPixel, int yPixel, Tile tile) {
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int yAbsolute = y + yPixel;
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int xAbsolute = x + xPixel;
				if (xAbsolute < 0 || xAbsolute >= width || yAbsolute < 0 || yAbsolute >= width) break;
				pixels[xAbsolute + yAbsolute*width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
			}
		}
	}
	
}