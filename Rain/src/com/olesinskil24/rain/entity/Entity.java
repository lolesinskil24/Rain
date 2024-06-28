package com.olesinskil24.rain.entity;

import java.util.Random;

import com.olesinskil24.rain.graphics.Screen;
import com.olesinskil24.rain.level.Level;

public class Entity {
	public int x, y;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	
	public void update() {
		
	}
	
	public void render(Screen screen) {
		
	}
}
