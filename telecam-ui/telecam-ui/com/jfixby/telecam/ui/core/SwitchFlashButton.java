
package com.jfixby.telecam.ui.core;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.CollectionScanner;
import com.jfixby.cmns.api.floatn.FixedFloat2;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.Geometry;
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
import com.jfixby.r3.api.ui.unit.raster.Raster;

public class SwitchFlashButton implements MouseEventListener, CollectionScanner<TouchArea> {

	private Layer root;
	private CustomInput input;
	private FlashIconWrapper flash_on;
	private FlashIconWrapper flash_off;
	private FlashIconWrapper flash_auto;

	private Collection<TouchArea> touchAreas;
	private final CollectionScanner<TouchArea> touchAreasAligner = this;
	private final CanvasPosition baseOffset;
	private final UserPanel master;
	private final FixedFloat2 originalSceneDimentions;
	private Rectangle screen;

	public SwitchFlashButton (final UserPanel userPanel) {
		this.master = userPanel;

		this.originalSceneDimentions = this.master.getOriginalSceneDimentions();
		this.baseOffset = Geometry.newCanvasPosition();
	}

	public void setup (final Layer root) {
		this.root = root;

		this.input = (CustomInput)root.listChildren().getElementAt(0);

		this.input.setInputListener(this);
		this.input.setDebugRenderFlag(false);
		final Collection<Raster> options = this.input.listOptions();

		options.print("options");

		this.flash_on = new FlashIconWrapper(options.getElementAt(0), this);
		this.flash_auto = new FlashIconWrapper(options.getElementAt(1), this);
		this.flash_off = new FlashIconWrapper(options.getElementAt(2), this);

		this.touchAreas = this.input.listTouchAreas();

// this.baseOffset.set(this.input.getPosition());
		this.baseOffset.setX(this.originalSceneDimentions.getX() - this.input.getPosition().getX());

	}

	public void update (final Rectangle screen) {
		this.screen = screen;
		this.input.setPositionX(this.screen.getWidth() - this.baseOffset.getX());

		this.input.updateChildrenPositionRespectively();
// Collections.scanCollection(this.touchAreas, this.touchAreasAligner);
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
		L.d("click!");
		return true;
	}

	@Override
	public boolean onTouchDragged (final TouchDraggedEvent input_event) {
		return false;
	}

	@Override
	public void scanElement (final TouchArea element, final int index) {
		element.shape().setPositionX(this.screen.getWidth() - this.baseOffset.getX());
	}

}
