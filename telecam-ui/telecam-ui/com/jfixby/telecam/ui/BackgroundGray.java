
package com.jfixby.telecam.ui;

import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.cmns.api.geometry.RectangleCorner;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public class BackgroundGray {
	private Raster gray;
	private final UserInputBar master;
	private Layer root;

	public BackgroundGray (final UserInputBar userInputBar) {
		this.master = userInputBar;
	}

	public void setup (final Layer component_root) {
		this.root = component_root;
		this.gray = component_root.findComponent();
	}

	public void updateScreen (final Rectangle viewport_update) {
		this.gray.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.LEFT, ORIGIN_RELATIVE_VERTICAL.BOTTOM);
		this.gray.setWidth(viewport_update.getWidth());
		this.gray.setPositionY(viewport_update.getHeight());
		this.gray.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);

	}

	public CanvasPosition getPosition () {
		return this.gray.getPosition();
	}

	public void hide () {
		this.root.hide();
	}

	public RectangleCorner getTopLeftCorner () {
		return this.gray.getTopLeftCorner();
	}

	public double getWidth () {
		return this.gray.getWidth();
	}

	public void show () {
		this.root.show();
	}

}
