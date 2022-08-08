package engine.gfx.fbos;

public class CaptureFBO extends FrameBufferObject{

	public CaptureFBO(int width, int height) {
		super(width, height);
		initFBO();
	}

	@Override
	protected void initAttachments() {
		createColorAttachment();
		createDepthRBO();
	}
	
	public int getCaptureTextureID() {
		return super.textureAttachmentID;
	}

}
