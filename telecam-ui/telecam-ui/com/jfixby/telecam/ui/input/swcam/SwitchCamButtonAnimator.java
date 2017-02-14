
package com.jfixby.telecam.ui.input.swcam;

import com.jfixby.r3.api.ui.unit.animation.OnAnimationDoneListener;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.update.UnitClocks;
import com.jfixby.r3.api.ui.unit.update.OnUpdateListener;
import com.jfixby.scarabei.api.sys.Sys;
import com.jfixby.telecam.ui.TelecamUnit;

public class SwitchCamButtonAnimator implements OnUpdateListener {

	private static final String SELFIE = "SELFIE";
	private static final String REGULAR = "REGULAR";
	private final SwitchCameraButton master;

	public SwitchCamButtonAnimator (final SwitchCameraButton switchCameraButton) {
		this.master = switchCameraButton;
	}

	long delta = TelecamUnit.ANIMATION_DELTA;
	private long begin;
	private long end;
	private OnAnimationDoneListener animation_done_listener;
	private boolean animating;
	private int beginRoll;

	public void roll (final OnAnimationDoneListener animation_done_listener) {
		this.begin = Sys.SystemTime().currentTimeMillis();
		this.end = this.begin + this.delta;
		this.animation_done_listener = animation_done_listener;
		this.animating = true;
		this.beginRoll = this.current_roll;
	}

	int current_roll = 0;
	OnUpdateListener animator = this;
	private long currentTime;
	private double progress;
	private Raster roll;
	private Raster center;
	private Raster ring;
	private double original_size;

	public void setup (final Layer root, final Raster center, final Raster ring, final Raster roll) {
		root.attachComponent(this.animator);
		this.roll = roll;
		this.center = center;
		this.original_size = center.shape().getWidth();
		this.ring = ring;
// regular.hide();
// selfie.hide();
		this.roll(0, SELFIE);
	}

	private void roll (final double rotation) {
		String mode = REGULAR;
		final int rotation_int = (int)rotation;
		if (rotation_int % 2 == 0) {
			mode = SELFIE;
		}
		this.roll(rotation, mode);
	}

	private void roll (final double rotation, final String mode) {
		final double angle = -rotation * Math.PI;
		this.roll.setRotation(angle);
		final double size = (int)(Math.sin(angle / 2) * this.original_size);
// L.d("size", size);
		this.center.setSize(size, size);
// this.regular.setSize(size, size);
	}

	@Override
	public void onUpdate (final UnitClocks unit_clock) {
		if (!this.animating) {
			return;
		}
		this.currentTime = Sys.SystemTime().currentTimeMillis() - this.begin;

		this.progress = this.currentTime * 1d / this.delta;

		if (this.progress > 1) {
			this.animating = false;
			this.animation_done_listener.onAnimationDone(null);
			this.current_roll++;
			this.roll(this.current_roll);
			return;
		}
		this.roll(this.current_roll + this.progress);
	}

}
