
package com.jfixby.telecam.ui.input.cropper;

import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public class CropperBackground {

	private final Cropper master;
	private Layer root;
	private Raster black;
	private Raster gray;

	public CropperBackground (final Cropper cropper) {
		this.master = cropper;
	}

	public void setup (final Layer root) {
		this.root = root;

		this.black = root.findComponent("black");
		this.gray = root.findComponent("gray");

	}

	public void updateScreen (final Rectangle viewport_update) {

		this.gray.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.LEFT, ORIGIN_RELATIVE_VERTICAL.BOTTOM);
		this.gray.setWidth(viewport_update.getWidth());
		this.gray.setPositionY(viewport_update.getHeight());
		this.gray.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);

		this.black.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.LEFT, ORIGIN_RELATIVE_VERTICAL.BOTTOM);
		this.black.setWidth(viewport_update.getWidth());
		this.black.setPositionY(viewport_update.getHeight());
		this.black.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);

	}

	public CanvasPosition getBlackPosition () {
		return this.black.getPosition();
	}

	public CanvasPosition getGrayPosition () {
		return this.gray.getPosition();
	}

	public void hide () {
		this.root.hide();
	}

	public double getWidth () {
		return this.black.getWidth();
	}

	public double getHeight () {
		return this.black.getHeight();
	}
}
