
package com.jfixby.telecam.ui.input.cropper;

import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.input.CustomInput;

public class CropperButtonCancel {

	private CustomInput btn;
	private final Cropper master;

	private double baseOffsetX;

	public CropperButtonCancel (final Cropper cropper, final CropperButtonDone btnDone) {
		this.master = cropper;

	}

	public void setup (final CustomInput btn) {
		this.btn = btn;
		this.baseOffsetX = btn.getPositionX();
	}

	public void update (final Rectangle viewport_update) {
		final double width = this.master.getBackground().getWidth();

	}

	public double getBaseOffsetX () {
		return this.baseOffsetX;
	}

}
