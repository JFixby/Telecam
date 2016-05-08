
package com.jfixby.telecam.ui.input.cropper.tool;

import com.jfixby.cmns.api.math.MathTools;
import com.jfixby.cmns.api.math.VectorTool;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public class CropperElement {

	public CropperTool getMaster () {
		return this.master;
	}

	public Raster getRaster () {
		return this.raster;
	}

	private final CropperTool master;
	private Raster raster;
	private final VectorTool vectorTool;

	public VectorTool getVectorTool () {
		return this.vectorTool;
	}

	public CropperElement (final CropperTool master) {
		this.master = master;
		this.vectorTool = MathTools.newVectorTool();
	}

	public void setup (final Raster raster) {
		this.raster = raster.copy();
		final Layer list = this.master.getRasterList();
		list.attachComponent(this.raster);
		this.raster.setPosition(0, 0);

	}
}
