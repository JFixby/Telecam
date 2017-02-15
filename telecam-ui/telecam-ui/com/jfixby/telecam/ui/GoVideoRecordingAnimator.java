
package com.jfixby.telecam.ui;

import com.jfixby.r3.api.ui.unit.animation.AnimationLifecycleListener;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.update.OnUpdateListener;
import com.jfixby.r3.api.ui.unit.update.UnitClocks;
import com.jfixby.scarabei.api.sys.Sys;
import com.jfixby.telecam.ui.input.red.RedButton;

public class GoVideoRecordingAnimator implements OnUpdateListener {

	private static final boolean RECORDING = false;
	private static final boolean IDLE = !RECORDING;
	private AnimationLifecycleListener animation_done_listener;
	private final long delta = TelecamUnit.ANIMATION_DELTA;
	private long begin;
	private long end;
	private double progress;
	private boolean animating;
	BackgroundGray bgGray;
	private final UserInputBar master;

	public GoVideoRecordingAnimator (final UserInputBar userInputBar) {
		this.master = userInputBar;

	}

	boolean target = RECORDING;

	public void goVideoRecording (final AnimationLifecycleListener animation_done_listener) {
		this.animation_done_listener = animation_done_listener;
		this.begin = Sys.SystemTime().currentTimeMillis();
		this.end = this.begin + this.delta;
		this.progress = 0d;
		this.animating = true;
		this.target = RECORDING;
	}

	public void goVideoIdle (final AnimationLifecycleListener animation_done_listener) {
		this.animation_done_listener = animation_done_listener;
		this.begin = Sys.SystemTime().currentTimeMillis();
		this.end = this.begin + this.delta;
		this.progress = 0d;
		this.animating = true;
		this.target = IDLE;
		final RedButton red = this.master.getRedButton();
		red.convertBack();
	}

	OnUpdateListener animator = this;
	private long currentTime;

	public void setup (final Layer root) {
		root.attachComponent(this);
	}

	@Override
	public void onUpdate (final UnitClocks unit_clock) {
		if (!this.animating) {
			return;
		}
		this.currentTime = Sys.SystemTime().currentTimeMillis();

		this.progress = (this.currentTime - this.begin) * 1d / this.delta;
		if (this.progress > 1) {
			this.animating = false;
			this.progress = 1;
		}
		this.bgGray = this.master.getBackgroundGray();
		final RedButton red = this.master.getRedButton();
		if (this.target == RECORDING) {
			this.bgGray.setRelativeHeight(1d - this.progress);
			red.convertToSquare(this.progress);
		} else {
			this.bgGray.setRelativeHeight(this.progress);
			red.convertBack(this.progress);
		}

		if (this.animating == false) {
			if (this.target == RECORDING) {
				red.convertToSquare();
			} else {
				red.convertBack(1);
			}

			this.animation_done_listener.onAnimationDone(null, 0);
		}
	}

}
