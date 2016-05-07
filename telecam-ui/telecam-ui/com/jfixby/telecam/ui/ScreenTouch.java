
package com.jfixby.telecam.ui;

import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.cmns.api.log.L;
import com.jfixby.r3.api.ui.unit.input.CustomInput;
import com.jfixby.r3.api.ui.unit.input.MouseEventListener;
import com.jfixby.r3.api.ui.unit.input.MouseMovedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchArea;
import com.jfixby.r3.api.ui.unit.input.TouchDownEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDraggedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchUpEvent;
import com.jfixby.r3.api.ui.unit.layer.Layer;

public class ScreenTouch implements MouseEventListener {
	CustomInput input;
	private final UserInputBar master;
	private double baseOffsetY;
	private TouchArea touch;

	public ScreenTouch (final UserInputBar userInputBar) {
		this.master = userInputBar;
	}

	public void setup (final Layer button_root) {
		this.input = button_root.findComponent();
		this.input.setInputListener(this);
		this.input.setDebugRenderFlag(false);
		this.touch = this.input.listTouchAreas().getLast();
		this.baseOffsetY = this.master.getOriginalSceneDimentions().getY() - this.touch.shape().getHeight();
		this.touch.shape().setPosition();
	}

	public void hide () {
	}

	public void update (final Rectangle viewport_update) {
		final double H = viewport_update.getHeight() - this.baseOffsetY;
		this.touch.shape().setHeight(H);
		this.touch.shape().setWidth(viewport_update.getWidth());
		this.touch.shape().setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.touch.shape().setPosition(viewport_update.getWidth() / 2, H / 2);
		this.touch.shape().reScale(0.95, 0.95);
	}

	@Override
	public boolean onMouseMoved (final MouseMovedEvent input_event) {
		L.d(this + "", input_event);
		return true;
	}

	@Override
	public boolean onTouchDown (final TouchDownEvent input_event) {
		L.d(this + "", input_event);
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

}
