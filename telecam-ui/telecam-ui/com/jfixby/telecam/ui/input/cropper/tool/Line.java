
package com.jfixby.telecam.ui.input.cropper.tool;

import com.jfixby.cmns.api.floatn.Float2;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.cmns.api.math.FloatMath;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public class Line extends CropperElement {

	private final Float2 A;
	private final Float2 B;
	private final Float2 tmp;

	private final double offsetAngle;
	private double length;

	public Line (final CropperTool cropperTool, final float relativeX1, final float relativeY1, final float relativeX2,
		final float relativeY2) {
		super(cropperTool);

		this.A = Geometry.newFloat2(relativeX1, relativeY1);
		this.B = Geometry.newFloat2(relativeX2, relativeY2);
		this.tmp = Geometry.newFloat2();
		this.getVectorTool().X = this.B.getX() - this.A.getX();
		this.getVectorTool().Y = this.B.getY() - this.A.getY();
		this.getVectorTool().XYtoAR();
		this.getVectorTool().A = this.getVectorTool().A;
		this.offsetAngle = this.getVectorTool().A - FloatMath.PI() / 2;

	}

	@Override
	public void setup (final Raster raster) {
		super.setup(raster);
		this.getRaster().setName("line: " + this.A + " -> " + this.B);
	}

	public void update () {
		this.getRaster().setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.TOP);
		final Rectangle area = this.getMaster().getArea();

		this.tmp.setLinearSum(this.A, -1, this.B, 1);
		area.toAbsolute(this.tmp);
		this.tmp.subtract(area.getTopLeftCorner());
		this.length = this.tmp.norm();
		this.getRaster().setHeight(this.length);
		this.tmp.set(this.A);
		area.toAbsolute(this.tmp);

		this.getRaster().setPosition(this.tmp);
		this.getRaster().setRotation(this.offsetAngle + area.getRotation().toRadians());

	}

	public CanvasPosition getPosition () {
		return this.getRaster().getPosition();
	}

	public double getLength () {
		return this.length;
	}

}
