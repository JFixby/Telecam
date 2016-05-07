
package com.jfixby.telecam.ui.input.cropper;

import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.input.CustomInput;
import com.jfixby.r3.api.ui.unit.input.TouchArea;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public class CropperButtonRotate {

	private CustomInput btn;
	private final Cropper master;

	private Raster icon;
	private TouchArea touch;
	private final CropperButtonDone btnDone;
	private double baseOffsetY;

	public CropperButtonRotate (final Cropper cropper, final CropperButtonDone btnDone) {
		this.master = cropper;
		this.btnDone = btnDone;

	}

	public void setup (final CustomInput btn) {
		this.btn = btn;
		btn.setDebugRenderFlag(false);
		this.icon = btn.listOptions().getLast();
		this.icon.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.baseOffsetY = this.master.getOriginalSceneDimentions().getY() - this.icon.getPositionY();
		this.touch = btn.listTouchAreas().getLast();

		this.touch.shape().setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);

	}

	public void update (final Rectangle viewport_update) {
		this.icon.setPositionX(this.btnDone.getX());
		this.touch.shape().setPositionX(this.btnDone.getX());

		this.icon.setPositionY(viewport_update.getHeight() - this.baseOffsetY);
		this.touch.shape().setPositionY(viewport_update.getHeight() - this.baseOffsetY);

	}

	public double getX () {
		return this.icon.getPositionX();
	}

	public double getY () {
		return this.icon.getPositionY();
	}

	public double getRasterWidth () {
		return this.icon.getWidth();
	}

	public double getTouchWidth () {
		return this.touch.shape().getWidth();
	}

	public double getTouchHeight () {
		return this.touch.shape().getHeight();
	}

}
