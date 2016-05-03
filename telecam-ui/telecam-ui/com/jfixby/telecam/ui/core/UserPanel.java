
package com.jfixby.telecam.ui.core;

import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.camera.ScreenDimentions;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public class UserPanel {

	private Layer root;
	private Raster gray;
	private double height;

	public void setup (final Layer root, final Camera camera) {
		this.root = root;
		this.gray = root.listChildren().findRaster("gray").getElementAt(0);
		this.gray.setOriginRelative(0, 1);
		this.height = this.gray.getHeight();
	}

	public void updateScreen (final ScreenDimentions viewport_update) {
		this.gray.setWidth(viewport_update.getScreenWidth());
		this.gray.setPositionY(viewport_update.getScreenHeight());
	}

}
