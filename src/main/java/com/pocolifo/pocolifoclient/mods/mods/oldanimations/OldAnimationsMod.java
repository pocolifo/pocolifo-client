package com.pocolifo.pocolifoclient.mods.mods.oldanimations;

import com.pocolifo.pocolifoclient.mods.AbstractBaseMod;
import com.pocolifo.pocolifoclient.mods.config.ModConfiguration;
import com.pocolifo.pocolifoclient.mods.config.types.BooleanConfigurationType;

public class OldAnimationsMod extends AbstractBaseMod {
	@ModConfiguration(value = "none", name = "1.7 None Actions", type = BooleanConfigurationType.class)
	public boolean oldNone = true;

	@ModConfiguration(value = "eating", name = "1.7 Eating", type = BooleanConfigurationType.class)
	public boolean oldEating = true;

	@ModConfiguration(value = "drinking", name = "1.7 Drinking", type = BooleanConfigurationType.class)
	public boolean oldDrinking = true;

	@ModConfiguration(value = "blocking", name = "1.7 Blocking", type = BooleanConfigurationType.class)
	public boolean oldBlocking = true;

	@ModConfiguration(value = "bow", name = "1.7 Bow", type = BooleanConfigurationType.class)
	public boolean oldBow = true;


	public OldAnimationsMod() {
		super("Old Animations", "Revert various animations back to 1.7");
	}
}
