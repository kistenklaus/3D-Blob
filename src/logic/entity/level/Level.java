package logic.entity.level;

import org.joml.Vector3f;

import engine.gfx.Renderer;
import engine.input.Key;
import logic.GameCamera;
import logic.entity.Hitbox2D;

public class Level {
	
	private Block[] blocks;
	private BlockShader blockShader;
	private GameCamera camera;
	private final float GRAVITY;
	private float rotation;
	
	public Level(Block[] blocks, BlockShader staticShader, GameCamera camera) {
		this.blocks = blocks;
		this.blockShader = staticShader;
		this.GRAVITY = 0.02f;
		this.camera = camera;
		this.rotation = 0f;
	}
	
	public void render(Renderer renderer) {
		for(int i = 0; i < blocks.length; i++) {
			this.blockShader.loadColor(this.blocks[i].getColor());
			renderer.bindShader(this.blockShader);
			renderer.render(this.blocks[i]);
		}
	}
	
	public boolean intersects(Hitbox2D hit) {
		for(int i = 0; i < this.blocks.length; i++) {
			if(this.blocks[i].intersects(hit)){
				return true;
			}
		}
		return false;
	}
	
	public Vector3f getGravity() {
		return new Vector3f(
				-GRAVITY * Math.round(Math.sin(Math.toRadians(rotation))),
				-GRAVITY * Math.round(Math.cos(Math.toRadians(rotation))),
				0);
	}
	private void rotate(float dr) {
		this.rotation += dr;
		if(this.rotation>=360) {
			this.rotation-=360;
		}else if(this.rotation<0){
			this.rotation+=360;
		}
		this.camera.setPrefZRotation(this.rotation);
	}
	public float getRotation() {
		return this.rotation;
	}

	public void keyPressed(int key) {
		if(key==Key.D) {
			rotate(-90);
		}if(key==Key.A) {
			rotate(90);
		}
	}
}
