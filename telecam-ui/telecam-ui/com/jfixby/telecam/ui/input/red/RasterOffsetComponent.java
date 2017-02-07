
package com.jfixby.telecam.ui.input.red;

import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_VERTICAL;

public class RasterOffsetComponent {

	private final RedButton master;
	private Raster raster;
	private final CanvasPosition originalOffset;
	private final CanvasPosition currentCenter;
	private final CanvasPosition tmp;
	private final CanvasPosition currentOffset;
	private Layer parentRoot;

	public RedButton getMaster () {
		return this.master;
	}

	public Raster getRaster () {
		return this.raster;
	}

	public Layer getParentRoot () {
		return this.parentRoot;
	}

	public void hide () {
		this.raster.hide();
	}

	public void show () {
		this.raster.show();
	}

	public RasterOffsetComponent (final RedButton slider) {
		this.master = slider;
		this.originalOffset = Geometry.newCanvasPosition();
		this.currentCenter = Geometry.newCanvasPosition();
		this.currentOffset = Geometry.newCanvasPosition();
		this.tmp = Geometry.newCanvasPosition();
	}

	public ReadOnlyFloat2 getOriginalOffset () {
		return this.originalOffset;
	}

	public void setup (final Raster findComponent, final Layer root) {
		this.raster = findComponent;
		this.parentRoot = root;
		this.raster.setOriginAbsolute(this.master.getPosition());
		findComponent.setDebugRenderFlag(!true);
		this.raster.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.originalOffset.setPosition(this.raster.getPosition());
		this.originalOffset.subtract(this.master.getPosition());
		this.currentCenter.set(this.raster.getPosition());
		this.currentOffset.setPosition(this.originalOffset);
		findComponent.setDebugRenderFlag(!true);
	}

	public void setCenter (final Float2 position) {
		this.currentCenter.setPosition(position);
		this.updateRaster();
	}

	private void updateRaster () {
		this.tmp.set(this.currentCenter);
		this.tmp.add(this.currentOffset);
		this.raster.setPosition(this.tmp);
	}

	public void setOffset (final Float2 offset) {
		this.setOffset(offset.getX(), offset.getY());
	}

	public void setOffset (final double x, final double y) {
		this.currentOffset.setXY(x, y);
		this.updateRaster();
	}

	public void setOffsetX (final double x) {
		this.setOffset(x, this.getOffsetY());
	}

	public double getOffsetX () {
		return this.currentOffset.getX();
	}

	public double getOffsetY () {
		return this.currentOffset.getY();
	}

	public ReadOnlyFloat2 getCenter () {
		return this.currentCenter;
	}

}
