package com.pocolifo.pocolifoclient.mods;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.Getter;
import lombok.Setter;
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
	@Getter @Setter private float scale = 1f;

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
		jsonObject.add("scale", new JsonPrimitive(this.scale));

		return jsonObject;
	}

	public void deserialize(JsonElement element) {
		JsonObject jsonObject = element.getAsJsonObject();

		if (jsonObject.has("x")) this.xPercent = jsonObject.getAsJsonPrimitive("x").getAsFloat();
		if (jsonObject.has("y")) this.yPercent = jsonObject.getAsJsonPrimitive("y").getAsFloat();
		if (jsonObject.has("scale")) this.scale = jsonObject.getAsJsonPrimitive("scale").getAsFloat();
	}

	public void changeScale(float scale) {
		// make sure things don't get too small or big
		if (this.scale + scale > 0.25f && 10f > this.scale + scale) this.scale += scale;
	}
}
