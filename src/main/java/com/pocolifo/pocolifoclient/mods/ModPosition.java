package com.pocolifo.pocolifoclient.mods;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.Getter;
import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

/*
 * This helps to responsively set the position of a mod
 * TODO: Orient around the center of the screen, not the top left
 */
public class ModPosition {
	@Getter private float xPercent = 0f;
	@Getter private float yPercent = 0f;

	private int getWidth() {
		return new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth();
	}

	private int getHeight() {
		return new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight();
	}

	public void setX(float x) {
		this.xPercent = x / this.getWidth();
	}

	public void moveX(float x) {
		this.setX(this.getRenderX() + x);
	}

	public void setY(float y) {
		this.yPercent = y / this.getHeight();
	}

	public void moveY(float y) {
		this.setY(this.getRenderY() + y);
	}

	public float getRenderX() {
		return this.xPercent * this.getWidth();
	}

	public float getRenderY() {
		return this.yPercent * this.getHeight();
	}

	public JsonElement serialize() {
		JsonObject jsonObject = new JsonObject();

		jsonObject.add("x", new JsonPrimitive(this.xPercent));
		jsonObject.add("y", new JsonPrimitive(this.yPercent));

		return jsonObject;
	}

	public void deserialize(JsonElement element) {
		JsonObject jsonObject = element.getAsJsonObject();

		this.xPercent = jsonObject.getAsJsonPrimitive("x").getAsFloat();
		this.yPercent = jsonObject.getAsJsonPrimitive("y").getAsFloat();
	}
}
