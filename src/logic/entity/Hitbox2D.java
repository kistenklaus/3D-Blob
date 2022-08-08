package logic.entity;

import org.joml.Vector2f;

public class Hitbox2D {

	private float x, y, width, height;
	private float tx, ty;

	public Hitbox2D(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		pop();
		this.width = width;
		this.height = height;
	}

	public void pop() {
		this.tx = x;
		this.ty = y;
	}

	public void setLocation_Center(Vector2f center) {
		this.tx = center.x - width / 2;
		this.ty = center.y + height / 2;
	}

	public void setLocation_Center(float cx, float cy) {
		this.tx = cx - width / 2;
		this.ty = cy + height / 2;
	}

	public void setLocation(float x, float y) {
		this.tx = x;
		this.ty = y;
	}

	public boolean intersects(Hitbox2D hit) {
//		System.out.println(this + " intersects " + hit);
		return (this.tx < hit.tx + hit.width &&
				this.tx + this.width > hit.tx && 
				this.ty < hit.ty + hit.height &&
				this.ty + this.height > hit.ty);
	}
	
	public String toString() {
		return "["+tx+"|"+ty+"|"+width+"|"+height+"]";
	}
}
