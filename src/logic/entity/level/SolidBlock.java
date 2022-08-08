package logic.entity.level;

import engine.model.ModelLoader;
import engine.model.RawModel;
import logic.entity.Hitbox2D;

public class SolidBlock extends Block {

	private Hitbox2D hitbox;
	
	public SolidBlock(int xCoord, int yCoord, ModelLoader modelLoader, RawModel rawModel) {
		super(rawModel, xCoord*UNIT, yCoord*UNIT);
		this.hitbox = createHitbox(xCoord, yCoord, 1, 1);
	}

	@Override
	public boolean intersects(Hitbox2D hit) {
		return hitbox.intersects(hit);
	}

}
