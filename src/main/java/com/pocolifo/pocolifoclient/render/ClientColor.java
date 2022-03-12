package com.pocolifo.pocolifoclient.render;

import java.awt.Color;

import net.minecraft.client.renderer.GlStateManager;

public class ClientColor {
	public static final float TRANSPARENT = 0.0F;
	public static final float OPAQUE = 1.0F;

	public static final int MAX_VALUE_INT = 255;
	public static final int ALPHA_SHIFT = 24;
	public static final int RED_SHIFT = 16;
	public static final int GREEN_SHIFT = 8;
	public static final int BLUE_SHIFT = 0;
	public static final int HEXADECIMAL_CHARS = 16;

	public float red;
	public float green;
	public float blue;
	public float alpha;

	public ClientColor(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	public ClientColor(float red, float green, float blue) {
		this(red, green, blue, OPAQUE);
	}

	public ClientColor(int red, int green, int blue, int alpha) {
		this(red / 255F, green / 255F, blue / 255F, alpha / 255F);
	}

	public ClientColor(int red, int green, int blue) {
		this(red, green, blue, OPAQUE);
	}

	public ClientColor(Color color) {
		this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	public ClientColor(int color) {
		this((color >> RED_SHIFT & MAX_VALUE_INT) / (float) MAX_VALUE_INT,
				(color >> GREEN_SHIFT & MAX_VALUE_INT) / (float) MAX_VALUE_INT,
				(color >> BLUE_SHIFT & MAX_VALUE_INT) / (float) MAX_VALUE_INT,
				(color >> ALPHA_SHIFT & MAX_VALUE_INT) / (float) MAX_VALUE_INT);
	}

	public ClientColor(String hex) {
		this(getRedHex(hex), getGreenHex(hex), getBlueHex(hex), getAlphaHex(hex));
	}

	private static String normalizeHexString(String hex) {
		hex = hex.replaceAll("#", "");

		if (6 > hex.length() || hex.length() > 8) {
			throw new IllegalArgumentException(String.format("invalid hex string (%s)", hex));
		}

		return hex;
	}

	private static int getAlphaHex(String hex) {
		hex = normalizeHexString(hex);

		if (hex.length() == 8) {
			return Integer.parseInt(hex.substring(6, 8), HEXADECIMAL_CHARS);
		}

		// opaque
		return MAX_VALUE_INT;
	}

	private static int getBlueHex(String hex) {
		hex = normalizeHexString(hex);
		return Integer.parseInt(hex.substring(4, 6), HEXADECIMAL_CHARS);
	}

	private static int getGreenHex(String hex) {
		hex = normalizeHexString(hex);
		return Integer.parseInt(hex.substring(2, 4), HEXADECIMAL_CHARS);
	}

	private static int getRedHex(String hex) {
		hex = normalizeHexString(hex);
		return Integer.parseInt(hex.substring(0, 2), HEXADECIMAL_CHARS);
	}

	public void setCurrentColor() {
		GlStateManager.color(this.red, this.green, this.blue, this.alpha);
	}

	public ClientColor brighterBy(float amount) {
		return new ClientColor(this.red + amount, this.green + amount, this.blue + amount, this.alpha);
	}

	public ClientColor darkerBy(float amount) {
		return new ClientColor(this.red - amount, this.green - amount, this.blue - amount, this.alpha);
	}

	public int getRGB() {
		return new Color(this.red, this.green, this.blue, this.alpha).getRGB();
	}
}
