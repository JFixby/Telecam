
package com.jfixby.telecam.ui.input.slider;

import com.jfixby.r3.api.ui.unit.animation.OnAnimationDoneListener;
import com.jfixby.r3.api.ui.unit.update.UnitClocks;
import com.jfixby.r3.api.ui.unit.user.UpdateListener;
import com.jfixby.scarabei.api.sys.Sys;
import com.jfixby.telecam.ui.BackgroundGray;
import com.jfixby.telecam.ui.TelecamUnit;
import com.jfixby.telecam.ui.input.blue.BlueButton;
import com.jfixby.telecam.ui.input.flash.SwitchFlashButton;
import com.jfixby.telecam.ui.input.red.RedButton;

public class SliderAnimator implements UpdateListener {
	private final UpdateListener listener = this;
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
	private final BackgroundGray bgGray;
	private final RedButton redButton;
	private final SwitchFlashButton switchFlashButton;

	public SliderAnimator (final Slider slider, final DotIndicator indicator, final DotWorm worm, final BackgroundGray bgGray,
		final BlueButton blueButton, final RedButton redButton, final SwitchFlashButton switchFlashButton) {
		this.master = slider;
		this.switchFlashButton = switchFlashButton;
		this.indicator = indicator;
		this.worm = worm;
		this.bgGray = bgGray;
		this.redButton = redButton;
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
			final float bgOpacity = (float)(1d - (this.currentPosition + 1d) / 4d * 1.2d);
			final double flashOpacity = 1d - (this.currentPosition + 1d) / 2d;
			this.bgGray.setBackgroundOpacity(bgOpacity);

			final double redToWide = (this.currentPosition + 1d) / 2d;
			this.redButton.setWide(redToWide);
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
