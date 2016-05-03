
package com.jfixby.telecam.ui.game.splash;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.floatn.Float2;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.cmns.api.java.IntValue;
import com.jfixby.cmns.api.math.FloatMath;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.cmns.api.util.JUtils;
import com.jfixby.cmns.api.util.StateSwitcher;
import com.jfixby.r3.api.ui.unit.CanvasPositionable;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.LayerBasedComponent;
import com.jfixby.r3.api.ui.unit.animation.Animation;
import com.jfixby.r3.api.ui.unit.animation.OnAnimationDoneListener;
import com.jfixby.r3.api.ui.unit.animation.PositionAnchor;
import com.jfixby.r3.api.ui.unit.animation.PositionsSequence;
import com.jfixby.r3.api.ui.unit.animation.PositionsSequenceSpecs;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.update.OnUpdateListener;
import com.jfixby.r3.api.ui.unit.update.UnitClocks;
import com.jfixby.r3.ext.api.scene2d.Scene;
import com.jfixby.r3.ext.api.scene2d.Scene2D;
import com.jfixby.r3.ext.api.scene2d.Scene2DSpawningConfig;

public class SplasherIcons implements LayerBasedComponent, CanvasPositionable {
	private static final int ANIMATION_DELTA = 200;
	private static final int MARGIN = 0;
	final private Layer root;
	final private ComponentsFactory components_factory;
	private final OnUpdateListener animator = new OnUpdateListener() {

		@Override
		public void onUpdate (final UnitClocks unit_clock) {

		}
	};
	private double MAX_SHIFT;
	private final Splasher master;

	public SplasherIcons (final ComponentsFactory components_factory, final Splasher splasher) {
		this.components_factory = components_factory;
		this.root = components_factory.newLayer();
		this.master = splasher;
		this.root.setName("SplasherIcons");
		final Scene2DSpawningConfig config = Scene2D.newSceneSpawningConfig();
		config.setDebugOpacity(0.5f);
		config.setStructureID(this.ssui_id);
		final Scene scene = Scene2D.spawnScene(components_factory, config);
		this.root.attachComponent(scene);
		this.root.closeInputValve();
		// scene.listRaster().print("rasters");
		this.camera = scene.getCamera();
		this.splashers.addAll(scene.listRaster());
		// splashers.print("splashers");
		// splashers.removeElementAt(3);// remove com.jfixby.telecam.scene-013.psd
		// splashers.print("splashers");
		// camera.setPositionX(-camera.getWidth() / 2);
		// camera.setPositionY(-camera.getWidth() / 2);
		// camera.setZoom(0.5d);
		this.camera.setSize(Splasher.width, Splasher.height);

		this.MAX_SHIFT = 0;
		final List<IntValue> toRemove = Collections.newList();
		int indexPosition = 0;
		for (int i = 0; i < this.splashers.size(); i++) {
			final Raster splash = this.splashers.getElementAt(i);
			final AssetID splasherID = splash.getAssetID();
			final AssetID sceneID = SceneWrapper.sceneID(i);
			// L.d("sceneID", splasherID + " -> " + sceneID);
			if (this.isIgnored(sceneID)) {
				final IntValue remve = new IntValue();
				remve.value = i;
				toRemove.add(remve);
				splash.hide();
				continue;
			}

			// splash.setOpacity(0.5);
			splash.setDebugRenderFlag(!true);
			final SceneWrapper i_splasher = new SceneWrapper(i, splash, this.master);

			this.master.registerSplasher(i_splasher);

			final double width = splash.getWidth();
			final double height = splash.getHeight();
			this.camera.setSize(width, height);
			// splash.setSize(width, height);
			// L.d(splash.getWidth());
			final double x = indexPosition * splash.getWidth();
			splash.setPositionX(x);
			// if (i != 0) {
			// splash.hide();
			// }
			this.MAX_SHIFT = FloatMath.max(this.MAX_SHIFT, x + MARGIN);
			indexPosition++;
		}

		for (int i = 0; i < toRemove.size(); i++) {
			final IntValue rem = toRemove.getElementAt(i);
			this.splashers.removeElementAt(rem.value);

		}
		// Sys.exit();
		this.current_splash_index = 0;

		this.root.setCamera(this.camera);
		// L.d("camera", camera);

		this.touch_state = JUtils.newStateSwitcher(TOUCH_STATE.FREE);
		this.touch_state.setThrowErrorOnUnexpectedState(false);

		this.root.attachComponent(this.animator);

		this.master.setStartSplasher(this.current_splash_index);
	}

	private boolean isIgnored (final AssetID sceneID) {
		if ("com.jfixby.telecam.scene-013.psd".equals(sceneID.toString().toLowerCase())) {
			return true;
		}

		return false;
	}

	private final StateSwitcher<TOUCH_STATE> touch_state;
	private final AssetID ssui_id = Names.newAssetID("com.jfixby.telecam.SplashScreenUI.psd");

	private final List<Raster> splashers = Collections.newList();
	private final Camera camera;

	@Override
	public Layer getRoot () {
		return this.root;
	}

	final Float2 begin_camera = Geometry.newFloat2();
	final Float2 end_camera = Geometry.newFloat2();
	final Float2 current_camera = Geometry.newFloat2();

	private PositionsSequence animation;
	private final OnAnimationDoneListener animation_done_listener = new OnAnimationDoneListener() {

		@Override
		public void onAnimationDone (final Animation animation) {
			SplasherIcons.this.try_to_lock_splash_screen();
		}

	};
	private long current_splash_index;

	public void captureCursor () {
		this.begin_camera.set(this.camera.getPosition());
		this.current_camera.set(this.begin_camera);
		this.touch_state.switchState(TOUCH_STATE.DRAG);
		this.terminateAnimation();
	}

	private void terminateAnimation () {
		if (this.animation == null) {
			return;
		}
		// animation.stopAnimation();
		this.root.detatchComponent(this.animation);
		// animation = null;
	}

	public void releaseCursor () {
		this.end_camera.set(this.current_camera);
		this.touch_state.switchState(TOUCH_STATE.NORMALIZING);
		this.setupNormalization();
	}

	private void setupNormalization () {
		final int camera_index_pos = (int)FloatMath.round(this.camera.getPositionX() / this.camera.getWidth());
		final double camera_target_x = camera_index_pos * this.camera.getWidth();

		final double camera_current_x = this.camera.getPositionX();

		//
		final PositionsSequenceSpecs specs = this.components_factory.getAnimationDepartment().newPositionsSequence();
		specs.setIsLooped(false);
		specs.setTimeStream(Sys.SystemTime());
		specs.setComponent(this);
		specs.setComponentRequiresAttachment(false);
		specs.setOnCompleteListener(this.animation_done_listener);
		{
			final PositionAnchor animation_anchor = this.components_factory.getAnimationDepartment().newAnchor(0);
			animation_anchor.setX(camera_current_x);
			specs.addAnchor(animation_anchor);

		}
		{
			final PositionAnchor animation_anchor = this.components_factory.getAnimationDepartment().newAnchor(ANIMATION_DELTA);
			animation_anchor.setX(camera_target_x);
			specs.addAnchor(animation_anchor);

		}
		// specs.listAnchors().print("anchors");
		this.animation = this.components_factory.getAnimationDepartment().newPositionsSequence(specs);
		this.animation.startAnimation();
		//
		this.root.attachComponent(this.animation);
	}

	private void try_to_lock_splash_screen () {
		final long index = FloatMath.round(this.current_camera.getX() / this.camera.getWidth());
		// L.d("lock index", index);
		final Raster next_splash = this.splashers.getElementAt(index);
		final Raster current_splash = this.splashers.getElementAt(this.current_splash_index);
		if (next_splash != current_splash) {
			this.master.activateNextSplasher(index);
			this.current_splash_index = index;
		}
	}

	public void updateCursor (final double relative_shift) {
		double camera_x = this.begin_camera.getX() + relative_shift * this.camera.getWidth();
		// L.d("camera_x", camera_x);
		camera_x = FloatMath.limit(0 - MARGIN, camera_x, this.MAX_SHIFT);
		// L.d(" ", camera_x);
		this.update_cam_position(camera_x);

	}

	private void update_cam_position (final double camera_x) {
		this.camera.setPositionX(camera_x);
		this.current_camera.set(this.camera.getPosition());

		final double offset = this.current_splash_index * this.camera.getWidth();
		final double raw_delta = this.current_camera.getX() - offset;
		final double relative_shift = raw_delta / this.camera.getWidth();
		this.master.shiftScene(relative_shift);
	}

	@Override
	public void setPosition (final CanvasPosition position) {
		this.update_cam_position(position.getX());
	}

	@Override
	public void setPositionXY (final double position_x, final double position_y) {
		this.update_cam_position(position_x);
	}

}
