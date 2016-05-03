
package com.jfixby.telecam.ui.game;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.LayerBasedComponent;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.camera.CameraSpecs;
import com.jfixby.r3.api.ui.unit.camera.SIMPLE_CAMERA_POLICY;
import com.jfixby.r3.api.ui.unit.input.InputComponent;
import com.jfixby.r3.api.ui.unit.input.MouseMovedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDownEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDraggedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchUpEvent;
import com.jfixby.r3.api.ui.unit.input.U_OnMouseInputEventListener;
import com.jfixby.r3.api.ui.unit.layer.ComponentsList;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.ext.api.scene2d.Scene;
import com.jfixby.r3.ext.api.scene2d.Scene2D;
import com.jfixby.r3.ext.api.scene2d.Scene2DSpawningConfig;
import com.jfixby.telecam.ui.game.splash.Splasher;

public class InputBar implements LayerBasedComponent {

	private final ComponentsFactory components_factory;
	private final Layer root;
	private final BarIcons icons;
	private final BarInput input;
	private final Camera camera;
	public static final AssetID InputBar_SceneID = Names.newAssetID("com.jfixby.telecam.InputBar.psd");
	private final Scene scene;
	private final InputComponent button_lang;

	private void toggleLangbar () {
		if (this.lang_bar.isVisible()) {
			this.lang_bar.hide();
			this.gameMainUI.enableScrollerInput();
		} else {
			this.lang_bar.show();
			this.gameMainUI.disableScrollerInput();
		}
	}

	private final Layer lang_bar;
	private final GameMainUI gameMainUI;
	private final InputComponent button_lang_exit;
	private final LanguageManager language_manager;

	public InputBar (final ComponentsFactory components_factory, final GameMainUI gameMainUI) {
		this.components_factory = components_factory;
		this.root = components_factory.newLayer();

		this.icons = new BarIcons(components_factory, this);
		this.root.attachComponent(this.icons);

		this.input = new BarInput(components_factory, this);
		this.root.attachComponent(this.input);

		this.gameMainUI = gameMainUI;

		final CameraSpecs spec = components_factory.getCameraDepartment().newCameraSpecs();
		spec.setSimpleCameraPolicy(SIMPLE_CAMERA_POLICY.KEEP_ASPECT_RATIO_ON_SCREEN_RESIZE);
		this.camera = components_factory.getCameraDepartment().newCamera(spec);
		this.camera.setOriginRelative(0, 0);
		this.camera.setPosition(0, 0);
		this.camera.setSize(Splasher.width, Splasher.height);

		this.root.setCamera(this.camera);

		final Scene2DSpawningConfig config = Scene2D.newSceneSpawningConfig();
		config.setStructureID(this.InputBar_SceneID);

		this.scene = Scene2D.spawnScene(components_factory, config);

		this.button_lang = this.scene.listInputComponents().get(this.InputBar_SceneID.child("button_lang"));
		this.button_lang.setInputListener(this.lang_switch);

		this.language_manager = new LanguageManager(this.scene.listInputComponents(), this);

		this.button_lang_exit = this.scene.listInputComponents().get(this.InputBar_SceneID.child("button_lang_exit"));
		this.button_lang_exit.setInputListener(this.lang_switch);

		this.root.attachComponent(this.scene);

		final Layer scene_root = this.scene.getRoot();
		final ComponentsList<Layer> list = scene_root.listChildLayers();
		// list.print("list children");
		this.lang_bar = list.findElementByName("lang bar");
		this.lang_bar.hide();

	}

	@Override
	public Layer getRoot () {
		return this.root;
	}

	private final U_OnMouseInputEventListener lang_switch = new U_OnMouseInputEventListener() {

		@Override
		public boolean onMouseMoved (final MouseMovedEvent input_event) {
			return true;
		}

		@Override
		public boolean onTouchDown (final TouchDownEvent input_event) {
			InputBar.this.toggleLangbar();
			return true;
		}

		@Override
		public boolean onTouchUp (final TouchUpEvent input_event) {
			return true;
		}

		@Override
		public boolean onTouchDragged (final TouchDraggedEvent input_event) {
			return true;
		}
	};

	public GameMainUI getGameMainUI () {
		return this.gameMainUI;
	}

}
