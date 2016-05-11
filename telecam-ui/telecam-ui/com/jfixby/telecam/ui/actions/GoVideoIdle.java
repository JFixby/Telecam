
package com.jfixby.telecam.ui.actions;

import com.jfixby.r3.api.ui.unit.animation.Animation;
import com.jfixby.r3.api.ui.unit.animation.OnAnimationDoneListener;
import com.jfixby.telecam.ui.TelecamUnit;

public class GoVideoIdle extends TelecamUIAction implements OnAnimationDoneListener {
	final OnAnimationDoneListener animation_done_listener = this;
	private boolean done;

	@Override
	public void start (final TelecamUnit ui) {
		this.done = false;
		ui.disableInput();
		ui.goVideoIdle(this.animation_done_listener);

	}

	@Override
	public void perform (final TelecamUnit ui) {

	}

	@Override
	public boolean isDone (final TelecamUnit ui) {
		if (this.done) {
			ui.enableInput();
		}
		return this.done;
	}

	@Override
	public void onAnimationDone (final Animation animation) {
		this.done = true;
	}

}