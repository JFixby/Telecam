
package com.jfixby.telecam.ui.input.blue;

import com.jfixby.r3.api.ui.UI;
import com.jfixby.r3.api.ui.unit.input.CustomInput;
import com.jfixby.r3.api.ui.unit.input.MouseMovedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchArea;
import com.jfixby.r3.api.ui.unit.input.TouchDownEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDraggedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchUpEvent;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.update.UnitClocks;
import com.jfixby.r3.api.ui.unit.user.MouseInputEventListener;
import com.jfixby.r3.api.ui.unit.user.UpdateListener;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.CollectionScanner;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.scarabei.api.sys.Sys;
import com.jfixby.telecam.ui.UserInputBar;
import com.jfixby.telecam.ui.actions.TelecamUIAction;

public class BlueButton extends MouseInputEventListener implements CollectionScanner<TouchArea> {

	private Layer root;
	private CustomInput input;
	private Raster white;
	private Raster blue;
	private Raster dark_blue;
	private Collection<TouchArea> touchAreas;
	private final CollectionScanner<TouchArea> touchAreasAligner = this;
	private CanvasPosition position;
	private final UserInputBar master;
	private double blueRadiusNormal;
	private double blueRadiusSquized;
	private double blueWidth;

	public BlueButton (final UserInputBar userPanel) {
		this.master = userPanel;
	}

	public void setup (final Layer root) {
		this.root = root;
		this.input = (CustomInput)root.listChildren().getElementAt(0);
		this.input.setInputListener(this);
		this.input.setDebugRenderFlag(false);
		final Collection<Raster> options = this.input.listOptions();
		this.white = options.getElementAt(0);
		this.blue = options.getElementAt(1);
		this.blueWidth = this.blue.getWidth();

		this.dark_blue = options.getElementAt(2);
		this.touchAreas = this.input.listTouchAreas();

		this.blueRadiusNormal = this.blue.shape().getWidth();
		this.blueRadiusSquized = this.dark_blue.shape().getWidth() * 1.1;
		this.root.attachComponent(this.updater);
// root.detatchAllComponents();
	}

	public void update (final CanvasPosition position) {
		this.position = position;
		this.white.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.blue.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.dark_blue.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.white.setPosition(position);
		this.blue.setPosition(position);
		this.dark_blue.setPosition(position);
		Collections.scanCollection(this.touchAreas, this.touchAreasAligner);
	}

	long touchBeginTime;
	long touchCurrentTime;
	long touchLongTap;
	long LONG_TAP_DELPTA_PERIOD = 1500;
	boolean pressed = false;
	final Float2 touchBegin = Geometry.newFloat2();
	final Float2 touchCurrent = Geometry.newFloat2();

	@Override
	public boolean onMouseMoved (final MouseMovedEvent input_event) {
		this.pressed = false;
		return true;
	}

	@Override
	public boolean onTouchDown (final TouchDownEvent input_event) {
		this.touchBeginTime = Sys.SystemTime().currentTimeMillis();
		this.touchLongTap = this.touchBeginTime + this.LONG_TAP_DELPTA_PERIOD;
		this.pressed = true;

		UI.pushAction(TelecamUIAction.pressBlueButton);
		this.touchBegin.set(input_event.getCanvasPosition());

		return true;
	}

	final UpdateListener updater = new UpdateListener() {
		@Override
		public void onUpdate (final UnitClocks unit_clock) {
			BlueButton.this.touchCurrentTime = Sys.SystemTime().currentTimeMillis();
			if (!BlueButton.this.pressed) {
				return;
			}

			if (BlueButton.this.touchCurrentTime < BlueButton.this.touchLongTap) {
				return;
			} else {
				BlueButton.this.pressed = false;
// TelecamUIAction.disableInput.push();
				TelecamUIAction.switchToVideoShoot.submit();
				TelecamUIAction.goVideoRecording.submit();
				TelecamUIAction.doRecordVideo.submit();
// TelecamUIAction.enableInput.push();
			}

		}
	};

	@Override
	public boolean onTouchUp (final TouchUpEvent input_event) {
		if (!this.pressed) {
			return false;
		}
		this.pressed = false;
		if (this.touchCurrentTime < this.touchLongTap) {
			this.clickBlue();
		}
		return true;
	}

	@Override
	public boolean onTouchDragged (final TouchDraggedEvent input_event) {
		if (!this.pressed) {
			return false;
		}
		this.touchCurrent.set(input_event.getCanvasPosition());
		final double TOUCH_MAX_DELTA = this.blueWidth / 8 * 0 + 0;
// L.d(this.blueWidth);
		if (this.touchCurrent.distanceTo(this.touchBegin) >= TOUCH_MAX_DELTA) {
			this.clickBlue();
		}
		return true;
	}

	private void clickBlue () {
		this.pressed = false;
		TelecamUIAction.disableInput.submit();
		TelecamUIAction.doBlink.submit();
		TelecamUIAction.releaseBlueButton.submit();
		TelecamUIAction.goAcceptDeclinePhoto.submit();
		TelecamUIAction.enableInput.submit();
	}

	@Override
	public void scanElement (final TouchArea element, final long index) {
		element.shape().setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		element.shape().setPosition(this.position);
	}

	public void hide () {
		this.root.hide();
	}

	public void show () {
		this.root.show();
	}

	public void press () {
		this.blue.shape().setSize(this.blueRadiusSquized, this.blueRadiusSquized);
	}

	public void setShootProgress (final float f) {
		final double radius = this.blueRadiusSquized + (this.blueRadiusNormal - this.blueRadiusSquized) * (1d - f);
		this.blue.shape().setSize(radius, radius);
	}

	public void release () {
		this.blue.shape().setSize(this.blueRadiusNormal, this.blueRadiusNormal);
	}

}
