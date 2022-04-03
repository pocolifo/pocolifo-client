package com.pocolifo.pocolifoclient.render;

public enum Colors {
	TRANSPARENT(new ClientColor(0f, 0f, 0f, ClientColor.TRANSPARENT)),
	WHITE(new ClientColor(1f, 1f, 1f)),
	RED(new ClientColor(1f, 0, 0)),
	YELLOW(new ClientColor(1f, 1f, 0f)),
	BLACK(new ClientColor(0, 0, 0)),

	POCOLIFO_PURPLE(new ClientColor("#bb00ffff")),
	POCOLIFO_BLUE(new ClientColor("#0099ffff")),

	BLACK_TRANSPARENT(new ClientColor(0, 0, 0, 0.4f));

	public final ClientColor color;

	Colors(ClientColor color) {
		this.color = color;
	}
}
