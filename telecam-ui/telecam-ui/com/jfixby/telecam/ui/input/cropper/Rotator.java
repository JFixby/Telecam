
package com.jfixby.telecam.ui.input.cropper;

import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.err.Err;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.cmns.api.math.FloatMath;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.input.CustomInputSpecs;
import com.jfixby.r3.api.ui.unit.input.TouchArea;
import com.jfixby.r3.api.ui.unit.input.TouchAreaSpecs;
import com.jfixby.r3.api.ui.unit.input.UserInputFactory;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public class Rotator {

	private final CropperButtonRotate btnRotate;
	private final List<Raster> exampleRasters;

	public Rotator (final Cropper cropper, final CropperButtonRotate btnRotate) {
		this.btnRotate = btnRotate;
		this.exampleRasters = Collections.newList();
	}

	public void update (final Rectangle viewport_update) {
		final double btnRotateX = this.btnRotate.getX();
		final double rasterWidth = this.btnRotate.getRasterWidth();
		final double touchWidth = this.btnRotate.getTouchWidth();
		final double touchHeight = this.btnRotate.getTouchHeight();

		final double distanceToRotateButton = btnRotateX - viewport_update.getWidth() / 2 - touchWidth;
		this.touchArea.shape().setPositionX(viewport_update.getWidth() / 2);
		this.touchArea.shape().setPositionY(this.btnRotate.getY());
		this.touchArea.shape().setHeight(touchHeight);
		final double otherWidth = rasterWidth * 8 * 2;
		this.HUGE_STEPPING = rasterWidth / 2;
		final double componentWidth = FloatMath.min(distanceToRotateButton * 2, otherWidth);

		this.touchArea.shape().setWidth(componentWidth);

		this.setRasterPosition(this.bigWhite, this.touchArea.shape().getPosition());
		this.setRasterPosition(this.bigBlue, this.touchArea.shape().getPosition());
		this.setRasterPosition(this.mediumBlue, this.touchArea.shape().getPosition());
		this.setRasterPosition(this.mediumWhite, this.touchArea.shape().getPosition());
		this.setRasterPosition(this.smallWhite, this.touchArea.shape().getPosition());
		this.setRasterPosition(this.smallBlue, this.touchArea.shape().getPosition());

		for (int i = 0; i < this.exampleRasters.size(); i++) {
			final Raster raster_i = this.exampleRasters.getElementAt(i);
			raster_i.setPositionX(raster_i.getPositionX() + this.HUGE_STEPPING * i);
		}

	}

	private void setRasterPosition (final Raster raster, final CanvasPosition position) {
		raster.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		raster.setPosition(position);
	}

	boolean needToDeploy = true;
	private Layer root;
	private TouchArea touchArea;
	private Raster bigWhite;
	private Raster bigBlue;
	private Raster mediumBlue;
	private Raster mediumWhite;
	private Raster smallWhite;
	private Raster smallBlue;

	public void setup (final Layer component_root) {
		this.root = component_root;
		if (this.needToDeploy) {
			final ComponentsFactory factory = component_root.getComponentsFactory();
			this.deploy(factory);
			this.needToDeploy = false;
		}
	}

	private void deploy (final ComponentsFactory factory) {
		if (!this.needToDeploy) {
			Err.reportError("Already deployed");
		}
		final UserInputFactory inputFactory = factory.getUserInputDepartment();
		final CustomInputSpecs specs = inputFactory.newCustomInputSpecs();
		final TouchAreaSpecs touchSpecs = inputFactory.newTouchAreaSpecs();
		specs.addTouchArea(touchSpecs);
		final TouchArea touchArea = inputFactory.newTouchArea(touchSpecs);
		this.touchArea = touchArea;
		this.touchArea.setDebugRenderFlag(!true);
		this.touchArea.shape().setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.root.attachComponent(touchArea);

		final Layer rasters = this.root.findComponent("raster");
		rasters.listChildren().print("rasters");

		this.bigWhite = rasters.findComponent("bigWhite");
		this.bigBlue = rasters.findComponent("bigBlue");

		this.mediumWhite = rasters.findComponent("mediumWhite");
		this.mediumBlue = rasters.findComponent("mediumBlue");

		this.smallWhite = rasters.findComponent("smallWhite");
		this.smallBlue = rasters.findComponent("smallBlue");

		this.exampleRasters.add(this.bigWhite);
		this.exampleRasters.add(this.bigBlue);

		this.exampleRasters.add(this.mediumWhite);
		this.exampleRasters.add(this.mediumBlue);

		this.exampleRasters.add(this.smallWhite);
		this.exampleRasters.add(this.smallBlue);

// rasters.detatchAllComponents();
	}

	double HUGE_STEPPING;

}
