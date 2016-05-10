
package com.jfixby.telecam.ui.input.swcam;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.CollectionScanner;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.r3.api.ui.unit.animation.OnAnimationDoneListener;
import com.jfixby.r3.api.ui.unit.input.CustomInput;
import com.jfixby.r3.api.ui.unit.input.MouseEventListener;
import com.jfixby.r3.api.ui.unit.input.MouseMovedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchArea;
import com.jfixby.r3.api.ui.unit.input.TouchDownEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDraggedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchUpEvent;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.telecam.ui.UserInputBar;
import com.jfixby.telecam.ui.actions.TelecamUIAction;

public class SwitchCameraButton implements MouseEventListener, CollectionScanner<TouchArea> {

	private Layer root;
	private CustomInput input;
	private Raster center;
	private Raster ring;
	private Raster roll;

	private Collection<TouchArea> touchAreas;
	private final CollectionScanner<TouchArea> touchAreasAligner = this;
	private CanvasPosition position;
	SwitchCamButtonAnimator animator = new SwitchCamButtonAnimator(this);

	public SwitchCameraButton (final UserInputBar userPanel) {
	}

	public void setup (final Layer root) {
		this.root = root;

		this.input = (CustomInput)root.listChildren().getElementAt(0);
		this.roll = (Raster)root.listChildren().getElementAt(1);
// this.roll.setOpacity(0.5);
		this.input.setInputListener(this);
		this.input.setDebugRenderFlag(false);

		this.center = this.input.listOptions().getElementAt(1);
		this.ring = this.input.listOptions().getElementAt(0);

		this.touchAreas = this.input.listTouchAreas();
		this.animator.setup(root, this.center, this.ring, this.roll);
	}

	public void update (final CanvasPosition position) {
		this.position = position;
		this.center.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.ring.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.roll.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);

		this.center.setPositionX(position.getX() / 2d);
		this.center.setPositionY(position.getY());

		this.ring.setPositionX(position.getX() / 2d);
		this.ring.setPositionY(position.getY());

		this.roll.setPositionX(position.getX() / 2d);
		this.roll.setPositionY(position.getY() * 1d);

		Collections.scanCollection(this.touchAreas, this.touchAreasAligner);
	}

	@Override
	public boolean onMouseMoved (final MouseMovedEvent input_event) {
		return false;
	}

	@Override
	public boolean onTouchDown (final TouchDownEvent input_event) {
		TelecamUIAction.doSwitchCam.push();
		return true;
	}

	@Override
	public boolean onTouchUp (final TouchUpEvent input_event) {

		return false;
	}

	@Override
	public boolean onTouchDragged (final TouchDraggedEvent input_event) {
		return false;
	}

	@Override
	public void scanElement (final TouchArea element, final int index) {
		element.shape().setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		element.shape().setPosition(this.position);
		element.shape().setPositionX(this.position.getX() / 2d);
		element.shape().setPositionY(this.position.getY());

	}

	public void hide () {
		this.root.hide();
	}

	public void show () {
		this.root.show();
	}

	public void switchCamera (final OnAnimationDoneListener animation_done_listener) {
		this.animator.roll(animation_done_listener);
	}

}
