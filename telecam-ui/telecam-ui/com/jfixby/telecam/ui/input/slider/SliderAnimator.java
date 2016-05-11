
package com.jfixby.telecam.ui.input.slider;

import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.r3.api.ui.unit.animation.OnAnimationDoneListener;
import com.jfixby.r3.api.ui.unit.update.OnUpdateListener;
import com.jfixby.r3.api.ui.unit.update.UnitClocks;
import com.jfixby.telecam.ui.TelecamUnit;

public class SliderAnimator implements OnUpdateListener {
	private final OnUpdateListener listener = this;
	private static final long DELTA = TelecamUnit.ANIMATION_DELTA / 2;
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
	private final DotWorm worm;
	private boolean animatingHead;
	private long delta;

	public SliderAnimator (final Slider slider, final DotIndicator indicator, final DotWorm worm) {
		this.master = slider;
		this.indicator = indicator;
		this.worm = worm;
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
		this.progress = (this.currentTime - this.beginTime) * 1d / this.delta;
		this.currentPosition = this.beginPosition + (this.targetPosition - this.beginPosition) * this.progress;
		if (this.animatingHead) {
			this.indicator.setSliderState(this.currentPosition);
			this.worm.stretchTo(this.beginPosition, this.currentPosition);
		} else {
			this.worm.stretchTo(this.targetPosition, this.currentPosition);
		}

		if (this.animating == false) {
			if (this.animatingHead) {
				this.send(this.animation_done_listener, this.targetPosition, this.beginPosition, false, this.delta);
			} else {
				if (this.animation_done_listener != null) {
					this.animation_done_listener.onAnimationDone(null);
				}
			}
		}
	}

	public void sendSliderToPhoto (final OnAnimationDoneListener animation_done_listener) {
		this.master.setVideoMode();
		this.send(animation_done_listener, +1, -1, true);
	}

	public void sendSliderToVideo (final OnAnimationDoneListener animation_done_listener) {
		this.master.setPhotoMode();
		this.send(animation_done_listener, -1, +1, true);
	}

	private void send (final OnAnimationDoneListener animation_done_listener, final double beginPosition,
		final double targetPosition, final boolean head) {
		this.send(animation_done_listener, beginPosition, targetPosition, head, DELTA);

	}

	private void send (final OnAnimationDoneListener animation_done_listener, final double beginPosition,
		final double targetPosition, final boolean head, final long delta) {
		this.animation_done_listener = animation_done_listener;
		this.beginTime = Sys.SystemTime().currentTimeMillis();
		this.targetTime = this.beginTime + delta;
		this.animating = true;
		this.beginPosition = beginPosition;
		this.targetPosition = targetPosition;
		this.animatingHead = head;
		this.delta = delta;

	}

	public void sendSliderToPhotoFast () {
		this.send(this.animation_done_listener, +1, -1, true, 1);
	}

	public void sendSliderToVideoFast () {
		this.send(this.animation_done_listener, -1, +1, true, 1);
	}

}
