
package com.jfixby.telecam.ui.input.cropper;

import com.jfixby.r3.api.ui.UI;
import com.jfixby.r3.api.ui.unit.input.CustomInput;
import com.jfixby.r3.api.ui.unit.input.MouseMovedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchArea;
import com.jfixby.r3.api.ui.unit.input.TouchDownEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDraggedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchUpEvent;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.user.MouseInputEventListener;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.telecam.ui.actions.TelecamUIAction;

public class CropperButtonDone implements MouseInputEventListener {

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
		this.touch.setInputListener(this);
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

	@Override
	public boolean onMouseMoved (final MouseMovedEvent input_event) {
		return false;
	}

	@Override
	public boolean onTouchDown (final TouchDownEvent input_event) {
		return false;
	}

	@Override
	public boolean onTouchUp (final TouchUpEvent input_event) {
		UI.pushAction(TelecamUIAction.goAndroidImageGallery);
		return true;
	}

	@Override
	public boolean onTouchDragged (final TouchDraggedEvent input_event) {
		return false;
	}

}
