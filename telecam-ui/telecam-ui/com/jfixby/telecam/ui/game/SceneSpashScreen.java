package com.jfixby.telecam.ui.game;

import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.LayerBasedComponent;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.camera.CameraSpecs;
import com.jfixby.r3.api.ui.unit.camera.SIMPLE_CAMERA_POLICY;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public class SceneSpashScreen implements LayerBasedComponent {

	final private Layer root;
	final private ComponentsFactory factory;
	final private Raster splashscreen_raster;
	private Camera camera;

	public SceneSpashScreen(Raster splashscreen_raster, boolean provoke_bug) {
		factory = splashscreen_raster.getComponentsFactory();
		this.root = factory.newLayer();
		CameraSpecs cam_specs = factory.getCameraDepartment().newCameraSpecs();
		cam_specs.setSimpleCameraPolicy(SIMPLE_CAMERA_POLICY.KEEP_ASPECT_RATIO_ON_SCREEN_RESIZE);
		camera = factory.getCameraDepartment().newCamera(cam_specs);
		root.setCamera(camera);
		int W = 2048;
		int H = 768 * 2;
		camera.setSize(W, H);
		camera.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		camera.setPosition(0, 0);
		root.attachComponent(splashscreen_raster);
		this.splashscreen_raster = splashscreen_raster;
		float m = 1.0f;
		splashscreen_raster.setSize(W * m, H * m);
		splashscreen_raster.setOrigin(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		splashscreen_raster.setPositionXY(0, 0);
	}

	public SceneSpashScreen(Raster splashscreen_raster) {
		factory = splashscreen_raster.getComponentsFactory();
		this.root = factory.newLayer();
		CameraSpecs cam_specs = factory.getCameraDepartment().newCameraSpecs();
		cam_specs.setSimpleCameraPolicy(SIMPLE_CAMERA_POLICY.KEEP_ASPECT_RATIO_ON_SCREEN_RESIZE);
		camera = factory.getCameraDepartment().newCamera(cam_specs);
		root.setCamera(camera);

		root.attachComponent(splashscreen_raster);
		this.splashscreen_raster = splashscreen_raster;
		float m = 0.910f;
		// double width = 1024 * 2;
		// double height = 768 * 2;

		double width = splashscreen_raster.getWidth();
		double height = splashscreen_raster.getHeight();

		// splashscreen_raster.setOrigin(ORIGIN_RELATIVE_HORIZONTAL.CENTER,
		// ORIGIN_RELATIVE_VERTICAL.CENTER);
		// splashscreen_raster.setPositionXY(100, 100);

		// int W = 2048;
		// int H = 768 * 2;
		camera.setSize(m * width, m * height);
		// camera.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER,
		// ORIGIN_RELATIVE_VERTICAL.CENTER);
		camera.setPosition(0, 0);
	}

	public void reset() {
	}

	@Override
	public Layer getRoot() {
		return root;
	}

}
