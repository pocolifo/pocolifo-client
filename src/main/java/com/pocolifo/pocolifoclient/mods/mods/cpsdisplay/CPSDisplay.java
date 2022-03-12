package com.pocolifo.pocolifoclient.mods.mods.cpsdisplay;

import java.util.ArrayList;
import java.util.List;

import com.pocolifo.pocolifoclient.mods.templatemods.AbstractDraggableTextMod;
import org.lwjgl.input.Mouse;

public class CPSDisplay extends AbstractDraggableTextMod {
	private final List<Long> clicksLeft = new ArrayList<>();
	private final List<Long> clicksRight = new ArrayList<>();

	private boolean leftDown;
	private boolean rightDown;

	public CPSDisplay() {
		super("CPS Display", "Displays how many times you've clicked in the last second");
	}

	@Override
	protected String getText() {
		this.updateCPS();

		return clicksLeft.size() + " | " + clicksRight.size();
	}

	private void updateCPS() {

		boolean m0 = Mouse.isButtonDown(0);
		boolean m1 = Mouse.isButtonDown(1);

		if (m0 != this.leftDown) {
			this.leftDown = Mouse.isButtonDown(0);
			if (m0) this.clicksLeft.add(System.currentTimeMillis());
		}

		if (m1 != this.rightDown) {
			this.rightDown = Mouse.isButtonDown(1);
			if (m1) this.clicksRight.add(System.currentTimeMillis());
		}

		this.clicksLeft.removeIf(l -> System.currentTimeMillis() > l + 1000L);
		this.clicksRight.removeIf(l -> System.currentTimeMillis() > l + 1000L);

	}
}
