
package com.jfixby.telecam.ui.input.red;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.CollectionScanner;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.r3.api.ui.unit.input.CustomInput;
import com.jfixby.r3.api.ui.unit.input.MouseMovedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchArea;
import com.jfixby.r3.api.ui.unit.input.TouchDownEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDraggedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchUpEvent;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.user.MouseInputEventListener;
import com.jfixby.telecam.ui.UserInputBar;
import com.jfixby.telecam.ui.actions.TelecamUIAction;

public class RedButton implements MouseInputEventListener, CollectionScanner<TouchArea> {

	private Layer root;
	private CustomInput input;
	private final Brigde white_bridge;
	private final LeftCircle whiteL;
	private final RightCircle whiteR;
	private Collection<TouchArea> touchAreas;
	private final CollectionScanner<TouchArea> touchAreasAligner = this;
	private final CanvasPosition position = Geometry.newCanvasPosition();
	private Raster redAnus;
	private final RedCircle redCircle;
	private final WhiteSquare whiteSquare;
	private final UserInputBar master;
	private String mode = READY_TO_RECORD;

	public RedButton (final UserInputBar userPanel) {
		this.master = userPanel;
		this.whiteR = new RightCircle(this);
		this.whiteL = new LeftCircle(this);
		this.white_bridge = new Brigde(this, this.whiteR, this.whiteL);
		this.redCircle = new RedCircle(this);
		this.whiteSquare = new WhiteSquare(this);
	}

	public void setup (final Layer root) {
		this.root = root;
		this.input = (CustomInput)root.listChildren().getElementAt(0);
		this.input.setInputListener(this);
		this.input.setDebugRenderFlag(false);
		this.position.set(this.input.getPosition());

		final Collection<Raster> options = this.input.listOptions();

		final Raster white_bridge = options.getElementAt(0);
// white_bridge.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);

		final Raster whiteL = options.getElementAt(1);
		final Raster whiteR = options.getElementAt(2);

		this.whiteR.setup(whiteR, root);
		this.whiteL.setup(whiteL, root);
		this.white_bridge.setup(white_bridge, root);

		whiteL.setOriginAbsolute(this.position);
		whiteR.setOriginAbsolute(this.position);

		this.redAnus = options.getElementAt(3);
// this.redAnus.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.redAnus.hide();

		final Raster redCircle = options.getElementAt(4);
// redCircle.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.redCircle.setup(redCircle, root);

		final Raster whiteSquare = options.getElementAt(5);
// whiteSquare.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);

		this.whiteSquare.setup(whiteSquare, root);
		whiteSquare.hide();
		this.touchAreas = this.input.listTouchAreas();

	}

	public void setWide (final double redToWide) {// [0,1]
		final double m = 1 / 4d;
		final double targetWidth = this.white_bridge.getComponentWidth() / 4 * m;
		final double shift = targetWidth / 4d;
		this.whiteR.setOffsetX(shift * redToWide);
		this.whiteL.setOffsetX(-shift * redToWide);
		this.white_bridge.getRaster().setWidth(targetWidth * redToWide * 4 / m);
		final double radius = 1 - redToWide * 0.75;
		this.redCircle.setRelativeRadius(radius);

	}

	public CanvasPosition getPosition () {
		return this.position;
	}

	public void update (final CanvasPosition position) {
		this.position.set(position);
		this.whiteL.setCenter(this.position);
		this.whiteR.setCenter(this.position);
		this.redAnus.setPosition(this.position);
		this.redCircle.setCenter(this.position);
		this.whiteSquare.setCenter(this.position);
		this.white_bridge.setCenter(this.position);

		Collections.scanCollection(this.touchAreas, this.touchAreasAligner);
	}

	@Override
	public boolean onMouseMoved (final MouseMovedEvent input_event) {
		return true;
	}

	@Override
	public boolean onTouchDown (final TouchDownEvent input_event) {
		TelecamUIAction.disableInput.submit();
		if (this.mode == READY_TO_RECORD) {
			TelecamUIAction.goVideoRecording.submit();
			TelecamUIAction.doRecordVideo.submit();
		} else {
			TelecamUIAction.doStopRecordVideo.submit();
			TelecamUIAction.goVideoIdle.submit();
			TelecamUIAction.goAcceptDeclineVideo.submit();
		}
		TelecamUIAction.enableInput.submit();

		return true;
	}

	@Override
	public boolean onTouchUp (final TouchUpEvent input_event) {
// final UnitManager man = this.master.getUnit().getUnitManager();
// final ScreenShotSpecs specs = man.getToolkit().newScreenShotSpecs();
//
// final ScreenShot screenShot = man.getToolkit().newScreenShot(specs);
//
//// final ColorMap map = screenShot.toColorMap();
//
// final File root = LocalFileSystem.ROOT();
// final File targetFolder = root.child("storage").child("emulated").child("0").child("Pictures");
//// targetFolder.listChildren().print();
// final File screenSHotFile = targetFolder.child("screen-" + Sys.SystemTime().currentTimeMillis() + ".png");
//
//// screenShot.saveToFile(screenSHotFile);
// L.d("click!", this);
		return true;
	}

	@Override
	public boolean onTouchDragged (final TouchDraggedEvent input_event) {
		return true;
	}

	@Override
	public void scanElement (final TouchArea element, final long index) {
		element.shape().setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		element.shape().setPosition(this.position);
	}

	public void hide () {
		this.root.hide();
	}

	public void setWideMin () {
	}

	public void setWideMax () {
	}

	public void show () {
		this.root.show();
	}

	public void convertToSquare (final double progress) {
		final double redToWide = 1d - progress;
		this.setWide(redToWide);
	}

	public void convertToSquare () {
		this.setWide(0);
		this.whiteL.hide();
		this.whiteR.hide();
		this.white_bridge.hide();
		this.whiteSquare.show();
		this.setMode(RECORDING);
	}

	private void setMode (final String mode) {
		this.mode = mode;
	}

	static final public String RECORDING = "RECORDING";
	static final public String READY_TO_RECORD = "READY_TO_RECORD";

	public void convertBack (final double progress) {
		final double redToWide = progress;
		this.setWide(redToWide);
	}

	public void convertBack () {
		this.setWide(0);
		this.whiteL.show();
		this.whiteR.show();
		this.white_bridge.show();
		this.whiteSquare.hide();
		this.setMode(READY_TO_RECORD);
	}
}
