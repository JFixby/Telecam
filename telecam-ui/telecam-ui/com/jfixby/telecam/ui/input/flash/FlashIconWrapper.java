
package com.jfixby.telecam.ui.input.flash;

import com.jfixby.r3.api.ui.unit.raster.Raster;

public class FlashIconWrapper {

	private final Raster raster;
	private final SwitchFlashButton master;

	public FlashIconWrapper (final Raster elementAt, final SwitchFlashButton master) {
		this.raster = elementAt;
		this.master = master;
	}

}
