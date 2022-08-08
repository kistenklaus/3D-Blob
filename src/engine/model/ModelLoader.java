package engine.model;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import engine.objLoader.ModelData;
import engine.textures.Texture;

public class ModelLoader {
	
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> texs = new ArrayList<Integer>();
	
	public Texture loadTexture(String filename) {
		BufferedImage bi = null;
		try {
			bi=ImageIO.read(new File(filename));
		} catch (IOException e) {e.printStackTrace();}
		int width = bi.getWidth();
		int height = bi.getHeight();
		int[] pixels_raw = new int[width*height];
		pixels_raw = bi.getRGB(0, 0, width, height, null, 0, width);
		
		ByteBuffer pixels = BufferUtils.createByteBuffer(width*height*4);
		for(int j = 0; j < width; j++) {
			for(int i = 0; i < height; i++) {
				int pixel = pixels_raw[i*width+j];
				pixels.put((byte) ((pixel >> 16) & 0xFF)); //RED
				pixels.put((byte) ((pixel >> 8) & 0xFF));  //GREEN
				pixels.put((byte) (pixel & 0xFF)); 		   //BLUE
				pixels.put((byte) ((pixel >> 24) & 0xFF)); //ALPHA
			}
		}
		pixels.flip();
		
		int texID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texID);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height,
				0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		texs.add(texID);
		return new Texture(texID);
	}
	
	public RawModel loadObjToVao(ModelData data) {
		return loadPINToVoa(data.getVertices(), data.getIndices(), data.getNormals());
	}
	
	public RawModel loadPIToVoa(float[] positions, int[] indicies) {
		int vaoID = createVAO();
		bindIndiciesBuffer(indicies);
		storeDataInAttributList(0, 3, positions);
		unbindVAO();
		return new RawModel(vaoID, indicies.length);
	}
	
	public RawModel loadPITToVoa(float[] positions, float[] uvCoords, int[] indicies) {
		int vaoID = createVAO();
		bindIndiciesBuffer(indicies);
		storeDataInAttributList(0, 3, positions);
		storeDataInAttributList(1, 2, uvCoords);
		unbindVAO();
		return new RawModel(vaoID, indicies.length);
	}
	
	public RawModel loadPINToVoa(float[] positions, int[] indicies, float[] normals) {
		int vaoID = createVAO();
		bindIndiciesBuffer(indicies);
		storeDataInAttributList(0, 3, positions);
		storeDataInAttributList(1, 3, normals);
		unbindVAO();
		return new RawModel(vaoID, indicies.length);
	}
	
	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	private void storeDataInAttributList(int attributeNumber, int dimensions , float[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, storeDataInBuffer(data), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, dimensions, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private void bindIndiciesBuffer(int[] indicies) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, storeDataInBuffer(indicies), GL15.GL_STATIC_DRAW);
	}
	
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	public void cleanUp() {
		for (int vao : vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		for (int vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		for (int tex : texs) {
			GL11.glDeleteTextures(tex);
		}
	}
	
	private FloatBuffer storeDataInBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	private IntBuffer storeDataInBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
}
