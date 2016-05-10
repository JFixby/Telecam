
package com.jfixby.telecam.ui.input.slider;

import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.r3.api.ui.unit.animation.OnAnimationDoneListener;
import com.jfixby.r3.api.ui.unit.update.OnUpdateListener;
import com.jfixby.r3.api.ui.unit.update.UnitClocks;

public class SliderAnimator implements OnUpdateListener {
	private final OnUpdateListener listener = this;
	private static final long DELTA = 200;
	private final Slider master;

	long beginTime;
	double beginPosition;

	long targetTime;
	double targetPosition;

	long currentTime;
	double currentPosition;
	private boolean animating;
	private double progress;
	private final DotIndicator indicator;
	private OnAnimationDoneListener animation_done_listener;

	public SliderAnimator (final Slider slider, final DotIndicator indicator, final DotWorm worm) {
		this.master = slider;
		this.indicator = indicator;
	}

	public void setup () {
		this.master.getRoot().attachComponent(this);
	}

	@Override
	public void onUpdate (final UnitClocks unit_clock) {
		if (!this.animating) {
			return;
		}
		this.currentTime = Sys.SystemTime().currentTimeMillis();
		if (this.currentTime >= this.targetTime) {
			this.currentTime = this.targetTime;
			this.animating = false;
		}
		this.progress = (this.currentTime - this.beginTime) * 1d / DELTA;
		this.currentPosition = this.beginPosition + (this.targetPosition - this.beginPosition) * this.progress;
		this.indicator.setSliderState(this.currentPosition);
		if (this.animating == false) {
			this.animation_done_listener.onAnimationDone(null);
		}
	}

	public void sendSliderToPhoto (final OnAnimationDoneListener animation_done_listener) {
		this.master.setVideoMode();
		L.d("sendSliderToPhoto");
		this.send(animation_done_listener, +1, -1);
	}

	public void sendSliderToVideo (final OnAnimationDoneListener animation_done_listener) {
		this.master.setPhotoMode();
		L.d("sendSliderToVideo");
		this.send(animation_done_listener, -1, +1);
	}

	private void send (final OnAnimationDoneListener animation_done_listener, final int beginPosition, final int targetPosition) {
		this.animation_done_listener = animation_done_listener;
		this.beginTime = Sys.SystemTime().currentTimeMillis();
		this.targetTime = this.beginTime + DELTA;
		this.animating = true;
		this.beginPosition = beginPosition;
		this.targetPosition = targetPosition;
	}

}
