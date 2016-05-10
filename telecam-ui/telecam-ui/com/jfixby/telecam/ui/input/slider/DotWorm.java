
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
	private final DotRigh wormRight;
	private final DotLeft wormLeft;

	public DotWorm (final Slider slider, final DotIndicator indicator, final DotLeft wormLeft, final DotRigh wormRight) {
		super(slider);
		this.indicator = indicator;
		this.wormLeft = wormLeft;
		this.wormRight = wormRight;

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
		myRaster.setOriginRelativeX(1);
// this.wormLeft.getRaster().setOriginRelativeX(0);
// this.wormRight.hide();

		this.wormLeft.getRaster().setOriginRelativeX(1);
		this.wormLeft.setOffsetX(-1 * this.indicatorPathLength / 2d);
		this.wormLeft.getRaster().setDebugRenderFlag(!true);

		this.wormRight.getRaster().setOriginRelativeX(0);
		this.wormRight.setOffsetX(+1 * this.indicatorPathLength / 2d);
		this.wormRight.getRaster().setDebugRenderFlag(!true);
	}

	@Override
	public void hide () {
		final Raster myRaster = this.getRaster();
		myRaster.hide();

	}

	public void stretchTo (final double tail, final double head) {
		if (tail < head) {
			this.stretchTo(head, tail);
			return;
		}
		final Raster myRaster = this.getRaster();

		double delta = 0;
		if (this.screenWidth % 2 == 0) {
			delta = -0.5;// ugly hack!
		}
		final double width = (this.indicatorPathLength * (tail - head) / 2d);

		myRaster.setWidth(width);

		final double offsetX = (delta + tail * this.indicatorPathLength / 2d);

		this.setOffsetX(offsetX);
		final double centerX = this.getCenter().getX();

		this.wormLeft.setOffsetX(myRaster.getTopLeftCorner().getX() - centerX);
		this.wormRight.setOffsetX(myRaster.getTopRightCorner().getX() - centerX);

	}

	public void setCenter (final CanvasPosition position, final Rectangle screen) {
		super.setCenter(position);
		this.screenWidth = screen.getWidth();
	}

}
