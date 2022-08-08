package engine.gfx;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import engine.model.Entity;
import engine.model.RawModel;

public class Renderer {

	public static final float FOV = 70;
	public static final float NEAR = 0.1f;
	public static final float FAR = 1000f;
	
	private Shader activeShader;
	private Scene scene;
	private StaticColorShader staticColorShader;
	private StaticTextureShader staticTextureShader;
	private Matrix4f projection;
	private final int WIDTH,HEIGHT;
	
	public void clearBuffers() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		unbindShader();
	}
	
	public void prepare3DScene() {
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	public Renderer(Scene scene, int WIDTH, int HEIGHT) {
		this.scene = scene;
		this.projection = LinMath.getProjectionMatrix(WIDTH, HEIGHT, FOV, NEAR, FAR);
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
	}
	
	public void init() {
		this.staticColorShader = new StaticColorShader(projection);
		this.staticTextureShader = new StaticTextureShader(WIDTH,HEIGHT);
	}

	public void bindShader(Shader shader) {
		if (this.activeShader != shader) {
			this.activeShader = shader;
			this.activeShader.start();
			this.activeShader.prepareScene(scene);
		}
	}
	public enum StaticShader{
		COLOR,TEXTURE;
	}
	
	public void bindStaticShader(StaticShader type) {
		if(type == StaticShader.COLOR) {
			this.activeShader = staticColorShader;
		}else if(type == StaticShader.TEXTURE) {
			this.activeShader = staticTextureShader;
		}
	}

	public void unbindShader() {
		if (this.activeShader != null) {
			this.activeShader.stop();
			this.activeShader = null;
		}
	}
	
	public void startShaderProgram() {
		this.activeShader.start();
	}
	public void stopShaderProgram() {
		this.activeShader.start();
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public Scene getScene() {
		return this.scene;
	}
	
	public int getWIDTH() {
		return this.WIDTH;
	}
	
	public int getHEIGHT() {
		return this.HEIGHT;
	}
	public Shader getActiveShader() {
		return this.activeShader;
	}
	
	public StaticColorShader getStaticColorShader() {
		return this.staticColorShader;
	}
	public StaticTextureShader getStaticTexureShader() {
		return this.staticTextureShader;
	}

	public Matrix4f getProjectionMatrix() {
		return this.projection;
	}
	
	public void render(Entity entity) {
		this.activeShader.start();
		this.activeShader.prepareEntity(entity);
		RawModel rawModel = entity.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	public void render(RawModel rawModel) {
		this.activeShader.start();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}

	public void render_TEX(Entity entity, int texID) {
		this.activeShader.start();
		this.activeShader.prepareEntity(entity);
		RawModel rawModel = entity.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	public void render_RAW_TEX(RawModel rawModel, int texID) {
		this.activeShader.start();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}

	// TEXTURED SHIT
//	public void render(Entity entity) {
//		RawModel rawModel = entity.getRawModel();
//		GL30.glBindVertexArray(rawModel.getVaoID());
//		GL20.glEnableVertexAttribArray(0);
//		GL20.glEnableVertexAttribArray(1);
//		GL13.glActiveTexture(GL13.GL_TEXTURE0);
//		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTexID());
//		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
//		GL20.glDisableVertexAttribArray(0);
//		GL20.glDisableVertexAttribArray(1);
//		GL30.glBindVertexArray(0);
//	}
}
