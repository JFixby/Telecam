
package com.jfixby.telecam.ui.input.slider;

import com.jfixby.cmns.api.floatn.Float2;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public class DotComponent {

	private final Slider master;
	private Raster raster;
	private final CanvasPosition originalOffset;
	private final CanvasPosition tmp;

	public DotComponent (final Slider slider) {
		this.master = slider;
		this.originalOffset = Geometry.newCanvasPosition();
		this.tmp = Geometry.newCanvasPosition();
	}

	public void setup (final Raster findComponent) {
		this.raster = findComponent;
		this.raster.setOriginAbsolute(this.master.getPosition());
		findComponent.setDebugRenderFlag(true);
		this.raster.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.originalOffset.setPosition(this.raster.getPosition());
		this.originalOffset.subtract(this.master.getPosition());
	}

	public void setCenter (final Float2 position) {
		this.tmp.set(position);
		this.tmp.add(this.originalOffset);
		this.raster.setPosition(this.tmp);
	}

}
