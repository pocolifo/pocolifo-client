package com.pocolifo.pocolifoclient.mods.mods.autotip;

import java.util.Timer;

import com.pocolifo.pocolifoclient.mods.AbstractBaseMod;
import com.pocolifo.pocolifoclient.mods.config.ModConfiguration;
import com.pocolifo.pocolifoclient.mods.config.types.BooleanConfigurationType;
import com.pocolifo.pocolifoclient.mods.config.types.NumberConfigurationType;
import com.pocolifo.pocolifoclient.mods.config.types.StringConfigurationType;

public class AutoTipMod extends AbstractBaseMod {
	@ModConfiguration(value = "tip_anonymously", name = "Send Tips Anonymously", type = BooleanConfigurationType.class)
	public boolean tipAnonymously = false;

	@ModConfiguration(value = "tip_one_player", name = "Tip Only One Player", type = BooleanConfigurationType.class)
	public boolean tipOnePlayer = false;

	@ModConfiguration(value = "tip_player_name", name = "Player Name", type = StringConfigurationType.class, availableIfOptionIsEnabled = "tip_one_player")
	public String tipPlayerName = "";

	@ModConfiguration(value = "tip_interval", name = "Tip Interval (s)", type = NumberConfigurationType.class)
	public float interval = 600; // 10 minutes

	public AutoTipMod() {
		super("Auto Tip", "Tips players on Hypixel once in a while");
	}

	private Timer timer;

	@Override
	public void enable() {
		super.enable();

		this.timer = new Timer();
		this.timer.scheduleAtFixedRate(new AutoTipTask(this), 0, (long) (this.interval * 1000L));
	}

	@Override
	public void disable() {
		super.disable();

		if (this.timer != null) this.timer.cancel();
	}
}
