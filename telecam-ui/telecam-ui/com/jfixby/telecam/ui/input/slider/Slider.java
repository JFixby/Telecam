
package com.jfixby.telecam.ui.input.slider;

import com.jfixby.r3.api.ui.unit.animation.OnAnimationDoneListener;
import com.jfixby.r3.api.ui.unit.input.CustomInput;
import com.jfixby.r3.api.ui.unit.input.MouseMovedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchArea;
import com.jfixby.r3.api.ui.unit.input.TouchDownEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDraggedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchUpEvent;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.user.MouseInputEventListener;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.CollectionScanner;
import com.jfixby.scarabei.api.floatn.FixedFloat2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.telecam.ui.BackgroundGray;
import com.jfixby.telecam.ui.UserInputBar;
import com.jfixby.telecam.ui.actions.TelecamUIAction;
import com.jfixby.telecam.ui.input.blue.BlueButton;
import com.jfixby.telecam.ui.input.flash.SwitchFlashButton;
import com.jfixby.telecam.ui.input.red.RedButton;
import com.jfixby.telecam.ui.input.swcam.SwitchCameraButton;

public class Slider implements MouseInputEventListener, CollectionScanner<TouchArea> {

	private static final String PHOTO = "PHOTO";
	private static final String VIDEO = "VIDEO";
	private Layer root;
	private CustomInput input;
	private final DotLeft left = new DotLeft(this);
	private final DotRigh right = new DotRigh(this);
	private final DotLeft wormLeft = new DotLeft(this);
	private final DotRigh wormRight = new DotRigh(this);
	private final DotIndicator indicator = new DotIndicator(this, this.left, this.right);
	private final DotWorm worm;

	private final SliderAnimator animator;

	String state = PHOTO;

	private Collection<TouchArea> touchAreas;
	private final CollectionScanner<TouchArea> touchAreasAligner = this;

	private final UserInputBar master;
	private final FixedFloat2 originalSceneDimentions;
	private final CanvasPosition position = Geometry.newCanvasPosition();
	private final CanvasPosition baseOffset;
	private Rectangle screen;
	private Layer rasterlayer;
	private final BackgroundGray bgGray;
// private final BlueButton blueButton;
// private final RedButton redButton;
	private final SwitchCameraButton switchCameraButton;
	private final SwitchFlashButton switchFlashButton;

	public Slider (final UserInputBar userPanel, final BackgroundGray bgGray, final BlueButton blueButton,
		final RedButton redButton, final SwitchCameraButton switchCameraButton, final SwitchFlashButton switchFlashButton) {
		this.switchFlashButton = switchFlashButton;
		this.master = userPanel;
		this.baseOffset = Geometry.newCanvasPosition();
		this.originalSceneDimentions = this.master.getOriginalSceneDimentions();
		this.bgGray = bgGray;
// this.blueButton = blueButton;
// this.redButton = redButton;
		this.switchCameraButton = switchCameraButton;
		this.worm = new DotWorm(this, this.indicator, this.wormLeft, this.wormRight, bgGray);
		this.animator = new SliderAnimator(this, this.indicator, this.worm, bgGray, blueButton, redButton, switchFlashButton);
	}

	public void setup (final Layer root) {
		this.root = root;

		this.input = (CustomInput)root.listChildren().getElementAt(0);
		this.position.setPosition(this.input.getPosition());
		this.input.setInputListener(this);
		this.input.setDebugRenderFlag(false);

		this.rasterlayer = root.findComponent("raster");
		this.left.setup((Raster)this.rasterlayer.findComponent("L"), root);
		this.left.hide();

		this.right.setup((Raster)this.rasterlayer.findComponent("R"), root);
		this.right.hide();

		this.wormLeft.setup((Raster)this.rasterlayer.findComponent("worm-L"), root);
		this.wormRight.setup((Raster)this.rasterlayer.findComponent("worm-R"), root);

		this.indicator.setup((Raster)this.rasterlayer.findComponent("I"), root);
		this.worm.setup((Raster)this.rasterlayer.findComponent("worm"), root);

		this.touchAreas = this.input.listTouchAreas();

		this.baseOffset.setY(this.originalSceneDimentions.getY() - this.input.getPosition().getY());
// this.worm.hide();
		this.animator.setup();
		this.setPhotoMode();
// this.setVideoMode();
	}

	public void sendSliderToVideo (final OnAnimationDoneListener animation_done_listener) {
		this.animator.sendSliderToVideo(animation_done_listener);
		this.state = VIDEO;
		this.switchFlashButton.hide();
// this.blueButton.hide();
// this.redButton.show();
// this.switchCameraButton.hide();
	}

	public void sendSliderToPhoto (final OnAnimationDoneListener animation_done_listener) {
		this.animator.sendSliderToPhoto(animation_done_listener);
		this.state = PHOTO;
		this.switchFlashButton.show();
// this.blueButton.show();
// this.redButton.hide();
// this.switchCameraButton.show();
	}

	public void setPhotoMode () {
		this.indicator.setSliderState(-1);
		this.worm.stretchTo(+1, +1);
		this.bgGray.setBackgroundOpacity(1f);

// this.redButton.setWideMin();
// this.blueButton.show();
// this.redButton.hide();
		this.state = PHOTO;
	}

	public void setVideoMode () {
		this.indicator.setSliderState(+1);
		this.worm.stretchTo(-1, -1);

		this.bgGray.setBackgroundOpacity(0.5f);
		this.state = VIDEO;
// this.redButton.setWideMax();
// this.blueButton.hide();
// this.redButton.show();

	}

	public void update (final CanvasPosition canvasPosition, final Rectangle screen) {

		this.screen = screen;

		this.input.setPosition(canvasPosition);
		this.input.setPositionY(screen.getHeight() - this.baseOffset.getY());

		this.position.setPosition(this.input.getPosition());

		this.touchAreas.getLast().shape().setPosition(this.position);
		this.worm.setCenter(this.position, screen);
		this.left.setCenter(this.position);
		this.right.setCenter(this.position);
		this.wormLeft.setCenter(this.position);
		this.wormRight.setCenter(this.position);
		this.indicator.setCenter(this.position);

	}

	@Override
	public void scanElement (final TouchArea element, final long index) {
		element.shape().setPosition(this.position);
		element.shape().setPositionY(this.screen.getHeight() - this.baseOffset.getY());

	}

	@Override
	public boolean onMouseMoved (final MouseMovedEvent input_event) {
		return false;
	}

	@Override
	public boolean onTouchDown (final TouchDownEvent input_event) {
		TelecamUIAction.disableInput.submit();
		if (this.state == PHOTO) {
			TelecamUIAction.switchToVideoShoot.submit();
		} else {
			TelecamUIAction.switchToPhotoShoot.submit();
		}
		TelecamUIAction.enableInput.submit();
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

	public void hide () {
		this.root.hide();
	}

	public void show () {
		this.root.show();
	}

	public CanvasPosition getPosition () {
		return this.position;
	}

	public Layer getRoot () {
		return this.root;
	}

}
