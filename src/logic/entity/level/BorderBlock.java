package logic.entity.level;

import engine.model.ModelLoader;
import engine.objLoader.OBJFileLoader;
import logic.entity.Hitbox2D;

public class BorderBlock extends Block{

	private Hitbox2D bottom;
	private Hitbox2D top;
	private Hitbox2D right;
	private Hitbox2D left;
	
	public BorderBlock(int xSpace, int ySpace, ModelLoader modelLoader) {
		super(OBJFileLoader.loadOBJ("./models/blockModels/border"+xSpace+"x"+ySpace+".obj",modelLoader),
				0,0);
		this.bottom = createHitbox(-(int)Math.floor(xSpace/2f), -(int)Math.ceil(ySpace/2f), xSpace, 1);
		this.right = createHitbox((int)Math.ceil(ySpace/2f), (int)Math.floor(xSpace/2f), 1, ySpace);
		this.top = createHitbox(-(int)Math.floor(xSpace/2f), (int)Math.ceil(ySpace/2f), xSpace, 1);
		this.left = createHitbox(-(int)Math.ceil(ySpace/2f), (int)Math.floor(xSpace/2f) , 1, ySpace);
	}

	@Override
	public boolean intersects(Hitbox2D hit) {
		return (this.bottom.intersects(hit) ||
				this.right.intersects(hit) ||
				this.top.intersects(hit) ||
				this.left.intersects(hit));
	}

}
