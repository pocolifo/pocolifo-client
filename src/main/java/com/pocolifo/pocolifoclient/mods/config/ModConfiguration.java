package com.pocolifo.pocolifoclient.mods.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ModConfiguration {
	String value();

	String name();

	Class<? extends ConfigurationType<?>> type();

	String availableIfOptionIsEnabled() default "";
}
