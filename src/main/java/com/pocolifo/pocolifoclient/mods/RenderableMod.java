package com.pocolifo.pocolifoclient.mods;

public interface RenderableMod extends Mod {
	ModPosition getPosition();

	float getWidth();

	float getHeight();

	void render();
}
