package com.pocolifo.pocolifoclient.render.font;

import com.pocolifo.pocolifoclient.render.ClientColor;

public enum ChatCodes {
	DARK_RED('4', "AA0000"),
	RED('c', "FF5555"),
	GOLD('6', "FFAA00"),
	YELLOW('e', "FFFF55"),
	DARK_GREEN('2', "00AA00"),
	GREEN('a', "55FF55"),
	AQUA('b', "55FFFF"),
	DARK_AQUA('3', "00AAAA"),
	DARK_BLUE('1', "0000AA"),
	BLUE('9', "5555FF"),
	LIGHT_PURPLE('d', "FF55FF"),
	DARK_PURPLE('5', "AA00AA"),
	WHITE('f', "FFFFFF"),
	GRAY('7', "AAAAAA"),
	DARK_GRAY('8', "555555"),
	BLACK('0', "000000"),

	OBFUSCATED('k'),
	BOLD('l'),
	STRIKETHROUGH('m'),
	UNDERLINE('n'),
	ITALICS('o'),
	RESET('r');

	public static final String SPECIAL_CHAR = "\u00A7";

	private final char code;

	private String hexColor;
	private ClientColor color;

	ChatCodes(char code, String hexColor) {
		this.code = code;

		this.hexColor = hexColor;
		this.color = new ClientColor(hexColor);
	}

	ChatCodes(char code) {
		this.code = code;
	}

	public static ChatCodes getByCode(char code) {
		for (ChatCodes cc : ChatCodes.values()) {
			if (cc.code == code) return cc;
		}

		return null;
	}

	public char getCode() {
		return this.code;
	}

	public String getHexColor() {
		return this.hexColor;
	}

	public ClientColor getColor() {
		return this.color;
	}

	public boolean isColor() {
		return this.color != null;
	}

	// TODO: Implement translateAlternateColorCodes
	// TODO: Implement stripColorCodes
}
