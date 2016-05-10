
package com.jfixby.telecam.ui.input.slider;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.CollectionScanner;
import com.jfixby.cmns.api.floatn.FixedFloat2;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.cmns.api.geometry.Rectangle;
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

public class Slider implements MouseEventListener, CollectionScanner<TouchArea> {

	private static final String PHOTO = "PHOTO";
	private static final String VIDEO = "VIDEO";
	private Layer root;
	private CustomInput input;
	private final DotLeft left = new DotLeft(this);
	private final DotRigh right = new DotRigh(this);
	private final DotLeft wormLeft = new DotLeft(this);
	private final DotRigh wormRight = new DotRigh(this);
	private final DotIndicator indicator = new DotIndicator(this, this.left, this.right);
	private final DotWorm worm = new DotWorm(this, this.indicator, this.wormLeft, this.wormRight);

	private final SliderAnimator animator = new SliderAnimator(this, this.indicator, this.worm);

	String state = PHOTO;

	private Collection<TouchArea> touchAreas;
	private final CollectionScanner<TouchArea> touchAreasAligner = this;

	private final UserInputBar master;
	private final FixedFloat2 originalSceneDimentions;
	private final CanvasPosition position = Geometry.newCanvasPosition();
	private final CanvasPosition baseOffset;
	private Rectangle screen;
	private Layer rasterlayer;

	public Slider (final UserInputBar userPanel) {
		this.master = userPanel;
		this.baseOffset = Geometry.newCanvasPosition();
		this.originalSceneDimentions = this.master.getOriginalSceneDimentions();

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
	}

	public void sendSliderToPhoto (final OnAnimationDoneListener animation_done_listener) {
		this.animator.sendSliderToPhoto(animation_done_listener);
		this.state = PHOTO;
	}

	public void setPhotoMode () {
		this.animator.sendSliderToPhotoFast();
		this.state = PHOTO;
	}

	public void setVideoMode () {
		this.animator.sendSliderToVideoFast();
		this.state = VIDEO;
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
		if (this.state == PHOTO) {
			TelecamUIAction.switchToVideoShoot.push();
		} else {
			TelecamUIAction.switchToPhotoShoot.push();
		}
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
