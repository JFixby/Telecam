
package com.jfixby.telecam.ui;

import com.jfixby.cmns.api.floatn.FixedFloat2;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.telecam.ui.input.accept.AcceptDecline;
import com.jfixby.telecam.ui.input.blue.BlueButton;
import com.jfixby.telecam.ui.input.crop.GoCropButton;
import com.jfixby.telecam.ui.input.cropper.Cropper;
import com.jfixby.telecam.ui.input.flash.SwitchFlashButton;
import com.jfixby.telecam.ui.input.red.RedButton;
import com.jfixby.telecam.ui.input.slider.Slider;
import com.jfixby.telecam.ui.input.swcam.SwitchCameraButton;
import com.jfixby.telecam.ui.input.vplay.VidepPlayPause;

public class UserInputBar {
	private final BlueButton blueButton;
	private final RedButton redButton;
	private final SwitchCameraButton switchCameraButton;
	private final SwitchFlashButton switchFlashButton;
	private final TelecamUnit master;
	private final Slider slider;
	private final AcceptDecline acceptDecline;
	private final GoCropButton cropButton;
	private final VidepPlayPause videpPlayResume;
	private final BackgroundGray bgGray;
	private final Cropper cropper;
	private final ScreenTouch screenTouch;

	UserInputBar (final TelecamUnit telecamUnit) {
		this.master = telecamUnit;
		this.blueButton = new BlueButton(this);
		this.redButton = new RedButton(this);
		this.switchCameraButton = new SwitchCameraButton(this);
		this.cropButton = new GoCropButton(this);
		this.videpPlayResume = new VidepPlayPause(this);
		this.acceptDecline = new AcceptDecline(this);
		this.switchFlashButton = new SwitchFlashButton(this);
		this.slider = new Slider(this);
		this.bgGray = new BackgroundGray(this);
		this.cropper = new Cropper(this);
		this.screenTouch = new ScreenTouch(this);

	}

	private Layer root;

	public void setup (final Layer root, final Camera camera) {
		this.root = root;

		{
			final Layer component_root = root.findComponent("bg-gray");
			this.bgGray.setup(component_root);
		}
		{
			final Layer gray_root = root.findComponent("cropper");
			this.cropper.setup(gray_root);
		}
		{
			final Layer button_root = root.findComponent("blue-button");
			this.blueButton.setup(button_root);
		}
		{
			final Layer button_root = root.findComponent("red-button");
			this.redButton.setup(button_root);
		}
		{
			final Layer button_root = root.findComponent("switch-cam");
			this.switchCameraButton.setup(button_root);
		}
		{
			final Layer button_root = root.findComponent("focus-screen");
			this.screenTouch.setup(button_root);
		}

		{
			final Layer button_root = root.findComponent("go-crop");
			this.cropButton.setup(button_root);
		}
		{
			final Layer button_root = root.findComponent("vide-pause-resume");
			this.videpPlayResume.setup(button_root);
		}
		{
			final Layer button_root = root.findComponent("accept-decline");
			this.acceptDecline.setup(button_root);
		}
		{
			final Layer button_root = root.findComponent("flash-button");
			this.switchFlashButton.setup(button_root);
		}
		{
			final Layer button_root = root.findComponent("slider");
			this.slider.setup(button_root);
		}

		this.hideAll();

	}

	public void hideAll () {
		this.redButton.hide();
		this.blueButton.hide();
		this.cropButton.hide();
		this.videpPlayResume.hide();
		this.acceptDecline.hide();
		this.switchCameraButton.hide();
		this.screenTouch.hide();
// this.bgGray.hide();
// this.cropper.hide();
	}

	public void updateScreen (final Rectangle viewport_update) {

		this.bgGray.updateScreen(viewport_update);
		this.cropper.updateScreen(viewport_update);

		this.blueButton.update(this.bgGray.getPosition());
		this.redButton.update(this.bgGray.getPosition());
		this.switchCameraButton.update(this.bgGray.getPosition());
		this.screenTouch.update(viewport_update);
		this.cropButton.update(this.bgGray.getPosition());
		this.videpPlayResume.update(this.bgGray.getPosition());
		this.acceptDecline.update(this.bgGray.getPosition(), viewport_update);
		this.switchFlashButton.update(viewport_update);
		this.slider.update(this.bgGray.getPosition(), viewport_update);

	}

	public FixedFloat2 getOriginalSceneDimentions () {
		return this.master.getOriginalSceneDimentions();
	}

	public TelecamUnit getUnit () {
		return this.master.getUnit();
	}

}
