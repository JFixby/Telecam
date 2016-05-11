
package com.jfixby.telecam.ui.input.red;

import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public class RedCircle extends RasterOffsetComponent {

	private double originalRadius;

	public RedCircle (final RedButton slider) {
		super(slider);
	}

	@Override
	public void setup (final Raster findComponent, final Layer root) {
		super.setup(findComponent, root);
		this.originalRadius = findComponent.getWidth();
	}

	public void setRelativeRadius (final double d) {
		final double radius = d * this.originalRadius;
		this.getRaster().setSize(radius, radius);
	}

}
