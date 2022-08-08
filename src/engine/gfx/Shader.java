package engine.gfx;

import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import engine.model.Entity;

public abstract class Shader {
	
	private int programID, vsID, fsID;
	
	public Shader(String vsFile, String fsFile) {
		vsID = loadShader(vsFile, GL20.GL_VERTEX_SHADER);
		fsID = loadShader(fsFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vsID);
		GL20.glAttachShader(programID, fsID);
		bindAttributes();
		glLinkProgram(programID);
		if(glGetProgrami(programID, GL_LINK_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(programID));
			System.exit(-1);
		}
		glValidateProgram(programID);
		if(glGetProgrami(programID, GL_VALIDATE_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(programID));
			System.exit(-1);
		}
		getAllUniformLocations();
	}
	
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attribute, String variableName) {
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}
	
	protected abstract void getAllUniformLocations();
	
	protected int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(this.programID, uniformName);
	}
	protected void loadBoolean(int location, boolean bool) {
		System.err.println("Check if correct Unifromfunction for booleans");
		GL20.glUniform1f(location, bool?0:1);
	}
	protected void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}
	protected void loadVec3(int location, Vector3f value) {
		GL20.glUniform3f(location, value.x, value.y, value.z);
	}
	protected void loadVec3(int location, float x, float y, float z) {
		GL20.glUniform3f(location, x,y,z);
	}
	protected void loadMat4(int location, Matrix4f value) {
		GL20.glUniformMatrix4fv(location, false, value.get(new float[16]));
	}
	public abstract void prepareEntity(Entity entity);
	public abstract void prepareScene(Scene scene);
	public void start() {
		GL20.glUseProgram(programID);
	}
	public void stop() {
		GL20.glUseProgram(0);
	}
	
	public void cleanUp() {
		stop();
		GL20.glDetachShader(programID, vsID);
		GL20.glDetachShader(programID, fsID);
		GL20.glDeleteShader(vsID);
		GL20.glDeleteShader(fsID);
		GL20.glDeleteProgram(programID);
	}
	
	private static int loadShader(String file, int type){
        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader: " + file);
            System.exit(-1);
        }
        return shaderID;
    }
}
