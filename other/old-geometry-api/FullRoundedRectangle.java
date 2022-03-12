//package com.pocolifo.pocolifoclient.render.geometry;
//
//import com.pocolifo.pocolifoclient.render.ClientColor;
//import com.pocolifo.pocolifoclient.render.DetailedShape;
//
//public class FullRoundedRectangle extends DetailedShape {
//	private FullOval topLeft;
//	private FullOval topRight;
//	private FullOval bottomLeft;
//	private FullOval bottomRight;
//
//	private FullRectangle leftRect;
//	private FullRectangle middleRect;
//	private FullRectangle rightRect;
//
//
//	public ClientColor color;
//	public float width;
//	public float height;
//	public float roundness;
//
//	public FullRoundedRectangle(float x, float y, float width, float height, float roundness, int detail, ClientColor color) {
//		super(x, y, detail);
//
//		this.color = color;
//		this.width = width;
//		this.height = height;
//		this.roundness = roundness;
//
//		this.updateGeometry();
//	}
//
//	public void updateGeometry() {
//		float radius = this.roundness / 2f;
//
//		// TODO: fix: ovals look weird when roundness > height or roundness > width
//		this.topLeft = new FullOval(this.x + radius, this.y + radius, this.detail, this.roundness, this.roundness, this.color);
//		this.topLeft.degrees = 90f;
//		this.topLeft.rotationRadians = Math.toRadians(180f);
//
//		this.topRight = new FullOval(this.x + this.width - radius, this.y + radius, this.detail, this.roundness, this.roundness, this.color);
//		this.topRight.degrees = 90f;
//		this.topRight.rotationRadians = Math.toRadians(90f);
//
//		this.bottomLeft = new FullOval(this.x + radius, this.y + this.height - radius, this.detail, this.roundness, this.roundness, this.color);
//		this.bottomLeft.degrees = 90f;
//		this.bottomLeft.rotationRadians = Math.toRadians(-90f);
//
//		this.bottomRight = new FullOval(this.x + width - radius, this.y + this.height - radius, this.detail, this.roundness, this.roundness, this.color);
//		this.bottomRight.degrees = 90f;
//		this.bottomRight.rotationRadians = 0f;
//
//		this.leftRect = new FullRectangle(this.x, this.y + radius, radius, this.height - this.roundness, this.color);
//		this.rightRect = new FullRectangle(this.x + this.width - radius, this.y + radius, radius, this.height - this.roundness, this.color);
//		this.middleRect = new FullRectangle(this.x + radius, this.y, this.width - this.roundness, this.height, this.color);
//	}
//
//	@Override
//	public void render() {
//		// TODO: fix: updateGeometry shouldn't be calling every render
//		this.updateGeometry();
//
//		this.topLeft.render();
//		this.topRight.render();
//		this.bottomLeft.render();
//		this.bottomRight.render();
//
//		this.leftRect.render();
//		this.middleRect.render();
//		this.rightRect.render();
//	}
//}
