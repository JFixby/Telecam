
package com.jfixby.telecam.ui.actions;

import com.jfixby.r3.api.ui.unit.animation.Animation;
import com.jfixby.r3.api.ui.unit.animation.AnimationLifecycleListener;
import com.jfixby.telecam.ui.TelecamUnit;

public class SwitchToPhotoShoot extends TelecamUIAction implements AnimationLifecycleListener {

	final AnimationLifecycleListener animation_done_listener = this;
	private boolean done;

	@Override
	public void start (final TelecamUnit ui) {
		this.done = false;
		ui.sendSliderToPhoto(this.animation_done_listener);
	}

	@Override
	public void push (final TelecamUnit ui) {
	}

	@Override
	public boolean isDone (final TelecamUnit ui) {
		return this.done;
	}

	@Override
	public void onAnimationDone (final Animation animation, final int loopIndex) {
		this.done = true;
	}

	@Override
	public void onAnimationStart (final Animation animation) {
	}

	@Override
	public void onLoop (final Animation animation, final int loopIndex) {
	}

}
