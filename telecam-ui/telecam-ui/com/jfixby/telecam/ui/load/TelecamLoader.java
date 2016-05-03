
package com.jfixby.telecam.ui.load;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.log.L;
import com.jfixby.r3.api.game.ProgressListener;
import com.jfixby.r3.api.game.ShadowStateListener;
import com.jfixby.r3.api.game.TaskProgress;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.RootLayer;
import com.jfixby.r3.api.ui.unit.Unit;
import com.jfixby.r3.api.ui.unit.UnitManager;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.layer.Shadow;
import com.jfixby.r3.api.ui.unit.layer.ShadowSpecs;
import com.jfixby.r3.ext.api.scene2d.Scene;
import com.jfixby.r3.ext.api.scene2d.Scene2D;
import com.jfixby.r3.ext.api.scene2d.Scene2DSpawningConfig;

public class TelecamLoader extends Unit implements ProgressListener, ShadowStateListener {

	public static final AssetID Scene2D_ID = Names.newAssetID("com.jfixby.telecam.scene-001.psd");
	private RootLayer root_layer;
	private Camera camera;
	private Shadow shadow;

	@Override
	public void onCreate (final UnitManager unitManager) {
		super.onCreate(unitManager);
		final ComponentsFactory components_factory = unitManager.getComponentsFactory();
		this.root_layer = unitManager.getRootLayer();

		final ShadowSpecs shadow_specs = components_factory.getCameraDepartment().newShadowSpecs();
		this.shadow = components_factory.getCameraDepartment().newShadow(shadow_specs);
		this.root_layer.closeInputValve();
		final Scene2DSpawningConfig config = Scene2D.newSceneSpawningConfig();
		config.setStructureID(Scene2D_ID);

		final Scene scene = Scene2D.spawnScene(components_factory, config);
		this.root_layer.attachComponent(scene);
		this.root_layer.attachComponent(this.shadow);
		this.camera = scene.getCamera();
		this.shadow.setValue(Shadow.ABSOLUTE_CLEAR);

	}

	@Override
	public void onLoaderBegin () {
	}

	@Override
	public void onUpdateProgress (final TaskProgress task_progress) {
		L.d("progress", task_progress);
	}

	@Override
	public void onLoaderEnd () {
	}

	@Override
	public boolean isDoneListening () {
		return true;
	}

	@Override
	public void beginShadowing (final float value_begin, final float value_end) {
		this.shadow.setValue(value_begin);
	}

	@Override
	public void updateShadow (final float value_current) {
		this.shadow.setValue(value_current);
	}

}
