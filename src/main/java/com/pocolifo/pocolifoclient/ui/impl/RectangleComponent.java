package com.pocolifo.pocolifoclient.ui.impl;

import com.pocolifo.pocolifoclient.render.ClientColor;
import com.pocolifo.pocolifoclient.render.geometry.Geometry;
import com.pocolifo.pocolifoclient.ui.api.ColoredComponent;
import com.pocolifo.pocolifoclient.ui.api.Component;
import com.pocolifo.pocolifoclient.ui.api.MouseActionComponent;
import com.pocolifo.pocolifoclient.ui.api.RectangularComponent;
import com.pocolifo.pocolifoclient.util.GlUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.input.Mouse;
import static com.pocolifo.pocolifoclient.util.ResolutionUtil.getScaledMouseX;
import static com.pocolifo.pocolifoclient.util.ResolutionUtil.getScaledMouseY;

public class RectangleComponent implements Component, ColoredComponent, RectangularComponent, MouseActionComponent {
	@Getter @Setter protected float x;
	@Getter @Setter protected float y;
	@Getter @Setter protected float width;
	@Getter @Setter protected float height;
	@Getter @Setter protected ClientColor color;

	private boolean pressed = true; // prevents action on menu open

	public RectangleComponent(float x, float y, float width, float height, ClientColor color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	@Override
	public void render() {
		Geometry.drawFullRectangle(x, y, width, height, color);
	}

	// TODO: In containers, you can click outside the container but where a button would be and the button will click like normal even though it's outside the container
	@Override
	public boolean isHovering() {
		return getScaledMouseX() - GlUtil.translationX > x && x + width > getScaledMouseX() - GlUtil.translationX &&
				getScaledMouseY() - GlUtil.translationY > y && y + height > getScaledMouseY() - GlUtil.translationY;
	}

	@Override
	public boolean isDown() {
		return Mouse.isButtonDown(0);
	}

	@Override
	public boolean isPressed() {
		if (isDown() && isHovering()) {
			if (pressed) {
				return false;
			}

			pressed = true;
		} else {
			pressed = false;
		}

		return pressed;
	}
}
