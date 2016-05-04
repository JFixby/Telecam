
package com.jfixby.telecam.ui.core;

import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public class UserPanel {

	UserPanel () {
		this.blueButton = new BlueButton(this);
		this.redButton = new RedButton(this);

	}

	private Layer root;
	private Raster gray;

	private final BlueButton blueButton;
	private final RedButton redButton;

	public void setup (final Layer root, final Camera camera) {
		this.root = root;
		this.gray = root.listChildren().findRaster("gray").getElementAt(0);

		final Layer blue_button_root = root.listChildren().findLayer("blue-button").getElementAt(0);
		this.blueButton.setup(blue_button_root);

		final Layer red_button_root = root.listChildren().findLayer("red-button").getElementAt(0);
		this.redButton.setup(red_button_root);

// L.d(this.blue_button_root);
	}

	public void updateScreen (final Rectangle viewport_update) {
		this.gray.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.LEFT, ORIGIN_RELATIVE_VERTICAL.BOTTOM);
		this.gray.setWidth(viewport_update.getWidth());
		this.gray.setPositionY(viewport_update.getHeight());
		this.gray.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.blueButton.update(this.gray.getPosition());
		this.redButton.update(this.gray.getPosition());
	}

}
