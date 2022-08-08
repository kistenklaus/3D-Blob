package engine.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public abstract class KeyCallback extends GLFWKeyCallback{
	
	protected boolean[] keys;
	protected int mod;
	
	public KeyCallback() {
		this.keys = new boolean[GLFW.GLFW_KEY_LAST];
		this.mod = 0;
	}
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mod) {
		this.keys[key] = action!=0;	
		this.mod = mod;
		if(action==1) {
			keyPressed(key, mod);
		}else if(action==0) {
			keyReleased(key, mod);
		}
	}
	
	public boolean isKeyDown(int key) {
		return this.keys[key];
	}
	
	public int getMod() {
		return this.mod;
	}
	
	public abstract void keyPressed(int key, int mod);
	public abstract void keyReleased(int key, int mod);
	
	public abstract void update();

}
