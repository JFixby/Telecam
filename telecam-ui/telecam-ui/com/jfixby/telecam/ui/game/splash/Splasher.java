package com.jfixby.telecam.ui.game.splash;

import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.LayerBasedComponent;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.telecam.ui.game.SceneContainer;

public class Splasher implements LayerBasedComponent {

	final private ComponentsFactory components_factory;
	final private Layer root;
	final private SplasherIcons icons;
	final private SplasherInput input;
	private SceneContainer scene_container;

	public static final double width = 1024 * 2;
	public static final double height = width * 3 / 4;
	private static final double M = 2;

	public Splasher(ComponentsFactory factory, SceneContainer scene_container) {
		this.components_factory = factory;
		this.root = components_factory.newLayer();
		root.setName("Splasher");
		this.scene_container = scene_container;

		this.icons = new SplasherIcons(components_factory, this);
		this.root.attachComponent(icons);
		this.input = new SplasherInput(components_factory, this);
		this.root.attachComponent(input);

	}

	@Override
	public Layer getRoot() {
		return root;
	}

	public void captureCursor() {
		this.icons.captureCursor();

	}

	public void releaseCursor() {
		this.icons.releaseCursor();

	}

	public void updateCursor(double relative_shift) {
		this.icons.updateCursor(relative_shift * M);

	}

	public void activateNextSplasher(long index) {
		scene_container.pushNextSplasher(index);
	}

	public void registerSplasher(SceneWrapper i_splasher) {
		scene_container.registerSplasher(i_splasher);
	}

	public void setStartSplasher(long current_splash_index) {
		scene_container.setStartSplasher(current_splash_index);
	}

	public void onIconSplasherActivated(SceneWrapper iconSplasher) {
		scene_container.onIconSplasherActivated(iconSplasher);
	}

	public void onIconSplasherDeactivated(SceneWrapper iconSplasher) {
		scene_container.onIconSplasherDeactivated(iconSplasher);
	}

	public void shiftScene(double relative_shift) {
		scene_container.updateCursor(relative_shift);
	}
}
