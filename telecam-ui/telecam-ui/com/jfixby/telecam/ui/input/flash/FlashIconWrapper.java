
package com.jfixby.telecam.ui.input.flash;

import com.jfixby.cmns.api.floatn.Float2;
import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.telecam.ui.TelecamUnit;

public class FlashIconWrapper {

	private static final double DELTA = TelecamUnit.ANIMATION_DELTA;
	private static final int INDEX_OFFSET = -1;
	private final Raster raster;
	private final SwitchFlashButton master;

	private final Float2 originalPosition = Geometry.newFloat2();
	private final double baseRelativeY;
	private double myRelativeY0;

	public FlashIconWrapper (final Raster elementAt, final SwitchFlashButton master) {
		this.raster = elementAt;
		this.raster.setDebugRenderFlag(!true);
		this.master = master;
		this.originalPosition.set(this.raster.getPosition());
		this.baseRelativeY = this.raster.shape().getOriginRelativeY();
	}

	public void pack () {
		this.myRelativeY0 = this.raster.shape().getOriginRelativeY();
	}

	public double getPositionY () {
		return this.raster.getPositionY();
	}

	public void toRelative (final Float2 tmp) {
		this.raster.shape().toRelative(tmp);
	}

	public void setRelativeY (final double y) {
		this.raster.setOriginRelativeY(y);
	}

	@Override
	public String toString () {
		return "FlashIconWrapper [baseRelativeY=" + this.baseRelativeY + "]";
	}

	public void setY (final double newY) {
		this.raster.setPositionY(newY);
	}

	public void setOriginAbsoluteY (final double y) {
		this.raster.setOriginAbsoluteY(y);
	}

	public void setOpacity (final float d) {
		this.raster.setOpacity(d);
	}

	public double getRelativeBaseY () {
		return this.myRelativeY0;
	}

	public void setX (final double x) {
		this.raster.setPositionX(x);
	}

	public void setIndexedPosition (final double y0, final int index, final double roll) {
		final double shiftMIN = ((index + INDEX_OFFSET - 0)) * this.master.getHeight();
		final double shiftMAX = ((index + INDEX_OFFSET + 1)) * this.master.getHeight();
		final double shiftDELTA = shiftMAX - shiftMIN;

		double shift = ((index + INDEX_OFFSET + roll)) * this.master.getHeight();
// shift = shift + shiftDELTA;
		shift = shift % (shiftDELTA * 3) - shiftDELTA;
// if (shift < 0) {
// shift = shift + shiftDELTA * 3;
// }

		this.raster.setPositionY(y0 + shift);
		double distance = Math.abs(shift / this.master.getHeight());
		if (distance > 1) {
			distance = 1;
		}
		final float alpha = (float)(1 - distance * distance);
		this.raster.setOpacity(alpha);

	}

}
