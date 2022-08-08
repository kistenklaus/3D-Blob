package engine.model;

import org.joml.Vector3f;

public class Entity {
	private RawModel rawModel;

	protected Vector3f pos;
	protected Vector3f rotation;
	private float scale;

	public Entity(RawModel rawModel, Vector3f pos, Vector3f rotation, float scale) {
		this.rawModel = rawModel;
		this.pos = pos;
		this.rotation = rotation;
		this.scale = scale;
	}

	public RawModel getRawModel() {
		return rawModel;
	}

	public Vector3f getPos() {
		return pos;
	}

	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	public Vector3f move(Vector3f delta) {
		this.pos.add(delta);
		return this.pos;
	}

	public Vector3f getRotation() {
		return rotation;
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

	public void setScale(float scale) {
		this.scale = scale;
	}

	public float getScale() {
		return scale;
	}

}
