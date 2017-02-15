
package com.jfixby.telecam.ui.input.flash;

import com.jfixby.r3.api.ui.UI;
import com.jfixby.r3.api.ui.unit.animation.AnimationLifecycleListener;
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
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.telecam.ui.UserInputBar;
import com.jfixby.telecam.ui.actions.TelecamUIAction;

public class SwitchFlashButton extends MouseInputEventListener implements CollectionScanner<TouchArea> {

	private Layer root;
	private CustomInput input;
	private FlashIconWrapper flash_on;
	private FlashIconWrapper flash_off;
	private FlashIconWrapper flash_auto;

	private Collection<TouchArea> touchAreas;
	private final CollectionScanner<TouchArea> touchAreasAligner = this;
	private final CanvasPosition baseOffset;
	private final UserInputBar master;
	private final ReadOnlyFloat2 originalSceneDimentions;
	private Rectangle screen;

	SwitchFlashButtonAnimator animator = new SwitchFlashButtonAnimator(this);
	private TouchArea touch;

	public SwitchFlashButton (final UserInputBar userPanel) {
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

// options.print("options");

		this.flash_on = new FlashIconWrapper(options.getElementAt(0), this);
		this.flash_auto = new FlashIconWrapper(options.getElementAt(1), this);
		this.flash_off = new FlashIconWrapper(options.getElementAt(2), this);

		this.animator.add(this.flash_auto);
		this.animator.add(this.flash_on);
		this.animator.add(this.flash_off);

		this.touchAreas = this.input.listTouchAreas();
		this.touch = this.touchAreas.getLast();

		this.baseOffset.setX(this.originalSceneDimentions.getX() - this.input.getPosition().getX());
		this.baseOffset.setY(this.input.getPosition().getY());
		this.animator.setup(root);

	}

	public double getHeight () {
		return this.input.listTouchAreas().getLast().getHeight();
	}

	public void update (final Rectangle screen) {
		this.screen = screen;
		this.touch.shape().setPositionX(this.screen.getWidth() - this.baseOffset.getX());
		this.animator.updateX(this.screen.getWidth() - this.baseOffset.getX());

	}

	public double getBaseOffsetY () {
		return this.input.getPositionY();
	}

	private void animateIcons (final AnimationLifecycleListener animation_done_listener) {
		this.animator.animate(animation_done_listener);

	}

	@Override
	public void scanElement (final TouchArea element, final long index) {
		element.shape().setPositionX(this.screen.getWidth() - this.baseOffset.getX());
	}

	@Override
	public boolean onMouseMoved (final MouseMovedEvent input_event) {
		return false;
	}

	@Override
	public boolean onTouchDown (final TouchDownEvent input_event) {
		UI.pushAction(TelecamUIAction.switchFlashMode);
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

	public Layer getRoot () {
		return this.root;
	}

	public double getPositionY () {
		return this.input.getPositionY();
	}

	public void switchFlashMode (final AnimationLifecycleListener animation_done_listener) {
		this.animateIcons(animation_done_listener);
	}

}
