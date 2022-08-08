package logic;

import engine.model.ModelLoader;
import logic.entity.level.Player;
import org.joml.Vector3f;

import engine.Engine;
import engine.Logic;
import engine.gfx.Scene;
import logic.entity.level.LevelManager;

public class Platformer3D extends Logic{
	public static void main(String[] args) {
		Engine engine = new Engine(new Platformer3D());
		engine.start();
	}
	protected Platformer3D() {super(960, 640, "3DPlattformer",
			new Scene(new GameCamera(), new GameLight(new Vector3f(200,-200,100), new Vector3f(1,1,1))));}
	
	private LevelManager levelManager;
	
	private GameCamera camera;
	private GameLight mainLight;

	private Player player;
	
	@Override
	public void init() {
		Scene scene = renderer.getScene();
		this.camera = (GameCamera)scene.getCamera();
		this.mainLight = (GameLight)scene.getLight();
		
		this.levelManager = new LevelManager(modelLoader, camera, WIDTH, HEIGHT, new Vector3f((WIDTH-HEIGHT)/2f,0,0), super.renderer);
		this.player = new Player(0,0,new ModelLoader(),this.levelManager, null, this.levelManager.getBlockShader());

		super.setKeyCallback(new GameInput(this.levelManager));
}
	
	@Override
	public void tick() {
		this.camera.update();
		this.mainLight.updatePosition(this.camera.getRoll());
		this.levelManager.tick();
		this.player.tick();
	}

	@Override
	protected void render() {
		renderer.clearBuffers();
		this.levelManager.render(renderer);
		this.player.render(renderer);
	}

	@Override
	protected void cleanUp() {
		this.levelManager.cleanUp();
	}

}
