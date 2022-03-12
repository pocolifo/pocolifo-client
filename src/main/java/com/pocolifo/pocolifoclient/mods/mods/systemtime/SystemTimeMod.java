package com.pocolifo.pocolifoclient.mods.mods.systemtime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.pocolifo.pocolifoclient.mods.templatemods.AbstractDraggableTextMod;

public class SystemTimeMod extends AbstractDraggableTextMod {
	private boolean twentyFourHourTime;

	public SystemTimeMod() {
		super("System Time", "Displays the current real life time (not like you would know)");
	}

	@Override
	protected String getText() {
		LocalDateTime now = LocalDateTime.now();
		String format;

		if (this.twentyFourHourTime) {
			format = "dd MMM yyyy HH:mm:ss";
		} else {
			format = "dd MMM yyyy hh:mm:ss a";
		}

		return now.format(DateTimeFormatter.ofPattern(format));
	}
}
