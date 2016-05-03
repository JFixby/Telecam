package com.jfixby.r3.test.shader;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.RootLayer;
import com.jfixby.r3.api.ui.unit.Unit;
import com.jfixby.r3.api.ui.unit.UnitManager;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.ext.api.scene2d.Scene;
import com.jfixby.r3.ext.api.scene2d.Scene2D;
import com.jfixby.r3.ext.api.scene2d.Scene2DSpawningConfig;

public class ShaderTest extends Unit {

	private RootLayer root;
	private ComponentsFactory components_factory;
	private Camera camera;

	@Override
	public void onCreate(UnitManager unitManager) {
		super.onCreate(unitManager);

		root = unitManager.getRootLayer();
		components_factory = unitManager.getComponentsFactory();

		AssetID scene_id = Names.newAssetID("com.jfixby.telecam.scene-010.psd");
		// AssetID scene_id =
		// Names.newAssetID("com.jfixby.telecam.shader.test.psd");

		Scene2DSpawningConfig config = Scene2D.newSceneSpawningConfig();
		config.setStructureID(scene_id);

		Scene scene = Scene2D.spawnScene(components_factory, config);

		root.attachComponent(scene);
		Collection<Raster> raster = scene.listRaster();
		raster.print("raster");

		Raster raster_i = raster.getElementAt(1);
//		raster_i.setBlendMode(BLEND_MODE.Grayscale);

		// Collection<Shader> shaders = scene.listShaders();
		// shaders.print("shaders");
		// Shader shader = shaders.getLast();
		// Mapping<String, ShaderParameter> parameters_list =
		// shader.listParameters();
		// parameters_list.print("params");
		// ShaderParameter param = parameters_list.getValueAt(0);
		// shader.setFloatParameterValue(param.getName(), 0f);

	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
