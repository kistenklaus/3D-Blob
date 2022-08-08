package logic;

import org.joml.Vector3f;

import engine.gfx.Light;

public class GameLight extends Light{

	private Vector3f untransformedPos;
	
	public GameLight(Vector3f pos, Vector3f color) {
		super(pos, color);
		this.untransformedPos = new Vector3f(super.getPos());
	}
	
	public void updatePosition(float camera_rotation) {
		super.pos = new Vector3f(
				untransformedPos.x * (float)Math.cos(Math.toRadians(camera_rotation)),
				untransformedPos.y * (float)Math.sin(Math.toRadians(camera_rotation)),
				untransformedPos.z);
	}
}
