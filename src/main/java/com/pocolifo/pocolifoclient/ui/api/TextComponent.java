package com.pocolifo.pocolifoclient.ui.api;

import com.pocolifo.pocolifoclient.render.ClientColor;

public interface TextComponent {
	String getText();
	void setText(String string);

	ClientColor getTextColor();
	void setTextColor(ClientColor color);

	boolean isTextShadow();
	void setTextShadow(boolean textShadow);
}
