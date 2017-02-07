
package com.jfixby.telecam.ui.input.cropper.tool;

import com.jfixby.r3.api.ui.unit.geometry.RectangleComponent;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.scarabei.api.angles.Angles;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.math.Angle;
import com.jfixby.telecam.ui.input.cropper.Cropper;

public class CropperTool {
	private final Rectangle cropArea = Geometry.newRectangle();
	private RectangleComponent cropAreaDebug;
	private Layer rasterList;

	public CropperTool (final Cropper cropper) {
		this.master = cropper;

		this.cornerTopLeft = new Corner(this, this.getVertex(0), Angles.ZERO());
		this.cornerBottomLeft = new Corner(this, this.getVertex(1), Angles.g90());
		this.cornerTopRight = new Corner(this, this.getVertex(2), Angles.g180());
		this.cornerBottomRight = new Corner(this, this.getVertex(3), Angles.g270());

		this.fatLineTop = new Line(this, 0, 0, 1, 0);
		this.fatLineRight = new Line(this, 1, 0, 1, 1);
		this.fatLineBottom = new Line(this, 1, 1, 0, 1);
		this.fatLineLeft = new Line(this, 0, 1, 0, 0);

		this.slimLineLeft = new Line(this, this.o3, 0, this.o3, 1);
		this.slimLineRight = new Line(this, this.o6, 0, this.o6, 1);
		this.slimLineTop = new Line(this, 0, this.o3, 1, this.o3);
		this.slimLineBottom = new Line(this, 0, this.o6, 1, this.o6);

		this.shadowTop = new Shadow(this, this.fatLineTop);
		this.shadowLeft = new Shadow(this, this.fatLineLeft);
		this.shadowRight = new Shadow(this, this.fatLineRight);
		this.shadowBottom = new Shadow(this, this.fatLineBottom);
	}

	private ReadOnlyFloat2 getVertex (final int i) {
		return this.cropArea.listVertices().getElementAt(i);
	}

	private Layer root;
	private final Cropper master;

	private final Corner cornerTopLeft;
	private final Corner cornerTopRight;
	private final Corner cornerBottomLeft;
	private final Corner cornerBottomRight;

	float o3 = 1 / 3f;
	float o6 = 2 / 3f;

	private final Line fatLineTop;
	private final Line fatLineRight;
	private final Line fatLineBottom;
	private final Line fatLineLeft;

	private final Line slimLineLeft;
	private final Line slimLineRight;
	private final Line slimLineTop;
	private final Line slimLineBottom;

	private final Shadow shadowLeft;
	private final Shadow shadowRight;
	private final Shadow shadowTop;
	private final Shadow shadowBottom;
	private double maxWidth;
	private double maxHeight;
	private double currentRelativeX = 0.8;
	private double currentRelativeY = 0.8;

	public void setup (final Layer component_root) {
		this.root = component_root;
		this.rasterList = component_root.findComponent("raster");

		final Raster wing = this.rasterList.findComponent("wing");

		final Raster fatLine = this.rasterList.findComponent("fat-line");
		final Raster slimLine = this.rasterList.findComponent("slim-line");
		final Raster shadow = this.rasterList.findComponent("shadow");
		this.rasterList.detatchAllComponents();
//

		this.shadowLeft.setup(shadow);
		this.shadowRight.setup(shadow);
		this.shadowTop.setup(shadow);
		this.shadowBottom.setup(shadow);

		this.slimLineLeft.setup(slimLine);
		this.slimLineRight.setup(slimLine);
		this.slimLineTop.setup(slimLine);
		this.slimLineBottom.setup(slimLine);

		this.fatLineLeft.setup(fatLine);
		this.fatLineRight.setup(fatLine);
		this.fatLineTop.setup(fatLine);
		this.fatLineBottom.setup(fatLine);

		this.cornerTopLeft.setup(wing);
		this.cornerTopLeft.also(wing);

		this.cornerTopRight.setup(wing);
		this.cornerTopRight.also(wing);

		this.cornerBottomLeft.setup(wing);
		this.cornerBottomLeft.also(wing);

		this.cornerBottomRight.setup(wing);
		this.cornerBottomRight.also(wing);

		this.cropArea.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.cropArea.setPosition(100, 100);

		this.cropAreaDebug = component_root.getComponentsFactory().getGeometryDepartment().newRectangle(this.cropArea);
		this.cropAreaDebug.setDebugRenderFlag(true);
// this.root.attachComponent(this.cropAreaDebug);
//

// this.rasterList.print();
// Sys.exit();
	}

	public void update (final Rectangle viewport_update) {

		final double activeHeight = (viewport_update.getHeight() - this.master.getBackground().getHeight());
		this.cropArea.setPositionX(viewport_update.getWidth() / 2);
		this.cropArea.setPositionY(activeHeight / 2);
		this.maxWidth = viewport_update.getWidth();
		this.maxHeight = activeHeight;
		this.updateCropArea();

// this.cropArea.setRotation(Angles.g45().toRadians() / 8);

		this.updateCorners();

	}

	private void updateCropArea () {
		this.cropArea.setSize(this.maxWidth * this.currentRelativeX, this.maxHeight * this.currentRelativeY);
	}

	private void updateCorners () {
		this.cornerTopLeft.update();
		this.cornerTopRight.update();
		this.cornerBottomLeft.update();
		this.cornerBottomRight.update();

		this.fatLineBottom.update();
		this.fatLineTop.update();
		this.fatLineRight.update();
		this.fatLineLeft.update();

		this.slimLineLeft.update();
		this.slimLineRight.update();
		this.slimLineTop.update();
		this.slimLineBottom.update();

		this.shadowRight.update();
		this.shadowLeft.update();
		this.shadowTop.update();
		this.shadowBottom.update();
	}

	public Layer getRasterList () {
		return this.rasterList;
	}

	public Angle getRotation () {
		return this.cropArea.getRotation();
	}

	public Rectangle getArea () {
		return this.cropArea;
	}

	public void reset () {
		this.currentRelativeX = 1;
		this.currentRelativeY = 1;
		this.updateCropArea();
		this.updateCorners();
	}

}
