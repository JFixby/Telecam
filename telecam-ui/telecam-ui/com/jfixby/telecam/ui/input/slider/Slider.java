
package com.jfixby.telecam.ui.input.slider;

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
import com.jfixby.telecam.ui.UserInputBar;

public class Slider implements MouseEventListener, CollectionScanner<TouchArea> {

	private Layer root;
	private CustomInput input;
	private Raster left;
	private Raster worm;
	private Raster right;

	private Collection<TouchArea> touchAreas;
	private final CollectionScanner<TouchArea> touchAreasAligner = this;

	private final UserInputBar master;
	private final FixedFloat2 originalSceneDimentions;
	private CanvasPosition position;
	private final CanvasPosition baseOffset;
	private Rectangle screen;

	public Slider (final UserInputBar userPanel) {
		this.master = userPanel;
		this.baseOffset = Geometry.newCanvasPosition();
		this.originalSceneDimentions = this.master.getOriginalSceneDimentions();

	}

	public void setup (final Layer root) {
		this.root = root;

		this.input = (CustomInput)root.listChildren().getElementAt(0);

		this.input.setInputListener(this);
		this.input.setDebugRenderFlag(false);
		final Collection<Raster> options = this.input.listOptions();

// options.print("options");

		this.worm = options.getElementAt(0);
// this.worm.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);

		this.left = options.getElementAt(1);
// this.left.setOriginAbsolute(this.worm.getPosition());

		this.right = options.getElementAt(2);
// this.right.setOriginAbsolute(this.worm.getPosition());

		this.touchAreas = this.input.listTouchAreas();

		this.baseOffset.setY(this.originalSceneDimentions.getY() - this.input.getPosition().getY());

	}

	public void update (final CanvasPosition canvasPosition, final Rectangle screen) {
		this.position = canvasPosition;
		this.screen = screen;

		this.input.setPosition(this.position);
		this.input.setPositionY(screen.getHeight() - this.baseOffset.getY());

		this.input.updateChildrenPositionRespectively();
	}

	@Override
	public void scanElement (final TouchArea element, final int index) {
		element.shape().setPosition(this.position);
		element.shape().setPositionY(this.screen.getHeight() - this.baseOffset.getY());

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

}
