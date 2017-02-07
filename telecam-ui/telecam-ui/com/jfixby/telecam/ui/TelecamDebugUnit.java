
package com.jfixby.telecam.ui;

import com.jfixby.r3.api.ui.AnimationsMachine;
import com.jfixby.r3.api.ui.InputManager;
import com.jfixby.r3.api.ui.UI;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.RootLayer;
import com.jfixby.r3.api.ui.unit.DefaultUnit;
import com.jfixby.r3.api.ui.unit.UnitManager;
import com.jfixby.r3.api.ui.unit.camera.ScreenDimentions;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.user.ScreenChangeListener;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.log.L;

public class TelecamDebugUnit extends DefaultUnit implements InputManager, ScreenChangeListener {
	private UnitManager unitManager;
	private RootLayer root_layer;
	public static final ID scene_asset_id = Names.newID("com.jfixby.telecam.scene-base.psd");
	final AnimationsMachine animations_machine = UI.newAnimationsMachine();
	private final ScreenChangeListener screenChangeListener = this;
	Rectangle screenDimentions = Geometry.newRectangle();
	ReadOnlyFloat2 sceneOriginalDimentions;

	@Override
	public void onCreate (final UnitManager unitManager) {
		super.onCreate(unitManager);
		final ComponentsFactory components_factory = unitManager.getComponentsFactory();
		this.unitManager = unitManager;
		this.root_layer = unitManager.getRootLayer();
		this.root_layer.closeInputValve();
		this.root_layer.setName("GameMainUI");

		this.root_layer.attachComponent(this.screenChangeListener);

		final ID asset_id = Names.newID("com.jfixby.r3.fokker.render.raster_is_missing");
		final Raster raster = components_factory.getRasterDepartment().newRaster(asset_id);
		this.root_layer.attachComponent(raster);
		raster.setSize(300, 300);
		this.animations_machine.activate();

	}

	@Override
	public void enableInput () {
		this.root_layer.openInputValve();
	}

	@Override
	public void onScreenChanged (final ScreenDimentions viewport_update) {
		L.d(viewport_update);

	}

	public ReadOnlyFloat2 getOriginalSceneDimentions () {
		return this.sceneOriginalDimentions;
	}

	public TelecamDebugUnit getUnit () {
		return this;
	}

	public UnitManager getUnitManager () {
		return this.unitManager;
	}

}
