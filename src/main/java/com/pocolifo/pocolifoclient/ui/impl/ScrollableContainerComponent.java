package com.pocolifo.pocolifoclient.ui.impl;

import com.pocolifo.pocolifoclient.render.ClientColor;
import com.pocolifo.pocolifoclient.render.Colors;
import com.pocolifo.pocolifoclient.render.geometry.Geometry;
import com.pocolifo.pocolifoclient.ui.api.Component;
import com.pocolifo.pocolifoclient.ui.api.RectangularComponent;
import com.pocolifo.pocolifoclient.util.Fonts;
import com.pocolifo.pocolifoclient.util.GlUtil;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GLSync;

import net.minecraft.client.renderer.GlStateManager;

public class ScrollableContainerComponent extends ContainerComponent {
	private double scrollY;
	private float scrollVel;

	public ScrollableContainerComponent(float x, float y, float width, float height, ClientColor color) {
		super(x, y, width, height, color);
	}

	@Override
	public void render() {
		final double div = 10d;
		int dWheel = Mouse.getDWheel();

		if (this.isHovering()) {
			scrollVel += dWheel / div;
		}

		scrollVel *= 0.7f;
		double invisibleContent = getInvisibleContent();

		if (invisibleContent > 1) {
			scrollY += scrollVel;

			// top
			if (scrollY > 0) {
				scrollY = 0;
			}

			if (-invisibleContent > scrollY) {
				scrollY = -invisibleContent;
			}
		}

		// TODO: Don't limit FPS in these menus
		Display.sync(60);

		super.render();
	}

	@Override
	public void renderContent() {
		// TODO: Don't use three translate calls lol

		GlUtil.translate(0d, scrollY, 0d);

		super.renderContent();

		if (this.height > getInnerHeight()) {
			return;
		}

		GlUtil.translate(0d, -scrollY, 0d);

		this.renderScrollbar();

		GlUtil.translate(0d, scrollY, 0d);
	}

	private double getInvisibleContent() {
		// 20 is for some margin
		return getInnerHeight() - this.height + 20;
	}

	private void renderScrollbar() {
		int width = 10;
		int margin = 5;
		double div = 2.5d;
		double invisibleContent = getInvisibleContent() / div;

		float height = (float) Math.min(this.height, this.height - invisibleContent);

		Geometry.drawFullRoundedRectangle(this.width - (width + margin), (float) Math.abs(scrollY / div), width, height, 10f, 10f, Colors.WHITE.color);
	}

	public double getInnerHeight() {
		double innerHeight = -1;

		for (Component component : this.components) {
			double i = component.getY();

			if (component instanceof RectangularComponent) {
				i += ((RectangularComponent) component).getHeight();
			}

			innerHeight = Math.max(innerHeight, i);
		}

		return innerHeight;
	}
}
