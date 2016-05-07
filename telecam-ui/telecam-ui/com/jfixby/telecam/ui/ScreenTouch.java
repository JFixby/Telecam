
package com.jfixby.telecam.ui;

import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.cmns.api.log.L;
import com.jfixby.r3.api.ui.unit.input.MouseEventListener;
import com.jfixby.r3.api.ui.unit.input.MouseMovedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchArea;
import com.jfixby.r3.api.ui.unit.input.TouchDownEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDraggedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchUpEvent;
import com.jfixby.r3.api.ui.unit.layer.Layer;

public class ScreenTouch implements MouseEventListener {
	TouchArea touch;
	private final UserInputBar master;
	private double baseOffsetY;

	public ScreenTouch (final UserInputBar userInputBar) {
		this.master = userInputBar;
	}

	public void setup (final Layer button_root) {
		button_root.printChildren("button_root");
		this.touch = button_root.findComponent();
		this.touch.setInputListener(this);
		this.baseOffsetY = this.master.getOriginalSceneDimentions().getY() - this.touch.shape().getHeight();
	}

	public void hide () {
	}

	public void update (final Rectangle viewport_update) {
		this.touch.shape().setHeight(viewport_update.getHeight() - this.baseOffsetY);
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
