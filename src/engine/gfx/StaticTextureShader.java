package engine.gfx;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import engine.model.Entity;

public class StaticTextureShader extends Shader{
	private static final String VS_FILE = "./shaders/StaticTextureVertex.vs", FS_FILE = "./shaders/StaticTextureFragement.fs";
	
	private int location_screen;
	private int location_trans;
	
	public StaticTextureShader(int width, int height) {
		super(VS_FILE, FS_FILE);
		super.start();
		super.loadMat4(location_screen, LinMath.getProjectionMatrix(width, height, Renderer.FOV, Renderer.NEAR, Renderer.FAR));
		super.stop();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoord");
	}

	@Override
	protected void getAllUniformLocations() {
		this.location_screen = super.getUniformLocation("screen");
		this.location_trans = super.getUniformLocation("trans");
	}
	
	public void loadTransformation(Matrix4f trans) {
		super.loadMat4(this.location_trans, trans);
	}
	public void loadTransformation(Vector3f pos, Vector3f rotation, float scale) {
		super.loadMat4(this.location_trans, LinMath.createTransformationMatrix(pos, rotation, scale));
	}

	@Override
	public void prepareEntity(Entity entity) {
		
	}

	@Override
	public void prepareScene(Scene scene) {
		
	}

}
