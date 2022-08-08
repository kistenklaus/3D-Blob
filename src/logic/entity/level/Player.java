package logic.entity.level;

import org.joml.Vector3f;

import engine.gfx.Camera;
import engine.gfx.Renderer;
import engine.model.Entity;
import engine.model.ModelLoader;
import engine.objLoader.OBJFileLoader;
import logic.entity.Hitbox2D;

public class Player extends Entity {

	private static final Vector3f SPAWN = new Vector3f(0,0,-Block.STAGE_DEPTH);
	

	private LevelManager levelManager;
	private Hitbox2D hitbox;
	
	private Vector3f vel;
	
	private BlockShader blockShader;


	public Player(int xCoord, int yCoord, ModelLoader modelLoader, 
			LevelManager levelManager, Camera camera, BlockShader blockShader) {
		super(OBJFileLoader.loadOBJ("./models/player.obj", modelLoader),
				new Vector3f(SPAWN),
				new Vector3f(), Block.UNIT);
		this.vel = new Vector3f();
		this.hitbox = new Hitbox2D(super.pos.x - Block.UNIT/2f, super.pos.y - Block.UNIT / 2f,
				Block.UNIT,
				Block.UNIT*.999f);
		this.levelManager = levelManager;
		this.blockShader = blockShader;
	}

	public void tick() {
		Level level = levelManager.getActiveLevel();
		vel.add(level.getGravity());

		// checks for any Collision and if non moves the player
		final int ACCURACY = 100;
		float step = 1f/ACCURACY;
		float rx = Math.round((super.pos.x - Block.UNIT/2f)*ACCURACY)/(float)ACCURACY;
		float ty = Math.round((super.pos.y - Block.UNIT/2f)*ACCURACY)/(float)ACCURACY;
		Vector3f dvel = new Vector3f();
		boolean negX = this.vel.x<0;
		float dx = negX?-step:step;
		for(float vx = 0; ((negX&&vx >= this.vel.x)
				|| (!negX && vx <= this.vel.x)	)
				&&this.vel.x!=0;vx+=dx){
			hitbox.setLocation(rx+vx, ty);
			if(!level.intersects(hitbox)) {
				dvel.x+=dx;
			}else {
				dvel.x-=dx;
				this.vel.x = 0;
				break;
			}
		}
				
				
		boolean negY = this.vel.y<0;
		float dy = negY?-step:step;
		for(float vy = 0; ((negY && vy >= this.vel.y)
				|| (!negY && vy <= this.vel.y))
				&&this.vel.y!=0; vy+=dy) {
			hitbox.setLocation(rx, ty+vy);
			if(!level.intersects(hitbox)){
				dvel.y+=dy;
			}else {
				dvel.y-=dy;
				this.vel.y = 0;
				break;
			}
		}
		super.pos.add(dvel);
	}

	public void render(Renderer renderer) {
		this.blockShader.loadColor(1, 0, 0);
		renderer.bindShader(this.blockShader);
		renderer.render(this);
	}

}
