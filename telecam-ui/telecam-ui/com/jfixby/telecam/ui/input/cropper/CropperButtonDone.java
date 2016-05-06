
package com.jfixby.telecam.ui.input.cropper;

import com.jfixby.cmns.api.floatn.FixedFloat2;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.input.CustomInput;

public class CropperButtonDone {

	private CustomInput btn;
	private final Cropper master;

	private double baseOffsetX;
	private double baseOffsetY;
	private final FixedFloat2 originalSceneDimentions;

	public CropperButtonDone (final Cropper cropper) {
		this.master = cropper;
		this.originalSceneDimentions = this.master.getOriginalSceneDimentions();

	}

	public void setup (final CustomInput btn) {
		this.btn = btn;
		btn.setDebugRenderFlag(!false);
		this.baseOffsetX = this.originalSceneDimentions.getX() - btn.getPositionX();
		this.baseOffsetY = this.originalSceneDimentions.getY() - btn.getPositionY();

	}

	public void update (final Rectangle viewport_update) {

		this.btn.setPositionX(viewport_update.getWidth() - this.baseOffsetX);
		this.btn.setPositionY(viewport_update.getHeight() - this.baseOffsetY);
		this.btn.updateChildrenPositionRespectively();

	}

	public double getBaseOffsetX () {
		return this.baseOffsetX;
	}

	public void setBaseOffsetX (final double baseOffsetX) {
		this.baseOffsetX = baseOffsetX;
	}

}
