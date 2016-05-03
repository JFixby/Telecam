
package com.jfixby.telecam.ui.game;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.r3.api.game.ShadowStateListener;
import com.jfixby.r3.api.ui.AnimationsMachine;
import com.jfixby.r3.api.ui.GameUI;
import com.jfixby.r3.api.ui.InputManager;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.RootLayer;
import com.jfixby.r3.api.ui.unit.Unit;
import com.jfixby.r3.api.ui.unit.UnitManager;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.layer.Shadow;
import com.jfixby.r3.api.ui.unit.layer.ShadowSpecs;
import com.jfixby.telecam.ui.game.splash.Splasher;

public class GameMainUI extends Unit implements ShadowStateListener, InputManager {

	public static final AssetID Scene2D_ID = Names.newAssetID("com.jfixby.telecam.ui.psd");

	private RootLayer root_layer;
	private Camera camera;
	private Shadow shadow;

	private Splasher splasher;
	private UnitManager unitManager;
	private SceneContainer scene_container;
	private InputBar input_bar;

	@Override
	public void onCreate (final UnitManager unitManager) {
		super.onCreate(unitManager);
		final ComponentsFactory components_factory = unitManager.getComponentsFactory();
		this.unitManager = unitManager;
		this.root_layer = unitManager.getRootLayer();
		this.root_layer.closeInputValve();
		this.root_layer.setName("GameMainUI");
		{
			this.scene_container = new SceneContainer(components_factory);
			this.root_layer.attachComponent(this.scene_container);
		}
		{
			this.splasher = new Splasher(components_factory, this.scene_container);
			this.root_layer.attachComponent(this.splasher);
		}
		{
			this.input_bar = new InputBar(components_factory, this);
			this.root_layer.attachComponent(this.input_bar);
		}
		{
			final ShadowSpecs shadow_specs = components_factory.getCameraDepartment().newShadowSpecs();
			this.shadow = components_factory.getCameraDepartment().newShadow(shadow_specs);
			this.shadow.setValue(Shadow.ABSOLUTE_DARKNESS);
			this.root_layer.attachComponent(this.shadow);
		}
		this.animations_machine.activate();
	}

	@Override
	public void beginShadowing (final float value_begin, final float value_end) {
		this.shadow.setValue(value_begin);
	}

	@Override
	public void updateShadow (final float value_current) {
		this.shadow.setValue(value_current);
	}

	@Override
	public void enableInput () {
		this.root_layer.openInputValve();
	}

	final AnimationsMachine animations_machine = GameUI.newAnimationsMachine();

	@Override
	public void onStart () {
		super.onStart();
		this.animations_machine.resume();

	}

	@Override
	public void onResume () {
		super.onResume();
		this.animations_machine.resume();
	}

	@Override
	public void onPause () {
		super.onPause();
		this.animations_machine.pause();
	}

	public void disableInput () {
		this.root_layer.closeInputValve();
	}

	public void enableScrollerInput () {
		this.splasher.getRoot().openInputValve();
	}

	public void disableScrollerInput () {
		this.splasher.getRoot().closeInputValve();
	}

	public SceneContainer getSceneContainer () {
		return this.scene_container;
	}

}
