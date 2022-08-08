package engine.gfx.fbos;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class CaptureFBO_AA extends FrameBufferObject{

	private CaptureFBO intermediateFBO;
	private boolean resolved;
	private int samples;
	
	public CaptureFBO_AA(int width, int height, int samples) {
		super(width, height);
		this.samples = samples;
		initFBO();
		this.intermediateFBO = new CaptureFBO(width, height);
		this.resolved = false;
	}

	@Override
	protected void initAttachments() {
		createMultisampleColorAttachment(samples);
		createMultisampleDepthRBO(samples);
	}
	
	public int getCaptureTextureID() {
		if(!resolved)System.err.println("The Multisampled Attachment should be resolved before drawing the FBO \n"
				+ "--> CaptureFBO_AA.blintImage(void) should be called before drawing");
		return this.intermediateFBO.getCaptureTextureID();
	}
	
	public void blintImage() {
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, super.fboID);
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, intermediateFBO.fboID);
		GL30.glBlitFramebuffer(0, 0, width, height, 0, 0, width, height, GL11.GL_COLOR_BUFFER_BIT, GL11.GL_NEAREST);
		this.resolved = true;
	}
	
	public void bind() {
		super.bind();
		this.resolved = false;
	}

}
