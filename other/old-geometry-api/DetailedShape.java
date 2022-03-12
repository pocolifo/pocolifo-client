package com.pocolifo.pocolifoclient.render;

public abstract class DetailedShape extends Shape {
	protected int detail;

	public DetailedShape(float x, float y, int detail) {
		super(x, y);
		this.detail = detail;
	}
}
