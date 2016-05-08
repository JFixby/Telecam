
package com.jfixby.telecam.ui.input.cropper.tool;

import com.jfixby.cmns.api.floatn.FixedFloat2;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.math.CustomAngle;
import com.jfixby.cmns.api.math.FloatMath;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public class Corner extends CropperElement {

	private final FixedFloat2 rectangleCorner;
	private final CustomAngle directionOffset;
	private Raster wing2;
	private Raster wing1;
	private double width;
	private double height;

	@Override
	public String toString () {
		return "Corner" + this.rectangleCorner + "";
	}

	public Corner (final CropperTool cropperTool, final FixedFloat2 rectangleCorner, final CustomAngle directionOffset) {
		super(cropperTool);
		this.rectangleCorner = rectangleCorner;
		this.directionOffset = directionOffset;
	}

	@Override
	public void setup (final Raster raster) {
		super.setup(raster);

		this.width = raster.getWidth();
		this.height = raster.getHeight();
		this.wing1 = this.getRaster();
		this.getRaster().setName("wing1 " + this.rectangleCorner);
	}

	public void update () {

		this.wing1.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.TOP);
		this.wing1.setPosition(0, 0);
		this.wing1.setRotation(0);
		this.wing1.setOriginAbsolute(0, this.width / 2);
		this.wing1.setPosition(this.rectangleCorner);
		this.wing1.setRotation(this.directionOffset.toRadians() + this.getMaster().getRotation().toRadians());

		this.wing2.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.TOP);
		this.wing2.setPosition(0, 0);
		this.wing2.setRotation(0);
		this.wing2.setOriginAbsolute(0, this.width / 2);
		this.wing2.setPosition(this.rectangleCorner);
		this.wing2.setRotation(this.directionOffset.toRadians() + this.getMaster().getRotation().toRadians() - FloatMath.PI() / 2);
	}

	public void also (final Raster wing) {
		this.wing2 = wing.copy();
		final Layer list = this.getMaster().getRasterList();
		list.attachComponent(this.wing2);
		this.wing2.setName("wing2 " + this.rectangleCorner);
	}

}
