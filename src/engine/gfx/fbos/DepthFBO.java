package engine.gfx.fbos;

public class DepthFBO extends FrameBufferObject{

	public DepthFBO(int width, int height) {
		super(width, height);
	}

	@Override
	protected void initAttachments() {
		createDepthAttachment();
		
	}
	
	public int getDepthMap() {
		return super.textureAttachmentID;
	}

}
