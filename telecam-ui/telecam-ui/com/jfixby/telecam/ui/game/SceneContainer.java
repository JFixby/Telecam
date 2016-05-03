package com.jfixby.telecam.ui.game;

import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.LayerBasedComponent;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.ext.api.scene2d.Scene;
import com.jfixby.telecam.ui.game.splash.SceneWrapper;

public class SceneContainer implements LayerBasedComponent {
	final private ComponentsFactory components_factory;
	final private Layer root;
	private Camera camera;

	public SceneContainer(ComponentsFactory components_factory) {
		this.components_factory = components_factory;
		this.root = components_factory.newLayer();
	}

	@Override
	public Layer getRoot() {
		return root;
	}

	public void onIconSplasherActivated(SceneWrapper iconSplasher) {
		Scene next_scene = iconSplasher.getScene();
		camera = next_scene.getCamera();
		root.attachComponent(next_scene);
		updateCursor(0);
//		updateCursor(0);
		// Debug.printCallStack();

	}

	public void onIconSplasherDeactivated(SceneWrapper iconSplasher) {
		camera = null;
		root.detatchAllComponents();
		// Debug.printCallStack();
	}

	final List<SceneWrapper> splashers = Collections.newList();

	public void registerSplasher(SceneWrapper i_splasher) {
		splashers.add(i_splasher);
	}

	public void pushNextSplasher(long index) {
		SceneWrapper previous = current;
		current = splashers.getElementAt(index);
		current.load(previous);
		// Debug.printCallStack();

	}

	public void setStartSplasher(long current_splash_index) {
		current = splashers.getElementAt(current_splash_index);
		current.load(null);

	}

	SceneWrapper current = null;

	public void captureCursor() {
	}

	public void releaseCursor() {
	}

	public void updateCursor(double relative_shift) {
		// L.d("relative_shift", relative_shift);
		double camera_x = camera.getWidth() * relative_shift;

		Debug.printCallStack(camera == null);
		Debug.exit(camera == null);

		camera.setPositionX(camera_x);

	}

	public SceneWrapper getCurrentSceneWrapper() {
		return current;
	}
}
