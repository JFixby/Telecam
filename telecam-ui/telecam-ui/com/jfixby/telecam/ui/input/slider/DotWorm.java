
package com.jfixby.telecam.ui.input.slider;

import com.jfixby.r3.api.ui.unit.raster.Raster;

public class DotWorm extends DotComponent {

	public DotWorm (final Slider slider, final DotIndicator indicator) {
		super(slider);
	}

	public void hide () {
		final Raster myRaster = this.getRaster();
		myRaster.hide();
	}

}
