
package com.jfixby.telecam.ui.input.cropper;

import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.input.CustomInput;
import com.jfixby.r3.api.ui.unit.input.TouchArea;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public class CropperButtonDone {

	private CustomInput btn;
	private final Cropper master;

	private Raster icon;
	private TouchArea touch;
	private double baseOffsetX;
	private final CropperButtonCancel btnCancel;

	public CropperButtonDone (final Cropper cropper, final CropperButtonCancel btnCancel) {
		this.master = cropper;
		this.btnCancel = btnCancel;

	}

	public void setup (final CustomInput btn) {
		this.btn = btn;
		btn.setDebugRenderFlag(false);
		this.icon = btn.listOptions().getLast();
		this.icon.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);

		this.touch = btn.listTouchAreas().getLast();

		this.touch.shape().setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);

	}

	public void update (final Rectangle viewport_update) {
		this.icon.setPositionY(this.master.getBackground().getGrayPosition().getY());
		this.icon.setPositionX(viewport_update.getWidth() - this.btnCancel.getBaseOffsetX());

		this.touch.shape().setPositionY(this.master.getBackground().getGrayPosition().getY());
		this.touch.shape().setPositionX(viewport_update.getWidth() - this.btnCancel.getBaseOffsetX());

	}

	public double getX () {
		return this.icon.getPositionX();
	}

	public double getY () {
		return this.btn.getPositionY();
	}

}
