package logic;

import org.joml.Vector3f;

import engine.Clock;
import engine.gfx.Camera;

public class GameCamera extends Camera{

	private float pref_zRotation;
	
	public GameCamera() {
		super();
		this.pref_zRotation = super.getRotation().z;
		// TODO Auto-generated constructor stub
	}

	public GameCamera(Vector3f pos, Vector3f rotation) {
		super(pos, rotation);
	}
	
	public void update() {
		
		//decides angleValue; example: 0 or 360 | 270 or -90
		float currRoll = this.pref_zRotation-super.getRoll()>180?super.getRoll()+360:super.getRoll();
		float prefRoll = this.pref_zRotation-super.getRoll()<-180?this.pref_zRotation+360:this.pref_zRotation;
		//simple linearInterpolated
		float drz = (float)((prefRoll-currRoll)*5*Clock.getDT());
		rotateZ(drz);
		
		if(super.getRoll()>=360) {
			super.rotateZ(-360);
		}else if(super.getRoll()<0) {
			super.rotateZ(360);
		}
	}
	
	public void setPrefZRotation(float pref) {
		this.pref_zRotation = pref;
	}
	
}
