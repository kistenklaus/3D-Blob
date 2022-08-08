package engine.gfx;

import org.joml.Vector3f;

public class Camera {
	private Vector3f pos;
	private Vector3f rotation;

	public Camera(Vector3f pos, Vector3f rotation) {
		super();
		this.pos = pos;
		this.rotation = rotation;
	}
	public Camera() {
		super();
		this.pos = new Vector3f();
		this.rotation = new Vector3f();
	}
	
	public void resetPos() {
		this.pos = new Vector3f();
	}

	public Vector3f getPos() {
		return this.pos;
	}

	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	public Vector3f move(Vector3f delta) {
		this.pos.add(delta);
		return this.pos;
	}

	public float getPitch() {
		return this.rotation.x;
	}

	public float getYaw() {
		return this.rotation.y;
	}

	public float getRoll() {
		return this.rotation.z;
	}

	public Vector3f getRotation() {
		return this.rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public void rotateX(float drx) {
		this.rotation.x += drx;
	}

	public void rotateY(float dry) {
		this.rotation.y += dry;
	}

	public void rotateZ(float drz) {
		this.rotation.z += drz;
	}
}
