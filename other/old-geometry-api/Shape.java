package com.pocolifo.pocolifoclient.render;

public abstract class Shape implements Renderable {
	public float x;
	public float y;

	public Shape(float x, float y) {
		this.x = x;
		this.y = y;
	}


}
