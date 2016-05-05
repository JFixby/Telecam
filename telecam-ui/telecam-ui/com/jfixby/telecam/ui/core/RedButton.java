
package com.jfixby.telecam.ui.core;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.CollectionScanner;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.r3.api.ui.unit.ScreenShot;
import com.jfixby.r3.api.ui.unit.ScreenShotSpecs;
import com.jfixby.r3.api.ui.unit.UnitManager;
import com.jfixby.r3.api.ui.unit.input.CustomInput;
import com.jfixby.r3.api.ui.unit.input.MouseEventListener;
import com.jfixby.r3.api.ui.unit.input.MouseMovedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchArea;
import com.jfixby.r3.api.ui.unit.input.TouchDownEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDraggedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchUpEvent;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public class RedButton implements MouseEventListener, CollectionScanner<TouchArea> {

	private Layer root;
	private CustomInput input;
	private Raster white_bridge;
	private Raster whiteL;
	private Raster whiteR;
	private Collection<TouchArea> touchAreas;
	private final CollectionScanner<TouchArea> touchAreasAligner = this;
	private CanvasPosition position;
	private Raster redAnus;
	private Raster redCircle;
	private Raster whiteSquare;
	private final UserPanel master;

	public RedButton (final UserPanel userPanel) {
		this.master = userPanel;
	}

	public void setup (final Layer root) {
		this.root = root;
		this.input = (CustomInput)root.listChildren().getElementAt(0);
		this.input.setInputListener(this);
		this.input.setDebugRenderFlag(false);
		final Collection<Raster> options = this.input.listOptions();
// options.print("options");
// Sys.exit();
		this.white_bridge = options.getElementAt(0);
		this.white_bridge.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);

		this.whiteL = options.getElementAt(1);
		this.whiteR = options.getElementAt(2);

		this.whiteL.setOriginAbsolute(this.white_bridge.getPosition());
		this.whiteR.setOriginAbsolute(this.white_bridge.getPosition());

		this.redAnus = options.getElementAt(3);
		this.redAnus.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);

		this.redCircle = options.getElementAt(4);
		this.redCircle.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);

		this.whiteSquare = options.getElementAt(5);
		this.whiteSquare.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);

		this.touchAreas = this.input.listTouchAreas();

	}

	public void update (final CanvasPosition position) {
		this.position = position;

		this.white_bridge.setPosition(position);

		this.whiteL.setPosition(position);

		this.whiteR.setPosition(position);

		this.redAnus.setPosition(position);

		this.redCircle.setPosition(position);

		this.whiteSquare.setPosition(position);

		Collections.scanCollection(this.touchAreas, this.touchAreasAligner);
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
		final UnitManager man = this.master.getUnit().getUnitManager();
		final ScreenShotSpecs specs = man.getToolkit().newScreenShotSpecs();

		final ScreenShot screenShot = man.getToolkit().newScreenShot(specs);

// final ColorMap map = screenShot.toColorMap();

		final File root = LocalFileSystem.ROOT();
		final File targetFolder = root.child("storage").child("emulated").child("0").child("Pictures");
// targetFolder.listChildren().print();
		final File screenSHotFile = targetFolder.child("screen-" + Sys.SystemTime().currentTimeMillis() + ".png");

		screenShot.saveToFile(screenSHotFile);
		L.d("click!", this);
		return true;
	}

	@Override
	public boolean onTouchDragged (final TouchDraggedEvent input_event) {
		return false;
	}

	@Override
	public void scanElement (final TouchArea element, final int index) {
		element.shape().setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		element.shape().setPosition(this.position);
	}

}
