package com.pocolifo.pocolifoclient.mods.mods.autogg;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Trigger {
	/**
	 * Corresponds to ServerID server identifier
	 */
	@SerializedName("server_id")
	public String serverId;

	@SerializedName("triggers")
	public List<String> triggers;
}
