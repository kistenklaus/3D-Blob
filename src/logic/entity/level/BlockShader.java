package logic.entity.level;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import engine.gfx.Camera;
import engine.gfx.Light;
import engine.gfx.LinMath;
import engine.gfx.Scene;
import engine.gfx.Shader;
import engine.model.Entity;

public class BlockShader extends Shader{
	
	private static final String VERTEX_FILE = "./shaders/BlockVertex.vs",
								FRAGMENT_FILE = "./shaders/BlockFragment.fs";
	
	private int location_color;
	private int location_transform;
	private int location_view;
	private int location_projection;
	private int location_lightPosition;
	private int location_lightColor;
	
	public BlockShader(Matrix4f projection) {
		super(VERTEX_FILE, FRAGMENT_FILE);
		this.start();
		loadMat4(this.location_projection, projection);
		this.stop();
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
		bindAttribute(1, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		this.location_color = super.getUniformLocation("color");
		this.location_transform = super.getUniformLocation("transform");
		this.location_view = super.getUniformLocation("view");
		this.location_projection = super.getUniformLocation("projection");
		this.location_lightPosition = super.getUniformLocation("lightPosition");
		this.location_lightColor = super.getUniformLocation("lightColor");
	}
	public void loadColor(float r, float g, float b) {
		super.loadVec3(this.location_color, r,g,b);
	}
	public void loadColor(Vector3f color) {
		super.loadVec3(this.location_color, color);
	}
	public void loadTransformationMatrix(Matrix4f transform) {
		super.loadMat4(this.location_transform, transform);
	}
	public void loadLight(Light light) {
		super.loadVec3(this.location_lightPosition, light.getPos());
		super.loadVec3(this.location_lightColor, light.getColor());
	}
	public void loadCamera(Camera camera) {
		super.loadMat4(this.location_view, LinMath.createViewMatrix(camera));
	}

	@Override
	public void prepareEntity(Entity entity) {
		Matrix4f trans = LinMath.createTransformationMatrix(
				entity.getPos(),entity.getRotation(), entity.getScale());
		loadTransformationMatrix(trans);
	}

	@Override
	public void prepareScene(Scene scene) {
		loadLight(scene.getLight());
		loadCamera(scene.getCamera());
	}
}
