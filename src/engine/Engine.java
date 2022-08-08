package engine;

import engine.input.KeyCallback;

public final class Engine {
	
	private Logic logic;
	private Window window;
	
	public Engine(Logic logic) {
		this.logic = logic;
		Window.initalizeGLFW();
		this.window = new Window(logic.getWidth(), logic.getHeight(), logic.getTitle());
		this.logic.setEngine(this);
		this.logic._init();
	}
	
	public void start() {
		Clock.init(this,72);
		while(!window.shouldClose()) {
			window.pollEvents();
			Clock.update();
			window.swapBuffers();
		}
		cleanUp();
	}
	
	public void tick() {
		this.window.updateInput();
		this.logic.tick();
		this.logic._render();
	}
	
	public void setKeyCallback(KeyCallback keyCallback) {
		this.window.addKeyCallback(keyCallback);
	}
	
	private void cleanUp() {
		this.logic._cleanUp();
		this.window.terminate();
		System.exit(0);
	}
	
}
