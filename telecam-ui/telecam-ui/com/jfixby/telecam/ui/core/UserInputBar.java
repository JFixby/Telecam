
package com.jfixby.telecam.ui.core;

import com.jfixby.cmns.api.floatn.FixedFloat2;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.telecam.ui.core.input.accdecc.AcceptDecline;
import com.jfixby.telecam.ui.core.input.blue.BlueButton;
import com.jfixby.telecam.ui.core.input.crop.GoCropButton;
import com.jfixby.telecam.ui.core.input.flash.SwitchFlashButton;
import com.jfixby.telecam.ui.core.input.red.RedButton;
import com.jfixby.telecam.ui.core.input.slider.Slider;
import com.jfixby.telecam.ui.core.input.swcam.SwitchCameraButton;
import com.jfixby.telecam.ui.core.input.vplay.VidepPlayPause;

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

	}

	private Layer root;

	public void setup (final Layer root, final Camera camera) {
		this.root = root;

		{
			final Layer component_root = root.listChildren().findLayer("bg-gray").getElementAt(0);
			this.bgGray.setup(component_root);
		}
		{
			final Layer gray_root = root.listChildren().findLayer("cropper").getElementAt(0);
			this.cropper.setup(gray_root);
		}
		{
			final Layer button_root = root.listChildren().findLayer("blue-button").getElementAt(0);
			this.blueButton.setup(button_root);
		}
		{
			final Layer button_root = root.listChildren().findLayer("red-button").getElementAt(0);
			this.redButton.setup(button_root);
		}
		{
			final Layer button_root = root.listChildren().findLayer("switch-cam").getElementAt(0);
			this.switchCameraButton.setup(button_root);
		}
		{
			final Layer button_root = root.listChildren().findLayer("go-crop").getElementAt(0);
			this.cropButton.setup(button_root);
		}
		{
			final Layer button_root = root.listChildren().findLayer("vide-pause-resume").getElementAt(0);
			this.videpPlayResume.setup(button_root);
		}
		{
			final Layer button_root = root.listChildren().findLayer("accept-decline").getElementAt(0);
			this.acceptDecline.setup(button_root);
		}
		{
			final Layer button_root = root.listChildren().findLayer("flash-button").getElementAt(0);
			this.switchFlashButton.setup(button_root);
		}
		{
			final Layer button_root = root.listChildren().findLayer("slider").getElementAt(0);
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
		this.bgGray.hide();
	}

	public void updateScreen (final Rectangle viewport_update) {

		this.bgGray.updateScreen(viewport_update);
		this.cropper.updateScreen(viewport_update);

		this.blueButton.update(this.bgGray.getPosition());
		this.redButton.update(this.bgGray.getPosition());
		this.switchCameraButton.update(this.bgGray.getPosition());
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
