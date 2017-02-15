
package com.jfixby.telecam.ui;

import com.jfixby.r3.api.ui.AnimationsMachine;
import com.jfixby.r3.api.ui.InputManager;
import com.jfixby.r3.api.ui.UI;
import com.jfixby.r3.api.ui.UIAction;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.RootLayer;
import com.jfixby.r3.api.ui.unit.DefaultUnit;
import com.jfixby.r3.api.ui.unit.UnitManager;
import com.jfixby.r3.api.ui.unit.animation.AnimationLifecycleListener;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.camera.ScreenDimentions;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.user.ScreenChangeListener;
import com.jfixby.r3.ext.api.scene2d.Scene;
import com.jfixby.r3.ext.api.scene2d.Scene2D;
import com.jfixby.r3.ext.api.scene2d.Scene2DSpawningConfig;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.telecam.api.TelecamUI;
import com.jfixby.telecam.ui.actions.TelecamUIAction;

public class TelecamUnit extends DefaultUnit implements TelecamUI, InputManager, ScreenChangeListener {
	private UnitManager unitManager;
	private RootLayer root;
	private Scene scene;
	public static final ID scene_asset_id = Names.newID("com.jfixby.telecam.scene-base.psd");
	public static final long ANIMATION_DELTA = 150;
	final AnimationsMachine animations_machine = UI.newAnimationsMachine();
	private Layer userPanelLayer;
	private UserInputBar userPanel;
	private final ScreenChangeListener screenChangeListener = this;
	Rectangle screenDimentions = Geometry.newRectangle();
	ReadOnlyFloat2 sceneOriginalDimentions;

	private Camera camera;

	@Override
	public void onCreate (final UnitManager unitManager) {
		super.onCreate(unitManager);
		final ComponentsFactory components_factory = unitManager.getComponentsFactory();
		this.unitManager = unitManager;
		this.root = unitManager.getRootLayer();
		this.root.closeInputValve();
		this.root.setName("GameMainUI");

		this.root.attachComponent(this.screenChangeListener);

		final Scene2DSpawningConfig config = Scene2D.newSceneSpawningConfig();
		config.setStructureID(scene_asset_id);

		this.scene = Scene2D.spawnScene(components_factory, config);
		this.camera = this.scene.getCamera();
		this.root.attachComponent(this.scene);

		this.animations_machine.activate();

		this.sceneOriginalDimentions = this.scene.getOriginalDimentions();

		this.userPanel = new UserInputBar(this);
		this.userPanel.setup(this.scene.findLayer("user-panel").getElementAt(0), this.scene.getCamera());
		UI.pushAction(TelecamUIAction.goPhotoShoot);
		UI.pushAction(TelecamUIAction.goCropper);
		this.root.closeInputValve();
	}

	@Override
	public void enableInput () {
		this.root.openInputValve();
	}

	@Override
	public void onScreenChanged (final ScreenDimentions viewport_update) {
		L.d(viewport_update);
		final double screenWidth = viewport_update.getScreenWidth();
		final double screenHeight = viewport_update.getScreenHeight();
		final double w2h = viewport_update.getWidthToHeightRatio();
		final double targetWidth = 2048 * 3 / 4;
		final double scale = targetWidth / screenWidth;

		this.screenDimentions.setSize(screenWidth, screenHeight);
		// this.screenDimentions.reScale(scale, scale);
		this.camera.setSize(this.screenDimentions.getWidth(), this.screenDimentions.getHeight());
		this.userPanel.updateScreen(this.screenDimentions);
	}

	public ReadOnlyFloat2 getOriginalSceneDimentions () {
		return this.sceneOriginalDimentions;
	}

	public TelecamUnit getUnit () {
		return this;
	}

	public UnitManager getUnitManager () {
		return this.unitManager;
	}

	@Override
	public void goPhotoShoot () {
		this.userPanel.goPhoto();
	}

	public void goVideoShoot () {
		this.userPanel.goVideo();
	}

	public void bindAcceptDecline (final UIAction<TelecamUnit> noAction, final UIAction<TelecamUnit> yesAction) {
		this.userPanel.bindAcceptDecline(noAction, yesAction);
	}

	public void disableInput () {
		this.root.closeInputValve();
	}

	public void showGoCrop () {
		this.userPanel.showGoCrop();
	}

	public void goCropper () {
		this.userPanel.goCropper();
	}

	public void switchFlashMode (final AnimationLifecycleListener animation_done_listener) {
		this.userPanel.switchFlashMode(animation_done_listener);
	}

	public void switchCamera (final AnimationLifecycleListener animation_done_listener) {
		this.userPanel.switchCamera(animation_done_listener);
	}

	public void animateAcceptDecline (final AnimationLifecycleListener animation_done_listener) {
		this.userPanel.animateAcceptDecline(animation_done_listener);
	}

	public void sendSliderToVideo (final AnimationLifecycleListener animation_done_listener) {
		this.userPanel.sendSliderToVideo(animation_done_listener);
	}

	public void sendSliderToPhoto (final AnimationLifecycleListener animation_done_listener) {
		this.userPanel.sendSliderToPhoto(animation_done_listener);
	}

	public void goVideoRecording (final AnimationLifecycleListener animation_done_listener) {
		this.userPanel.goVideoRecording(animation_done_listener);
	}

	public void goVideoIdle (final AnimationLifecycleListener animation_done_listener) {
		this.userPanel.goVideoIdle(animation_done_listener);
	}

	public void showPlayStop () {
		this.userPanel.showPlayStop();
	}

	final VideoPlayer player = new VideoPlayer();

	public VideoPlayer getVideoPlayer () {
		return this.player;
	}

	public void uploadVideoToPlayer () {
		this.player.reset();
	}

	public void pressBlueButton () {
		this.userPanel.pressBlueButton();
	}

	public void setBlink (final float f) {
		this.userPanel.setBlink(f);
	}

	public void setBlinkBegin () {
		this.userPanel.setBlinkBegin();
	}

	public void setBlinkEnd () {
		this.userPanel.setBlinkEnd();
	}

	public void releaseBlueButton () {
		this.userPanel.releaseBlueButton();
	}

}
