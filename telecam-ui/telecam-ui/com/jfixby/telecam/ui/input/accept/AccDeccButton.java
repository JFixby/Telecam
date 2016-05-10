
package com.jfixby.telecam.ui.input.accept;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.CollectionScanner;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.input.CustomInput;
import com.jfixby.r3.api.ui.unit.input.MouseEventListener;
import com.jfixby.r3.api.ui.unit.input.TouchArea;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public abstract class AccDeccButton implements MouseEventListener, CollectionScanner<TouchArea> {
	private final AcceptDecline master;
	private Layer root;
	private CustomInput input;
	private Collection<TouchArea> touchAreas;
	private CanvasPosition position;
	private Raster pressed;
	private Raster released;
	private final float horizontalAlignment;
	private final CollectionScanner<TouchArea> touchAreasAligner = this;
	private double originalSize;

	public AcceptDecline getMaster () {
		return this.master;
	}

	public AccDeccButton (final AcceptDecline acceptDecline, final float horizontalAlignment) {
		this.master = acceptDecline;
		this.horizontalAlignment = horizontalAlignment;
	}

	public void setup (final Layer root) {
		this.root = root;
		this.input = (CustomInput)root.listChildren().getElementAt(0);
		this.input.setInputListener(this);
		this.input.setDebugRenderFlag(false);
		final Collection<Raster> options = this.input.listOptions();
		this.touchAreas = this.input.listTouchAreas();

		this.pressed = options.getElementAt(0);
		this.released = options.getElementAt(1);
		this.originalSize = this.released.shape().getWidth();

// options.print("options");
// Sys.exit();
	}

	public void update (final CanvasPosition position, final Rectangle viewport_update) {
		this.position = position;

		this.pressed.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.released.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);

		this.pressed.setPositionX(position.getX() * this.horizontalAlignment);
		this.pressed.setPositionY(position.getY());

		this.released.setPositionX(position.getX() * this.horizontalAlignment);
		this.released.setPositionY(position.getY());

		Collections.scanCollection(this.touchAreas, this.touchAreasAligner);
	}

	@Override
	public void scanElement (final TouchArea element, final int index) {
		element.shape().setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		element.shape().setPosition(this.position);
		element.shape().setPositionX(this.position.getX() * this.horizontalAlignment);
		element.shape().setPositionY(this.position.getY());

	}

	public void setRadius (final double radius) {
		final int size = (int)(this.originalSize * radius);
		this.released.setSize(size, size);
		this.pressed.setSize(size, size);
	}
}
