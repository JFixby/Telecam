
package com.jfixby.telecam.ui.input.cropper.tool;

import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public class CropperElement {

	private final CropperTool master;

	public CropperElement (final CropperTool master) {
		this.master = master;
	}

	public void setup (final Raster shadow) {
		final Layer list = this.master.getRasterList();
		list.attachComponent(shadow);
	}
}
