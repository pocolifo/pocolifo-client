package com.pocolifo.pocolifoclient.mods.gui.settings;

import com.pocolifo.pocolifoclient.mods.Mod;
import com.pocolifo.pocolifoclient.render.ClientColor;
import com.pocolifo.pocolifoclient.ui.impl.ButtonComponent;
import lombok.Getter;

public class ModListingComponent extends ButtonComponent {
	@Getter private final Mod mod;

	public ModListingComponent(String text, float x, float y, float width, float height, ClientColor color, ClientColor textColor, Mod mod) {
		super(text, x, y, width, height, color, textColor);
		this.mod = mod;
	}
}
