
package com.jfixby.telecam.ui.input.slider;

import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public class DotWorm extends DotComponent {

	private final DotIndicator indicator;
	private double indicatorWidth;
	private double wormWidth;
	private double indicatorPathLength;
	private double l;
	private double r;
	private double screenWidth;

	public DotWorm (final Slider slider, final DotIndicator indicator) {
		super(slider);
		this.indicator = indicator;

	}

	@Override
	public void setup (final Raster myRaster, final Layer root) {
		super.setup(myRaster, root);
		this.indicatorWidth = this.indicator.getRaster().getWidth();
		this.indicatorPathLength = this.indicator.getComponentWidth();
		this.wormWidth = this.indicatorPathLength + this.indicatorWidth;
		this.l = -this.indicatorPathLength / 2;
		this.r = -this.l;
		myRaster.setDebugRenderFlag(!true);
		myRaster.setOriginRelativeX(0);
	}

	public void hide () {
		final Raster myRaster = this.getRaster();
		myRaster.hide();

	}

	public void stretchTo (final double tail, final double head) {
		final Raster myRaster = this.getRaster();

		final double center = (tail + head) / 2d;
		double delta = 0;
		if (this.screenWidth % 2 == 0) {
			delta = -0.5;// ugly hack!
		}
		myRaster.setWidth(-(this.indicatorPathLength * (tail - head) / 2d));

		final double offsetX = (delta + tail * this.indicatorPathLength / 2d);

		this.setOffsetX(offsetX);

	}

	public void setCenter (final CanvasPosition position, final Rectangle screen) {
		super.setCenter(position);
		this.screenWidth = screen.getWidth();
	}

}
