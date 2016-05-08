
package com.jfixby.telecam.ui.input.cropper.tool;

import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.geometry.RectangleComponent;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.telecam.ui.input.cropper.Cropper;

public class CropperTool {

	private Layer root;
	private final Cropper master;
	private Raster corner;
	private Raster fatLine;
	private Raster slimLine;
	private Raster shadow;

	private final Rectangle cropArea = Geometry.newRectangle();
	private RectangleComponent cropAreaDebug;
	private Layer rasterList;

	public CropperTool (final Cropper cropper) {
		this.master = cropper;

	}

	public void setup (final Layer component_root) {
		this.root = component_root;
		this.rasterList = component_root.findComponent("raster");

		this.corner = this.rasterList.findComponent("L");
		this.corner.copy();
		this.fatLine = this.rasterList.findComponent("fat-line");
		this.slimLine = this.rasterList.findComponent("slim-line");
		this.shadow = this.rasterList.findComponent("shadow");
		this.rasterList.detatchAllComponents();

		this.cropArea.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.cropArea.setPosition(0, 0);

		this.cropAreaDebug = component_root.getComponentsFactory().getGeometryDepartment().newRectangle(this.cropArea);
		this.cropAreaDebug.setDebugRenderFlag(true);
		this.root.attachComponent(this.cropAreaDebug);
// Sys.exit();
	}

	public void update (final Rectangle viewport_update) {

		final double activeHeight = (viewport_update.getHeight() - this.master.getBackground().getHeight());
		this.cropArea.setPositionX(viewport_update.getWidth() / 2);
		this.cropArea.setPositionY(activeHeight / 2);
		this.cropArea.setSize(viewport_update.getWidth() / 3, activeHeight / 3);
	}

}
