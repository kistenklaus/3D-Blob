package engine.model;

import engine.textures.Texture;

public class TexturedModel {
	private RawModel rawModel;
	private Texture tex;
	
	public TexturedModel(RawModel rawModel, Texture tex) {
		this.rawModel = rawModel;
		this.tex = tex;
	}
	public RawModel getRawModel() {
		return rawModel;
	}

	public Texture getTex() {
		return tex;
	}
}
