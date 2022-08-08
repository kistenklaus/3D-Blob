package engine.gfx.fbos;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_DEPTH24_STENCIL8;
import static org.lwjgl.opengl.GL30.GL_DEPTH_STENCIL_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBindRenderbuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glDeleteRenderbuffers;
import static org.lwjgl.opengl.GL30.glFramebufferRenderbuffer;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;
import static org.lwjgl.opengl.GL30.glRenderbufferStorage;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.GL_TEXTURE_2D_MULTISAMPLE;
import static org.lwjgl.opengl.GL32.*;

import java.nio.ByteBuffer;	

public abstract class FrameBufferObject {
	protected int fboID;
	protected int rboID;
	protected int width,height;
	protected int textureAttachmentID;
	public FrameBufferObject(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	protected void initFBO() {
		this.fboID = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, fboID);
		
		initAttachments();
		
		if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			System.err.println("Failed init a Framebuffer!!!");
		}
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	
	public void bind() {
		glBindFramebuffer(GL_FRAMEBUFFER, fboID);
		glViewport(0, 0, width, height);
	}
	
	public void unbind(int displayWidth, int displayHeight) {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glViewport(0, 0, displayWidth, displayHeight);
	}
	
	protected abstract void initAttachments();
	
	protected void createColorAttachment() {
		this.textureAttachmentID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, this.textureAttachmentID);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, (ByteBuffer)null);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		glBindTexture(GL_TEXTURE_2D, 0);
		
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, this.textureAttachmentID, 0);
		
	}
	protected void createMultisampleColorAttachment(int samples) {
		this.textureAttachmentID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D_MULTISAMPLE, this.textureAttachmentID);
		glTexImage2DMultisample(GL_TEXTURE_2D_MULTISAMPLE, samples, GL_RGB, width, height, true);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		glBindTexture(GL_TEXTURE_2D, 0);
		
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D_MULTISAMPLE,
				this.textureAttachmentID, 0);
		
	}
	
	protected void createDepthAttachment() {
		textureAttachmentID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureAttachmentID);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, width, height, 0, GL_DEPTH_COMPONENT, GL_FLOAT, (ByteBuffer)null);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, textureAttachmentID, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	protected void createDepthRBO() {
		rboID = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, rboID);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, width, height);
		glBindRenderbuffer(GL_RENDERBUFFER, 0);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, rboID);
		
		
	}
	
	protected void createMultisampleDepthRBO(int sampler) {
		rboID = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, rboID);
		glRenderbufferStorageMultisample(GL_RENDERBUFFER, sampler, GL_DEPTH24_STENCIL8, width, height);
		glBindRenderbuffer(GL_RENDERBUFFER, 0);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, rboID);
		
	}
	
	public void cleanUp() {
		glDeleteTextures(textureAttachmentID);
		glDeleteRenderbuffers(rboID);
		glDeleteFramebuffers(fboID);
	}
}
