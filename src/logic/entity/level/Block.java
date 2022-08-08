package logic.entity.level;

import org.joml.Vector3f;

import engine.model.Entity;
import engine.model.RawModel;
import logic.entity.Hitbox2D;

public abstract class Block extends Entity{

	public final static float UNIT = 2.5f;
	public final static float STAGE_DEPTH = 30;
	
	private Vector3f color;
	
	public Block(RawModel rawModel, float posx, float posy) {
		super(rawModel, new Vector3f(posx, posy, -STAGE_DEPTH), new Vector3f(), UNIT);
		this.color = new Vector3f(1,1,1);
	}
	
	public abstract boolean intersects(Hitbox2D hit);
	
	protected Hitbox2D createHitbox(int xCoord, int yCoord, int blockWidth, int blockHeight) {
		float posx = xCoord*UNIT+UNIT*blockWidth/2-UNIT/2;
		float posy = yCoord*UNIT-UNIT*blockHeight/2+UNIT/2;
		return new Hitbox2D(posx - (UNIT*blockWidth)/2f, posy - (UNIT*blockHeight)/2f, UNIT*blockWidth, UNIT*blockHeight);
	}
	
	public Vector3f getColor() {
		return this.color;
	}
}
