
package com.jfixby.telecam.ui.input.cropper.rotator;

import com.jfixby.cmns.api.angles.Angles;
import com.jfixby.cmns.api.err.Err;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.cmns.api.math.CustomAngle;
import com.jfixby.cmns.api.math.FloatMath;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.input.CustomInputSpecs;
import com.jfixby.r3.api.ui.unit.input.TouchArea;
import com.jfixby.r3.api.ui.unit.input.TouchAreaSpecs;
import com.jfixby.r3.api.ui.unit.input.UserInputFactory;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.raster.RasterPool;
import com.jfixby.telecam.ui.FontSettings;
import com.jfixby.telecam.ui.input.cropper.Cropper;
import com.jfixby.telecam.ui.input.cropper.CropperButtonRotate;

public class Rotator {

	private final CropperButtonRotate btnRotate;
// private final List<Raster> exampleRasters;
	private final AngleIndicator angleIndicator;
	private final Cropper master;
	private final CustomAngle angle = Angles.newAngle();

	public Rotator (final Cropper cropper, final CropperButtonRotate btnRotate) {
		this.btnRotate = btnRotate;
// this.exampleRasters = Collections.newList();
		this.angleIndicator = new AngleIndicator(this);
		this.master = cropper;
	}

	private void setRasterPosition (final Raster raster, final CanvasPosition position) {
		raster.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		raster.setPosition(position);
	}

	boolean needToDeploy = true;
	private Layer root;
	private TouchArea touchArea;
	private RasterPool bigWhite;
	private RasterPool bigBlue;
	private RasterPool mediumBlue;
	private RasterPool mediumWhite;
	private RasterPool smallWhite;
	private RasterPool smallBlue;
	private double touchHeight;
	private double touchWidth;

	public void setup (final Layer component_root) {
		this.root = component_root;
		if (this.needToDeploy) {
			final ComponentsFactory factory = component_root.getComponentsFactory();
			this.deploy(factory);
			this.needToDeploy = false;
		}
		{
			final Layer indicator = this.root.findComponent("angle-indicator");
			this.angleIndicator.setup(indicator);
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

		final Raster bigWhite = rasters.findComponent("bigWhite");
		final Raster bigBlue = rasters.findComponent("bigBlue");
		final Raster mediumWhite = rasters.findComponent("mediumWhite");
		final Raster mediumBlue = rasters.findComponent("mediumBlue");
		final Raster smallWhite = rasters.findComponent("smallWhite");
		final Raster smallBlue = rasters.findComponent("smallBlue");

		rasters.detatchAllComponents();

		this.bigWhite = factory.getRasterDepartment().newRasterPool(bigWhite.getAssetID());
		this.bigBlue = factory.getRasterDepartment().newRasterPool(bigBlue.getAssetID());
		this.mediumWhite = factory.getRasterDepartment().newRasterPool(mediumWhite.getAssetID());
		this.mediumBlue = factory.getRasterDepartment().newRasterPool(mediumBlue.getAssetID());
		this.smallWhite = factory.getRasterDepartment().newRasterPool(smallWhite.getAssetID());
		this.smallBlue = factory.getRasterDepartment().newRasterPool(smallBlue.getAssetID());

		rasters.attachComponent(this.bigWhite);
		rasters.attachComponent(this.bigBlue);

		rasters.attachComponent(this.mediumWhite);
		rasters.attachComponent(this.mediumBlue);

		rasters.attachComponent(this.smallWhite);
		rasters.attachComponent(this.smallBlue);

// rasters.detatchAllComponents();
	}

	public double getWidth () {
		return this.touchWidth;
	}

	public double getHeight () {
		return this.touchHeight;
	}

	public void update (final Rectangle viewport_update) {
		final double btnRotateX = this.btnRotate.getX();
		final double rasterWidth = this.btnRotate.getRasterWidth();
		this.touchWidth = this.btnRotate.getTouchWidth();
		this.touchHeight = this.btnRotate.getTouchHeight();

		final double distanceToRotateButton = btnRotateX - viewport_update.getWidth() / 2 - this.touchWidth;
		this.touchArea.shape().setPositionX(viewport_update.getWidth() / 2);
		this.touchArea.shape().setPositionY(this.btnRotate.getY());
		this.touchArea.shape().setHeight(this.touchHeight);
		final double otherWidth = rasterWidth * 8 * 2;
		this.HUGE_STEPPING = rasterWidth / 2;
		final double componentWidth = FloatMath.min(distanceToRotateButton * 2, otherWidth);

		this.touchArea.shape().setWidth(componentWidth);
		final CanvasPosition center = this.touchArea.shape().getPosition();
// this.setRasterPosition(this.bigWhite, center);
// this.setRasterPosition(this.bigBlue, this.touchArea.shape().getPosition());
// this.setRasterPosition(this.mediumBlue, this.touchArea.shape().getPosition());
// this.setRasterPosition(this.mediumWhite, this.touchArea.shape().getPosition());
// this.setRasterPosition(this.smallWhite, this.touchArea.shape().getPosition());
// this.setRasterPosition(this.smallBlue, this.touchArea.shape().getPosition());

// for (int i = 0; i < this.exampleRasters.size(); i++) {
// final Raster raster_i = this.exampleRasters.getElementAt(i);
// raster_i.setPositionX(raster_i.getPositionX() + this.HUGE_STEPPING * i);
// }

		this.angleIndicator.update(this.touchArea.shape().getPosition());

	}

	double HUGE_STEPPING;

	public FontSettings getFontSettings () {
		return this.master.getFontSettings();
	}

	public void reset () {
		this.master.getResetButton().hide();
		this.angle.setValue(0);
		this.updateAngleCaption();

	}

	private void updateAngleCaption () {
		this.angleIndicator.updateValue(this.angle);
	}

// public double getPositionY () {
// return this.touchArea.shape().;
// }

}
