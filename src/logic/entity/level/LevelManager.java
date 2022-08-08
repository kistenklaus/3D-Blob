package logic.entity.level;

import java.io.File;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import engine.gfx.LinMath;
import engine.gfx.Renderer;
import engine.gfx.Renderer.StaticShader;
import engine.gfx.fbos.CaptureFBO_AA;
import engine.model.ModelBuilder;
import engine.model.ModelLoader;
import engine.model.RawModel;
import logic.GameCamera;

public class LevelManager {
	private Player player;
	private Level[] levels;
	private int activeLevel = 0;
	private BlockShader blockShader;
	private CaptureFBO_AA levelCapture;
	private RawModel rawModel;
	private Vector3f pos;
	private LevelMenue levelMenue;
	
	public LevelManager(ModelLoader modelLoader, GameCamera camera, int width, int height, Vector3f pos, Renderer renderer) {
		Matrix4f projection = LinMath.getProjectionMatrix(width, height, Renderer.FOV, Renderer.NEAR, Renderer.FAR);
		this.blockShader = new BlockShader(projection);
		this.player = new Player(10, 20, modelLoader, this, camera, blockShader);
		File levelRes = new File("./levels/");
		if(!levelRes.exists())System.err.println("Level directory dosent Exist !!!");

		int levelCount = levelRes.listFiles().length;
		this.levels = new Level[levelCount];
		for(int i = 0 ; i < levelCount; i++) {
			levels[i] = LevelLoader.loadLevel("./levels/level" + i, blockShader, camera, modelLoader);
		}
		this.levelCapture = new CaptureFBO_AA(width, height, 4);
		this.rawModel = ModelBuilder.createTexQuad2D(width, height, modelLoader);
		this.pos = pos;
		
		
		this.levelMenue = new LevelMenue(levels, 960, 640, modelLoader, blockShader, renderer);
		
	}
	
	public void tick() {
		this.player.tick();
	}
	
	private void renderLevelFBO(Renderer renderer) {
		levelCapture.bind();
		GL11.glClearColor(0.15f, 0.15f, 0.15f, 1);
		renderer.prepare3DScene();
		renderer.clearBuffers();
		renderer.bindShader(blockShader);
		this.levels[activeLevel].render(renderer);
		this.player.render(renderer);
		levelCapture.blintImage();
		levelCapture.unbind(renderer.getWIDTH(), renderer.getHEIGHT());
	}
	
	private void renderActiveLevel(Renderer renderer) {
		renderLevelFBO(renderer);
		GL11.glClearColor(0, 0, 0, 1f);
		renderer.prepare3DScene();
		renderer.clearBuffers();
		renderer.bindStaticShader(StaticShader.TEXTURE);
		renderer.getStaticTexureShader().start();
		renderer.getStaticTexureShader().loadTransformation(this.pos, new Vector3f(), 1f);
		renderer.render_RAW_TEX(rawModel, levelCapture.getCaptureTextureID());
		renderer.unbindShader();
	
	}
	
	public void render(Renderer renderer) {
		this.levels[activeLevel].render(renderer);
//		renderer.prepare3DScene();
//		renderer.clearBuffers();
//		renderActiveLevel(renderer);
//		GL11.glClearColor(0.1f, 0.1f, 0.1f, 1f);
//		renderer.clearBuffers();
//		renderer.bindShader(blockShader);
//		renderer.prepare3DScene();
//		levelMenue.render(renderer);
	}
	
	public Level getActiveLevel() {
		return this.levels[activeLevel];
	}

	public void keyPressed(int key) {
		this.levels[activeLevel].keyPressed(key);
	}

	public void cleanUp() {
		this.levelCapture.cleanUp();
		
	}

	public BlockShader getBlockShader(){
		return this.blockShader;
	}


}
