package engine;

import engine.gfx.Renderer;
import engine.gfx.Scene;
import engine.input.KeyCallback;
import engine.model.ModelLoader;

public abstract class Logic {
	protected final int WIDTH, HEIGHT;
	protected final String TITLE;
	protected final ModelLoader modelLoader;
	protected final Renderer renderer;
	private Engine engine;

	protected Logic(final int WIDTH, final int HEIGHT, final String TITLE, Scene scene) {
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.TITLE = TITLE;
		this.modelLoader = new ModelLoader();
		this.renderer = new Renderer(scene, WIDTH, HEIGHT);
	}

	public abstract void init();

	public void _init() {
		this.renderer.init();
		init();
	}
	
	public abstract void tick();

	protected abstract void render();

	public void _render() {
		render();
	}

	protected abstract void cleanUp();

	public void _cleanUp() {
		this.modelLoader.cleanUp();
		cleanUp();
	}

	public int getWidth() {
		return this.WIDTH;
	}

	public int getHeight() {
		return this.HEIGHT;
	}

	public String getTitle() {
		return this.TITLE;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}
	
	public void setKeyCallback(KeyCallback keyCallback) {
		this.engine.setKeyCallback(keyCallback);
	}
}
