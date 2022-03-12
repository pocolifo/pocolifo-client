package com.pocolifo.pocolifoclient.util.serverid;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ServerMapping {
	@SerializedName("id")
	public String id;

	@SerializedName("name")
	public String name;

	@SerializedName("addresses")
	public List<String> addresses;
}
