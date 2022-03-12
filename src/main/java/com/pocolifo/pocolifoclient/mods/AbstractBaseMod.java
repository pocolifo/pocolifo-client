package com.pocolifo.pocolifoclient.mods;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.pocolifo.pocolifoclient.mods.config.ConfigurationType;
import com.pocolifo.pocolifoclient.mods.config.ModConfiguration;
import lombok.Getter;

public abstract class AbstractBaseMod implements Mod {
	@Getter private final String modName;
	@Getter private final String description;
	@Getter private boolean isEnabled;

	public AbstractBaseMod(String name, String description) {
		this.modName = name;
		this.description = description;
	}

	protected List<Field> getAllModConfigurations(Class<?> c) {
		List<Field> fieldList = new ArrayList<>();

		for (Field field : c.getDeclaredFields()) {
			if (field.isAnnotationPresent(ModConfiguration.class)) fieldList.add(field);
		}

		if (c.getSuperclass() != null) {
			fieldList.addAll(getAllModConfigurations(c.getSuperclass()));
		}

		return fieldList;
	}

	@Override
	public JsonObject serialize() throws ReflectiveOperationException {
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("enabled", new JsonPrimitive(this.isEnabled));

		JsonObject config = new JsonObject();

		for (Field possibleConfigField : getAllModConfigurations(this.getClass())) {
			possibleConfigField.setAccessible(true);

			ModConfiguration annotation = possibleConfigField.getAnnotation(ModConfiguration.class);

			Constructor<? extends ConfigurationType<?>> constructor = annotation.type().getConstructor();
			constructor.setAccessible(true);
			ConfigurationType<?> configurationType = constructor.newInstance();

			Object o = possibleConfigField.get(this);
			configurationType.setValue(o);

			config.add(annotation.value(), configurationType.serialize());
		}

		jsonObject.add("config", config);

		return jsonObject;
	}

	@Override
	public void deserialize(JsonObject object) throws Exception {
		if (object.has("enabled")) this.isEnabled = object.get("enabled").getAsBoolean();

		if (object.has("config")) {
			JsonObject config = object.get("config").getAsJsonObject();

			for (Field possibleConfigField : getAllModConfigurations(this.getClass())) {
				possibleConfigField.setAccessible(true);

				ModConfiguration annotation = possibleConfigField.getAnnotation(ModConfiguration.class);

				if (config.has(annotation.value())) {
					Constructor<? extends ConfigurationType<?>> constructor = annotation.type().getConstructor();
					constructor.setAccessible(true);
					ConfigurationType<?> configurationType = constructor.newInstance();
					configurationType.deserialize(config.get(annotation.value()));

					Object value = configurationType.getValue();

					possibleConfigField.set(this, value);
				}
			}
		}
	}

	@Override
	public void enable() {
		this.isEnabled = true;
	}

	@Override
	public void disable() {
		this.isEnabled = false;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Mod) {
			return ((Mod) o).getModName().equals(this.getModName());
		}

		return false;
	}
}
