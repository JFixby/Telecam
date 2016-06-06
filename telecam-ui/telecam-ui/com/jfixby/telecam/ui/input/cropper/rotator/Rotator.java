
package com.jfixby.telecam.ui.input.cropper.rotator;

import com.jfixby.cmns.api.angles.Angles;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.err.Err;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.cmns.api.math.CustomAngle;
import com.jfixby.cmns.api.math.FloatMath;
import com.jfixby.cmns.api.math.MathTools;
import com.jfixby.cmns.api.math.VectorTool;
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

	private static final double NUMBER_OF_SMALL_WHITES = 60d / 2d;
	private final CropperButtonRotate btnRotate;
	private final AngleIndicator angleIndicator;
	private final Cropper master;
	private final CustomAngle angle = Angles.newAngle();

	public Rotator (final Cropper cropper, final CropperButtonRotate btnRotate) {
		this.btnRotate = btnRotate;
		this.angleIndicator = new AngleIndicator(this);
		this.master = cropper;
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
	private double buttonTouchHeight;
	private double buttonTouchWidth;
// private double rotorTouchWidth;

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
		this.touchArea.setDebugRenderFlag(true);
// this.rotorTouchWidth = touchArea.getWidth();
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
		for (int i = 0; i < NUMBER_OF_SMALL_WHITES; i++) {
			final Raster raster = this.smallWhite.newInstance();

			raster.shape().setup(smallWhite.shape());

			raster.setOriginRelativeX(ORIGIN_RELATIVE_HORIZONTAL.CENTER);
			raster.setOriginRelativeY(ORIGIN_RELATIVE_VERTICAL.CENTER);
			this.smallWhiteList.add(raster);
		}
// rasters.detatchAllComponents();
	}

	final List<Raster> smallWhiteList = Collections.newList();
	private double componentWidth;

	public double getHeight () {
		return this.buttonTouchHeight;
	}

	public void update (final Rectangle viewport_update) {
		final double btnRotateX = this.btnRotate.getX();
		final double rasterWidth = this.btnRotate.getRasterWidth();
		this.buttonTouchWidth = this.btnRotate.getTouchWidth();
		this.buttonTouchHeight = this.btnRotate.getTouchHeight();
// this.rotorTouchWidth = this.touchArea.getWidth();

		final double distanceToRotateButton = btnRotateX - viewport_update.getWidth() / 2 - this.buttonTouchWidth;
		this.touchArea.shape().setPositionX(viewport_update.getWidth() / 2);
		this.touchArea.shape().setPositionY(this.btnRotate.getY());
		this.touchArea.shape().setHeight(this.buttonTouchHeight);
		final double otherWidth = rasterWidth * 8 * 2;
		this.HUGE_STEPPING = rasterWidth / 2;
		final double componentWidth = FloatMath.min(distanceToRotateButton * 2, otherWidth);

		this.touchArea.shape().setWidth(componentWidth);
		this.componentWidth = componentWidth;
		final CanvasPosition center = this.touchArea.shape().getPosition();

		this.updateRotationRisks(center);
		this.angleIndicator.update(this.touchArea.shape().getPosition());

	}

	private void updateRotationRisks (final CanvasPosition center) {
		for (int i = 0; i < NUMBER_OF_SMALL_WHITES; i++) {
			Raster raster;

			if (i % (NUMBER_OF_SMALL_WHITES / 12) == 0) {
				raster = this.smallWhiteList.getElementAt(i);
			} else {
				raster = this.smallWhiteList.getElementAt(i);

			}

			raster.setPosition(center);

			this.tool.R = Math.abs(this.getWidth() - this.getHeight() / 2d) * 0.5;
			this.tool.A = i * FloatMath.PI() * 2 / NUMBER_OF_SMALL_WHITES;
			final double rotation = this.tool.A + FloatMath.PI() / 2d;
			if (this.isVisibleRotation(this.tool.A + FloatMath.PI() / 2d)) {
				raster.show();
// raster.setOpacity(1f);
			} else {
				raster.hide();
// raster.setOpacity(0.5f);
			}
// raster.setRotation(rotation);
			this.tool.ARtoXY();

// raster.offset(this.tool.X, this.tool.Y);

			raster.offset(this.tool.X, 0);

		}

	}

	public double getWidth () {
		return this.componentWidth;
	}

	private boolean isVisibleRotation (final double d) {
		return FloatMath.abs(d - FloatMath.PI()) <= FloatMath.PI() / 1;
	}

	final VectorTool tool = MathTools.newVectorTool();

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
