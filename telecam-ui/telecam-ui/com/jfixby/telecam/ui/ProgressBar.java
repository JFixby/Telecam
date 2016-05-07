
package com.jfixby.telecam.ui;

import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.log.L;
import com.jfixby.r3.api.ui.unit.input.MouseEventListener;
import com.jfixby.r3.api.ui.unit.input.MouseMovedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDownEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDraggedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchUpEvent;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public class ProgressBar implements MouseEventListener {

	private Layer root;

	private Raster line;

	private double width;

	public ProgressBar (final UserInputBar userPanel) {
	}

	public void setup (final Layer root) {
		this.root = root;
		this.line = root.findComponent();

	}

	public void update (final BackgroundGray bgGray) {
		this.line.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.LEFT, ORIGIN_RELATIVE_VERTICAL.BOTTOM);

		this.line.setPosition(bgGray.getTopLeftCorner());

		this.width = bgGray.getWidth();
		this.line.setWidth(this.width);

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
		L.d("click", this);
		return true;
	}

	@Override
	public boolean onTouchDragged (final TouchDraggedEvent input_event) {
		return false;
	}

	public void hide () {
		this.root.hide();
	}

	public void show () {
		this.root.show();
	}

}
