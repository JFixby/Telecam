
package com.jfixby.telecam.ui;

import com.jfixby.r3.api.ui.UIAction;
import com.jfixby.r3.api.ui.unit.animation.AnimationLifecycleListener;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.Rectangle;
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
	private final ProgressBar progressBar;
	private final VideoTimer videTimer;
	private final GoVideoRecordingAnimator goVideoRecordingAnimator = new GoVideoRecordingAnimator(this);

	UserInputBar (final TelecamUnit telecamUnit) {
		this.master = telecamUnit;
		this.blueButton = new BlueButton(this);
		this.redButton = new RedButton(this);

		this.cropButton = new GoCropButton(this);
		this.videpPlayResume = new VidepPlayPause(this);
		this.acceptDecline = new AcceptDecline(this);
		this.switchFlashButton = new SwitchFlashButton(this);

		this.bgGray = new BackgroundGray(this);
		this.cropper = new Cropper(this);
		this.screenTouch = new ScreenTouch(this);
		this.progressBar = new ProgressBar(this);
		this.videTimer = new VideoTimer(this);
		this.switchCameraButton = new SwitchCameraButton(this, this.bgGray, this.blueButton, this.redButton);
		this.slider = new Slider(this, this.bgGray, this.blueButton, this.redButton, this.switchCameraButton,
			this.switchFlashButton);
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
		{
			final Layer button_root = root.findComponent("progress-bar");
			this.progressBar.setup(button_root);
		}
		{
			final Layer button_root = root.findComponent("video-timer");
			this.videTimer.setup(button_root);
		}

		this.goVideoRecordingAnimator.setup(root);

		this.hideAll();

	}

	public void hideAll () {
		this.redButton.hide();
		this.blueButton.hide();
		this.cropButton.hide();
		this.progressBar.hide();
		this.videpPlayResume.hide();
		this.acceptDecline.hide();
		this.switchCameraButton.hide();
		this.screenTouch.hide();
		this.videTimer.hide();
		this.bgGray.hide();
		this.cropper.hide();
		this.slider.hide();
		this.switchFlashButton.hide();
	}

	public void updateScreen (final Rectangle viewport_update) {

		this.bgGray.updateScreen(viewport_update);
		this.cropper.updateScreen(viewport_update);

		this.blueButton.update(this.bgGray.getCenter());
		this.redButton.update(this.bgGray.getCenter());
		this.switchCameraButton.update(this.bgGray.getCenter());
		this.screenTouch.update(viewport_update);
		this.cropButton.update(this.bgGray.getCenter());
		this.progressBar.update(this.bgGray);
		this.videTimer.update(this.bgGray.getCenter());
		this.videpPlayResume.update(this.bgGray.getCenter());
		this.acceptDecline.update(this.bgGray.getCenter(), viewport_update);
		this.switchFlashButton.update(viewport_update);
		this.slider.update(this.bgGray.getCenter(), viewport_update);

	}

	public ReadOnlyFloat2 getOriginalSceneDimentions () {
		return this.master.getOriginalSceneDimentions();
	}

	public TelecamUnit getUnit () {
		return this.master.getUnit();
	}

	final FontSettings fontSettings = new FontSettings();

	public FontSettings getFontSettings () {
		return this.fontSettings;
	}

	public void goPhoto () {
		this.hideAll();
//
		this.blueButton.show();
//// this.redButton.show();
		this.switchCameraButton.show();
		this.screenTouch.show();
		this.bgGray.show();
		this.slider.show();
		this.switchFlashButton.show();
// this.slider.sendSliderToPhoto(null);

	}

	public void goVideo () {
		this.hideAll();
		//
// this.blueButton.show();
		this.redButton.show();
		this.switchCameraButton.show();
		this.screenTouch.show();
		this.bgGray.show();
		this.slider.show();
// this.switchFlashButton.show();
	}

	public void bindAcceptDecline (final UIAction<TelecamUnit> noAction, final UIAction<TelecamUnit> yesAction) {

		this.acceptDecline.bindYesAction(yesAction);
		this.acceptDecline.bindNoAction(noAction);
	}

	public void showGoCrop () {
		this.cropButton.show();
	}

	public void goCropper () {
		this.hideAll();

		this.cropper.show();
		this.cropper.resetCroppingArea();
	}

	public void switchFlashMode (final AnimationLifecycleListener animation_done_listener) {
		this.switchFlashButton.switchFlashMode(animation_done_listener);
	}

	public void switchCamera (final AnimationLifecycleListener animation_done_listener) {
		this.switchCameraButton.switchCamera(animation_done_listener);
	}

	public void animateAcceptDecline (final AnimationLifecycleListener animation_done_listener) {
		this.hideAll();

		this.screenTouch.show();
		this.bgGray.show();
		this.acceptDecline.show();
		this.acceptDecline.animate(animation_done_listener);
	}

	public void sendSliderToVideo (final AnimationLifecycleListener animation_done_listener) {
// this.goPhoto();
		this.blueButton.hide();
		this.redButton.show();
		this.slider.sendSliderToVideo(animation_done_listener);
	}

	public void sendSliderToPhoto (final AnimationLifecycleListener animation_done_listener) {
		this.slider.sendSliderToPhoto(animation_done_listener);
		this.blueButton.show();
		this.redButton.hide();
// this.goVideo();
	}

	public Layer getRoot () {
		return this.root;
	}

	public BackgroundGray getBackgroundGray () {
		return this.bgGray;
	}

	public RedButton getRedButton () {
		return this.redButton;
	}

	public void goVideoRecording (final AnimationLifecycleListener animation_done_listener) {
		this.goVideoRecordingAnimator.goVideoRecording(animation_done_listener);
		this.switchCameraButton.hide();
		this.slider.hide();
// this.redButton.setMode();
	}

	public void goVideoIdle (final AnimationLifecycleListener animation_done_listener) {
		this.slider.show();
		this.switchCameraButton.show();
		this.goVideoRecordingAnimator.goVideoIdle(animation_done_listener);
	}

	public void showPlayStop () {
		this.master.getVideoPlayer().setProgressListener(this.progressBar);
		this.master.getVideoPlayer().stop();
		this.videpPlayResume.show();
		this.progressBar.show();
	}

	public ProgressBar getProgress () {
		return this.progressBar;
	}

	public VideoPlayer getVideoPlayer () {
		return this.master.getVideoPlayer();
	}

	public VidepPlayPause getVidepPlayPause () {
		return this.videpPlayResume;
	}

	public void pressBlueButton () {
		this.blueButton.press();
	}

	public void releaseBlueButton () {
		this.blueButton.release();
	}

	public void setBlinkBegin () {
		this.bgGray.setBlinkBegin();

	}

	public void setBlink (final float f) {
		this.bgGray.setShootProgress(f);
		this.blueButton.setShootProgress(f);
	}

	public void setBlinkEnd () {
		this.bgGray.setShootProgressDone();

	}
}
