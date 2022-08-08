package logic.entity.level;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import engine.gfx.LinMath;
import engine.gfx.Renderer;
import engine.gfx.Renderer.StaticShader;
import engine.gfx.fbos.CaptureFBO;
import engine.model.ModelBuilder;
import engine.model.ModelLoader;
import engine.model.RawModel;
import engine.objLoader.OBJFileLoader;
import engine.textures.Texture;

public class LevelMenue {
	private Level[] levels;
	private RawModel menueModel;
	private RawModel picFrame;
	private int menueWidth, menueHeight;
	private BlockShader blockShader;
	private MenuePart part;
	
	public LevelMenue(Level[] levels, int menueWidth, int menueHeight, ModelLoader modelLoader, BlockShader blockShader, Renderer renderer){
		this.levels = levels;
		this.menueWidth = menueWidth;
		this.menueHeight = menueHeight;
		this.blockShader = blockShader;
		this.menueModel = OBJFileLoader.loadOBJ("./models/levelMenue.obj", modelLoader);
		this.picFrame = ModelBuilder.createTexQuad2D(160, 160, modelLoader);
		this.part = new MenuePart(levels, 0, 160, renderer);
	}
	
	public void render(Renderer renderer) {
		part.render(renderer);
	}
	
	private class MenuePart{
		private Level[] selection;
		private Texture[] selection_prev;
		private MenuePart(Level[] levels, int off, int imgRes, Renderer renderer) {
			int selectionLen = 4;
			this.selection_prev = new Texture[selectionLen];
			this.selection = new Level[selectionLen];
			for(int i = 0; i < selectionLen; i++) {
				this.selection[i] = levels[off+i];
				CaptureFBO cap = new CaptureFBO(imgRes, imgRes);
				cap.bind();
				renderer.prepare3DScene();
				renderer.bindShader(blockShader);
				renderer.startShaderProgram();
				blockShader.loadColor(1, 1, 1);
				levels[off+i].render(renderer);
				cap.unbind(menueWidth, menueHeight);
				this.selection_prev[i] = new Texture(cap.getCaptureTextureID());
				cap.cleanUp();
			}
		}
		
		private void render(Renderer renderer) {
			renderer.bindShader(blockShader);
			blockShader.start();
			blockShader.loadColor(1, 1, 1);
			blockShader.loadTransformationMatrix(LinMath.createTransformationMatrix(
					new Vector3f(0,0,-9), new Vector3f(), 1f));
			renderer.render(menueModel);
			
			renderer.getStaticTexureShader().loadTransformation(new Vector3f(0,0,0), new Vector3f(), 1f);
			renderer.render_RAW_TEX(picFrame, selection_prev[0].getTexID());
		}
		private void cleanUp() {
			for (Texture tex : selection_prev) {
				GL11.glDeleteTextures(tex.getTexID());
			}
		}
		
	}
	
	private void cleanUp() {
		part.cleanUp();
	}
	
	
}
