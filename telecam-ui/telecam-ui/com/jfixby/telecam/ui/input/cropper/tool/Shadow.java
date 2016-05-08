
package com.jfixby.telecam.ui.input.cropper.tool;

import com.jfixby.r3.api.ui.unit.raster.Raster;

public class Shadow extends CropperElement {

	private static final double HUGE = 10000000f;
	private final Line line;

	public Shadow (final CropperTool cropperTool, final Line line) {
		super(cropperTool);
		this.line = line;

	}

	@Override
	public void setup (final Raster raster) {
		super.setup(raster);
		this.getRaster().setName("shadow: " + this.line);
	}

	public void update () {
		final Raster shadow = this.getRaster();
		shadow.setPosition(this.line.getPosition());
		shadow.setWidth(HUGE);
		shadow.setHeight(HUGE);

	}

}
