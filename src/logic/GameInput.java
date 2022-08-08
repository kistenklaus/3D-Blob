package logic;

import engine.input.KeyCallback;
import logic.entity.level.LevelManager;

public class GameInput extends KeyCallback{
	
	private LevelManager levelManager;

	public GameInput(LevelManager levelManager) {
		this.levelManager = levelManager;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void keyPressed(int key, int mod) {
		this.levelManager.keyPressed(key);
	}

	@Override
	public void keyReleased(int key, int mod) {
		
	}
	
}
