package com.pocolifo.pocolifoclient.ui.api;

import com.pocolifo.pocolifoclient.render.ClientColor;

public interface ColoredComponent {
	ClientColor getColor();
	void setColor(ClientColor color);
}
