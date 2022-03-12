package com.pocolifo.pocolifoclient.splash;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;

// TODO: Splash screens are very blurry, fix
// we create framebuffer @ 854x480
// it never gets resized
// so we have a blurry resolution and that's why it looks bad
public abstract class AbstractSplashScreen {
	protected Framebuffer framebuffer;
	protected int displayWidth;
	protected int displayHeight;

	public void prepare() {
		this.updateDimensions();

		this.framebuffer = new Framebuffer(displayWidth, displayHeight, true);
		this.framebuffer.bindFramebuffer(false);

		GlStateManager.matrixMode(GL11.GL_PROJECTION);
		GlStateManager.loadIdentity();
		GlStateManager.ortho(0.0D, displayWidth, displayHeight, 0.0D, 1000.0D, 3000.0D);
		GlStateManager.matrixMode(GL11.GL_MODELVIEW);
		GlStateManager.loadIdentity();
		GlStateManager.translate(0.0F, 0.0F, -2000.0F);
		GlStateManager.disableLighting();
		GlStateManager.disableFog();
		GlStateManager.disableDepth();
		GlStateManager.enableTexture2D();
	}

	protected void updateDimensions() {
		if ((Display.getWidth() != displayWidth || Display.getHeight() != displayHeight) && this.framebuffer != null) {
			this.framebuffer.deleteFramebuffer();
			this.framebuffer.createFramebuffer(Display.getWidth(), Display.getHeight());
		}

		displayWidth = Display.getWidth();
		displayHeight = Display.getHeight();
	}

	public void render() {
		framebuffer.bindFramebuffer(true);
		this.updateDimensions();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		this.renderElements();

		framebuffer.unbindFramebuffer();
		framebuffer.framebufferRender(displayWidth, displayHeight);

		Display.sync(60);
		Minecraft.getMinecraft().updateDisplay();
	}

	public void close() {
		GlStateManager.disableLighting();
		GlStateManager.disableFog();
		framebuffer.unbindFramebuffer();
		framebuffer.framebufferRender(displayWidth, displayHeight);
		GlStateManager.enableAlpha();
		GlStateManager.alphaFunc(516, 0.1F);
		Minecraft.getMinecraft().updateDisplay();
	}

	protected abstract void renderElements();
	public abstract boolean keepRendering();


	/*
	ScaledResolution var2 = new ScaledResolution(this);
        int var3 = var2.getScaleFactor();
        Framebuffer framebuffer = new Framebuffer(var2.getScaledWidth() * var3, var2.getScaledHeight() * var3, true);
        framebuffer.bindFramebuffer(false);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, (double)var2.getScaledWidth(), (double)var2.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();
        InputStream var5 = null;

        try {
            var5 = this.mcDefaultResourcePack.getInputStream(locationMojangPng);
            this.mojangLogo = var1.getDynamicTextureLocation("logo", new DynamicTexture(ImageIO.read(var5)));
            var1.bindTexture(this.mojangLogo);
        } catch (IOException var12) {
            logger.error("Unable to load logo: " + locationMojangPng, var12);
        } finally {
            IOUtils.closeQuietly(var5);
        }

        Tessellator var6 = Tessellator.getInstance();
        WorldRenderer var7 = var6.getWorldRenderer();
        var7.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        var7.pos(0.0D, (double)this.displayHeight, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
        var7.pos((double)this.displayWidth, (double)this.displayHeight, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
        var7.pos((double)this.displayWidth, 0.0D, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
        var7.pos(0.0D, 0.0D, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
        var6.draw();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int var8 = 256;
        int var9 = 256;
        this.draw((var2.getScaledWidth() - var8) / 2, (var2.getScaledHeight() - var9) / 2, 0, 0, var8, var9, 255, 255, 255, 255);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        framebuffer.unbindFramebuffer();
        framebuffer.framebufferRender(var2.getScaledWidth() * var3, var2.getScaledHeight() * var3);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);
        this.updateDisplay();
	 */
}
