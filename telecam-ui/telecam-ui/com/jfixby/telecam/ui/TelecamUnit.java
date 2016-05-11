
package com.jfixby.telecam.ui;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.floatn.FixedFloat2;
import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.cmns.api.log.L;
import com.jfixby.r3.api.ui.AnimationsMachine;
import com.jfixby.r3.api.ui.InputManager;
import com.jfixby.r3.api.ui.UI;
import com.jfixby.r3.api.ui.UIAction;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.RootLayer;
import com.jfixby.r3.api.ui.unit.Unit;
import com.jfixby.r3.api.ui.unit.UnitManager;
import com.jfixby.r3.api.ui.unit.animation.OnAnimationDoneListener;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.camera.ScreenChangeListener;
import com.jfixby.r3.api.ui.unit.camera.ScreenDimentions;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.ext.api.scene2d.Scene;
import com.jfixby.r3.ext.api.scene2d.Scene2D;
import com.jfixby.r3.ext.api.scene2d.Scene2DSpawningConfig;
import com.jfixby.telecam.api.TelecamUI;
import com.jfixby.telecam.ui.actions.TelecamUIAction;

public class TelecamUnit extends Unit implements TelecamUI, InputManager, ScreenChangeListener {
	private UnitManager unitManager;
	private RootLayer root;
	private Scene scene;
	public static final AssetID scene_asset_id = Names.newAssetID("com.jfixby.telecam.scene-base.psd");
	public static final long ANIMATION_DELTA = 150;
	final AnimationsMachine animations_machine = UI.newAnimationsMachine();
	private Layer userPanelLayer;
	private UserInputBar userPanel;
	private final ScreenChangeListener screenChangeListener = this;
	Rectangle screenDimentions = Geometry.newRectangle();
	FixedFloat2 sceneOriginalDimentions;

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

	public FixedFloat2 getOriginalSceneDimentions () {
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

	public void setShootProgress (final float f) {
		this.userPanel.setShootProgress(f);
	}

	public void setShootProgressBegin () {
		this.userPanel.setShootProgressBegin();
	}

	public void setShootProgressDone () {
		this.userPanel.setShootProgressDone();
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

	public void switchFlashMode (final OnAnimationDoneListener animation_done_listener) {
		this.userPanel.switchFlashMode(animation_done_listener);
	}

	public void switchCamera (final OnAnimationDoneListener animation_done_listener) {
		this.userPanel.switchCamera(animation_done_listener);
	}

	public void animateAcceptDecline (final OnAnimationDoneListener animation_done_listener) {
		this.userPanel.animateAcceptDecline(animation_done_listener);
	}

	public void sendSliderToVideo (final OnAnimationDoneListener animation_done_listener) {
		this.userPanel.sendSliderToVideo(animation_done_listener);
	}

	public void sendSliderToPhoto (final OnAnimationDoneListener animation_done_listener) {
		this.userPanel.sendSliderToPhoto(animation_done_listener);
	}

}
