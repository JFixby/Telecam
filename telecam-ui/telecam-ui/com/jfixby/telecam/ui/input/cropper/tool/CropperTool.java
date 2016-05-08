
package com.jfixby.telecam.ui.input.cropper.tool;

import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.cmns.api.geometry.RectangleCorner;
import com.jfixby.r3.api.ui.unit.geometry.RectangleComponent;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.telecam.ui.input.cropper.Cropper;

public class CropperTool {
	private final Rectangle cropArea = Geometry.newRectangle();
	private RectangleComponent cropAreaDebug;
	private Layer rasterList;

	public CropperTool (final Cropper cropper) {
		this.master = cropper;

		this.cornerTopLeft = new Corner(this, this.getVertex(0), this.getVertex(1));
		this.cornerTopRight = new Corner(this, this.getVertex(1), this.getVertex(2));
		this.cornerBottomLeft = new Corner(this, this.getVertex(2), this.getVertex(3));
		this.cornerBottomRight = new Corner(this, this.getVertex(3), this.getVertex(4));
	}

	private RectangleCorner getVertex (final int i) {
		return null;
	}

	private Layer root;
	private final Cropper master;

	private final Corner cornerTopLeft;
	private final Corner cornerTopRight;
	private final Corner cornerBottomLeft;
	private final Corner cornerBottomRight;

	float o3 = 1f / 3;
	float o6 = 1f / 6;

	private final Line fatLineLeft = new Line(this, 0, 0, 0, 1);
	private final Line fatLineRight = new Line(this, 1, 0, 1, 1);
	private final Line fatLineTop = new Line(this, 0, 0, 1, 0);
	private final Line fatLineBottom = new Line(this, 0, 1, 1, 1);

	private final Line slimLineLeft = new Line(this, this.o3, 0, this.o3, 1);
	private final Line slimLineRight = new Line(this, this.o6, 0, this.o6, 1);
	private final Line slimLineTop = new Line(this, 0, this.o3, 1, this.o3);
	private final Line slimLineBottom = new Line(this, 0, this.o6, 1, 6);

	private final Shadow shadowLeft = new Shadow(this, this.cornerBottomLeft, this.cornerTopLeft, 1f);
	private final Shadow shadowRight = new Shadow(this, this.cornerTopRight, this.cornerBottomRight, 1f);
	private final Shadow shadowTop = new Shadow(this, this.cornerTopLeft, this.cornerTopRight, 1f);
	private final Shadow shadowBottom = new Shadow(this, this.cornerBottomRight, this.cornerBottomLeft, 1f);

	public void setup (final Layer component_root) {
		this.root = component_root;
		this.rasterList = component_root.findComponent("raster");

		final Raster corner = this.rasterList.findComponent("L");

		final Raster fatLine = this.rasterList.findComponent("fat-line");
		final Raster slimLine = this.rasterList.findComponent("slim-line");
		final Raster shadow = this.rasterList.findComponent("shadow");
		this.rasterList.detatchAllComponents();

		this.shadowLeft.setup(shadow);
		this.shadowRight.setup(shadow.copy());
		this.shadowTop.setup(shadow.copy());
		this.shadowBottom.setup(shadow.copy());

		this.slimLineLeft.setup(slimLine);
		this.slimLineRight.setup(slimLine.copy());
		this.slimLineTop.setup(slimLine.copy());
		this.slimLineBottom.setup(slimLine.copy());

		this.fatLineLeft.setup(fatLine);
		this.fatLineRight.setup(fatLine.copy());
		this.fatLineTop.setup(fatLine.copy());
		this.fatLineBottom.setup(fatLine.copy());

		this.cornerTopLeft.setup(corner);
		this.cornerTopRight.setup(corner.copy());
		this.cornerBottomLeft.setup(corner.copy());
		this.cornerBottomRight.setup(corner.copy());

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

		this.cornerTopLeft.update();
		this.cornerTopRight.update();
		this.cornerBottomLeft.update();
		this.cornerBottomRight.update();

		this.shadowRight.update();
		this.shadowLeft.update();
		this.shadowTop.update();
		this.shadowBottom.update();

		this.slimLineLeft.update();
		this.slimLineRight.update();
		this.slimLineTop.update();
		this.slimLineBottom.update();

		this.fatLineBottom.update();
		this.fatLineTop.update();
		this.fatLineRight.update();
		this.fatLineLeft.update();

	}

	public Layer getRasterList () {
		return this.rasterList;
	}

}
