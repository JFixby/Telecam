
package com.jfixby.telecam.ui.input.slider;

import com.jfixby.cmns.api.floatn.FixedFloat2;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.update.OnUpdateListener;
import com.jfixby.r3.api.ui.unit.update.UnitClocks;

public class DotIndicator extends DotComponent implements OnUpdateListener {

	private final FixedFloat2 leftOffset;
	private final FixedFloat2 rightOffset;
	OnUpdateListener animator = this;
	private final CanvasPosition tmp;
	private double componentWidth;

	public DotIndicator (final Slider slider, final DotLeft left, final DotRigh right) {
		super(slider);
		this.leftOffset = left.getOriginalOffset();
		this.rightOffset = right.getOriginalOffset();

		this.tmp = Geometry.newCanvasPosition();
	}

	@Override
	public void setup (final Raster findComponent, final Layer root) {
		super.setup(findComponent, root);
// root.attachComponent(this.animator);
		this.componentWidth = this.rightOffset.getX() - this.leftOffset.getX();
	}

	@Override
	public void onUpdate (final UnitClocks unit_clock) {
		final double time = 2 * Sys.SystemTime().currentTimeMillis() / 1000d;
		final double alpha = (Math.sin(time));
		this.setSliderState(alpha);
	}

	public void setSliderState (final double position) {// [-1;+1]
		this.setOffsetX(position * this.componentWidth / 2);
	}

}
