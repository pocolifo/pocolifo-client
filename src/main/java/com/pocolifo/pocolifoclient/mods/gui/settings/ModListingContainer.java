package com.pocolifo.pocolifoclient.mods.gui.settings;

import com.pocolifo.pocolifoclient.render.ClientColor;
import com.pocolifo.pocolifoclient.ui.api.Component;
import com.pocolifo.pocolifoclient.ui.impl.ScrollableContainerComponent;
import static com.pocolifo.pocolifoclient.mods.gui.settings.GuiModSettings.DISABLED;
import static com.pocolifo.pocolifoclient.mods.gui.settings.GuiModSettings.ENABLED;

public class ModListingContainer extends ScrollableContainerComponent {
	public ModListingContainer(float x, float y, float width, float height, ClientColor color) {
		super(x, y, width, height, color);
	}

	@Override
	public void renderContent() {
		super.renderContent();

		for (Component component : this.getComponents()) {
			if (component instanceof ModListingComponent) {
				ModListingComponent modComponent = (ModListingComponent) component;

				if (modComponent.isPressed()) {
					if (modComponent.getMod().isEnabled()) {
						modComponent.getMod().disable();
					} else {
						modComponent.getMod().enable();
					}

					modComponent.setColor(modComponent.getMod().isEnabled() ? ENABLED : DISABLED);
				}
			}
		}
	}
}
