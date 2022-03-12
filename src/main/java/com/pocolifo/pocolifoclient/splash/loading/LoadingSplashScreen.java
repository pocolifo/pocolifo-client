package com.pocolifo.pocolifoclient.splash.loading;

import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JOptionPane;

import com.pocolifo.pocolifoclient.render.ClientColor;
import com.pocolifo.pocolifoclient.render.Colors;
import com.pocolifo.pocolifoclient.splash.DefaultSplashScreen;
import com.pocolifo.pocolifoclient.util.Fonts;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;

public class LoadingSplashScreen extends DefaultSplashScreen {
	private final InitializationThread updateThread;
	private final int segmentWidth = 40;
	private final int segmentHeight = 50;
	private final ClientColor segmentColor = ThreadLocalRandom.current().nextInt(0, 10) == 0 ? Colors.POCOLIFO_PURPLE.color : Colors.POCOLIFO_BLUE.color;
	private long startTime;

	public LoadingSplashScreen() {
		super();
		this.updateThread = new InitializationThread();
	}

	@Override
	public void renderElements() {
		super.renderElements();
		renderLoadingBar();
	}

	private void renderLoadingBar() {
		float add = (System.currentTimeMillis() - this.startTime) / 10f;
		int segmentsThatCanFit = displayWidth / segmentWidth + 3;

		for (int i = 0; segmentsThatCanFit > i; i++) {
			ClientColor color = i % 2 == 0 ? this.segmentColor : this.segmentColor.darkerBy(0.2f);

			double x = i * segmentWidth + add;
			x %= segmentWidth * segmentsThatCanFit;
			x -= segmentWidth * 2f;

			renderSegment(x, displayHeight - segmentHeight, segmentWidth, segmentHeight, color);
		}

		Fonts.updatingText.write(this.updateThread.state, 5, displayHeight - (segmentHeight / 2f) - (Fonts.updatingText.getFontHeight() / 2f), Colors.BLACK.color);
	}


	@Override
	public void prepare() {
		super.prepare();

		this.updateThread.start();
		this.startTime = System.currentTimeMillis();
	}

	@Override
	public boolean keepRendering() {
		return this.updateThread.isAlive();
	}

	private void renderSegment(double x, double y, double width, double height, ClientColor color) {
		// 1st half
		GL11.glBegin(GL11.GL_TRIANGLES);
		color.setCurrentColor();

		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x + width, y);
		GL11.glVertex2d(x + width, y + height);

		GL11.glEnd();


		// 2nd half
		GL11.glBegin(GL11.GL_TRIANGLES);
		color.setCurrentColor();

		GL11.glVertex2d(x + width, y + height);
		GL11.glVertex2d(x + width * 2, y + height);
		GL11.glVertex2d(x + width, y);

		GL11.glEnd();
	}
}
