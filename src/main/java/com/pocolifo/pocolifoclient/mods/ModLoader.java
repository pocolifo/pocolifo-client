package com.pocolifo.pocolifoclient.mods;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pocolifo.commons.HashingCommons;
import com.pocolifo.commons.ResourceCommons;
import com.pocolifo.commons.StreamCommons;
import com.pocolifo.pocolifoclient.PocolifoClient;
import com.pocolifo.pocolifoclient.launch.BuildProperties;
import com.pocolifo.pocolifoclient.launch.LaunchEnvironment;
import lombok.Getter;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModLoader {
	@Getter private final List<Mod> mods;
	@Getter private final List<RenderableMod> renderableMods;
	@Getter private final File modFolder;
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static final KeyBinding openModMenu = new KeyBinding("Open Mod Menu", Keyboard.KEY_RSHIFT, BuildProperties.NAME);


	public ModLoader() {
		this.mods = new ArrayList<>();
		this.renderableMods = new ArrayList<>();
		this.modFolder = new File(LaunchEnvironment.gameDirectory, "mods");
		this.modFolder.mkdirs();
	}

	public void registerMod(Mod mod) {
		if (!this.mods.contains(mod)) this.mods.add(mod); else return;

		if (mod instanceof RenderableMod) {
			RenderableMod renderableMod = (RenderableMod) mod;

			if (this.renderableMods.contains(renderableMod)) {
				return;
			} else {
				this.renderableMods.add(renderableMod);
			}
		}

		File modSaveFile = this.getModSaveFile(mod);

		try (InputStream stream = Objects.requireNonNull(this.getClass().getResourceAsStream("/default/mods/" + this.getModSaveFileName(mod)));
		 	Reader reader = modSaveFile.isFile() ? new FileReader(modSaveFile) : new InputStreamReader(stream)) {
			JsonObject modSave = new JsonParser().parse(reader).getAsJsonObject();
			mod.deserialize(modSave);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (mod.isEnabled()) mod.enable();
	}

	private String getModSaveFileName(Mod mod) {
		String val = mod.getModName() + "|" + mod.getDescription();
		String hash = HashingCommons.asHex(HashingCommons.sha384(val.getBytes(StandardCharsets.ISO_8859_1))).substring(0, 8);

		return hash + ".json";
	}

	public File getModSaveFile(Mod mod) {
		return new File(this.modFolder, this.getModSaveFileName(mod));
	}

	public void unloadAll() {
		for (Mod mod : this.mods) {
			try {
				Files.write(this.getModSaveFile(mod).toPath(), this.gson.toJson(mod.serialize()).getBytes(StandardCharsets.UTF_8));
			} catch (Exception e) {
				e.printStackTrace();
			}

			mod.disable();
		}
	}
}
