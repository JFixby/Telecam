
package com.jfixby.telecam.ui.input.cropper;

import com.jfixby.cmns.api.floatn.FixedFloat2;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.input.CustomInput;

public class CropperButtonCancel {

	private CustomInput btn;
	private final Cropper master;

	private double baseOffsetX;
	private double baseOffsetY;
	private final FixedFloat2 originalSceneDimentions;

	public CropperButtonCancel (final Cropper cropper, final CropperButtonDone btnDone) {
		this.master = cropper;
		this.originalSceneDimentions = this.master.getOriginalSceneDimentions();

	}

	public void setup (final CustomInput btn) {
		this.btn = btn;
		btn.setDebugRenderFlag(!false);
		this.baseOffsetX = btn.getPositionX();
		this.baseOffsetY = this.originalSceneDimentions.getY() - btn.getPositionY();

	}

	public void update (final Rectangle viewport_update) {
		this.btn.setPositionX(this.baseOffsetX);
		this.btn.setPositionY(viewport_update.getHeight() - this.baseOffsetY);
		this.btn.updateChildrenPositionRespectively();

	}

	public double getBaseOffsetX () {
		return this.baseOffsetX;
	}

}
