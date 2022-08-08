package engine;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL13;

import engine.input.KeyCallback;

public final class Window {
	
	public static void initalizeGLFW() {
		if(!glfwInit()) {
			throw new IllegalStateException("Failed initalizing GLFW");
		}
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
	}
	
	private long window;
	private KeyCallback keyCallback;
	
	public Window(final int WIDTH, final int HEIGHT, final String TITLE) {
		
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_SAMPLES, 4);
		
		this.window = glfwCreateWindow(WIDTH, HEIGHT, TITLE, 0,0);
		if(this.window == 0) {
			throw new IllegalStateException("Failed initalizing the GLFW Window");
		}
		GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(this.window, (vidMode.width()-WIDTH)/2, (vidMode.height()-HEIGHT)/2);
		
		
		glfwMakeContextCurrent(this.window);
		
		GL.createCapabilities();
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glEnable(GL13.GL_MULTISAMPLE);  
		
		glfwShowWindow(this.window);
	}
	
	public void setKeyCallback(GLFWKeyCallback keyCallback) {
		glfwSetKeyCallback(this.window, keyCallback);
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(this.window);
	}
	public void terminate() {
		glfwTerminate();
	}
	public void pollEvents() {
		glfwPollEvents();
	}
	public void swapBuffers() {
		glfwSwapBuffers(this.window);
	}
	public void updateInput() {
		if(this.keyCallback!=null) {
			this.keyCallback.update();
		}
	}

	public void addKeyCallback(KeyCallback keyCallback) {
		this.keyCallback = keyCallback;
		glfwSetKeyCallback(this.window, keyCallback);
	}
	
}
