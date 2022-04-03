package com.pocolifo.pocolifoclient.mods;

public interface RenderableMod extends Mod {
	ModPosition getPosition();

	default float getScaledWidth() {
		return getWidth() * getPosition().getScale();
	}

	default float getScaledHeight() {
		return getHeight() * getPosition().getScale();
	}

	float getWidth();

	float getHeight();

	void render();
}
