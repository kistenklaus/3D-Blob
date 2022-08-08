package engine.gfx;

public class Scene {
	private Camera camera;
	private Light light;

	public Scene(Camera camera, Light light) {
		super();
		this.camera = camera;
		this.light = light;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Light getLight() {
		return light;
	}

	public void setLight(Light light) {
		this.light = light;
	}
}
