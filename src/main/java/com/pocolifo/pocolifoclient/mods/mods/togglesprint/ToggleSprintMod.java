package com.pocolifo.pocolifoclient.mods.mods.togglesprint;

import java.util.Collection;
import java.util.Collections;

import com.pocolifo.pocolifoclient.mods.config.ModConfiguration;
import com.pocolifo.pocolifoclient.mods.config.types.StringConfigurationType;
import com.pocolifo.pocolifoclient.mods.templatemods.AbstractDraggableTextMod;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class ToggleSprintMod extends AbstractDraggableTextMod {
	private boolean toggled;

	@ModConfiguration(value = "prefix", name = "Prefix Text", type = StringConfigurationType.class)
	private String prefixText = "Toggle Sprint ";

	@ModConfiguration(value = "hungry", name = "Too Hungry Text", type = StringConfigurationType.class)
	public String tooHungryText = "[Hungry]";

	@ModConfiguration(value = "disabled", name = "Off Text", type = StringConfigurationType.class)
	public String offText = "[Off]";

	@ModConfiguration(value = "enabled", name = "On Text", type = StringConfigurationType.class)
	public String onText = "[On]";

	@ModConfiguration(value = "key_held", name = "Key Held Text", type = StringConfigurationType.class)
	public String keyHeldText = "[Key Held]";

	public ToggleSprintMod() {
		super("Toggle Sprint", "You're too lazy to hold down your sprint key");
	}

	private int getFoodLevel() {
		if (Minecraft.getMinecraft().thePlayer != null) {
			return Minecraft.getMinecraft().thePlayer.getFoodStats().getFoodLevel();
		}

		return 0;
	}

	private boolean tooHungryToSprint() {
		return getFoodLevel() < 7;
	}

	public KeyBinding getSprintKey() {
		return Minecraft.getMinecraft().gameSettings.keyBindSprint;
	}

	@Override
	protected String getText() {
		if (this.getSprintKey().isPressed()) {
			this.toggled = !this.toggled;
		}

		KeyBinding.setKeyBindState(this.getSprintKey().getKeyCode(), this.toggled);

		String s = this.prefixText;

		if (tooHungryToSprint() && Minecraft.getMinecraft().playerController.getCurrentGameType().isSurvivalOrAdventure()) {
			s += this.tooHungryText;
		} else if (Keyboard.isKeyDown(getSprintKey().getKeyCode())) {
			s += this.keyHeldText;
		} else if (this.toggled) {
			s += this.onText;
		} else if (!this.toggled) {
			s += this.offText;
		}

		return s;
	}

	@Override
	public Collection<KeyBinding> getKeybindings() {
		return Collections.emptyList();
	}
}
