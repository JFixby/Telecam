
package com.jfixby.telecam.ui.actions;

import com.jfixby.r3.api.ui.unit.animation.Animation;
import com.jfixby.r3.api.ui.unit.animation.OnAnimationDoneListener;
import com.jfixby.telecam.ui.TelecamUnit;

public class GoAcceptDeclineVideo extends TelecamUIAction implements OnAnimationDoneListener {
	final OnAnimationDoneListener animation_done_listener = this;
	private boolean done;

	@Override
	public void push (final TelecamUnit ui) {

	}

	@Override
	public void start (final TelecamUnit ui) {
		this.done = false;
		ui.animateAcceptDecline(this);

	}

	@Override
	public boolean isDone (final TelecamUnit ui) {

		if (this.done) {
			ui.bindAcceptDecline(TelecamUIAction.doDiscardVideo, TelecamUIAction.doDiscardVideo);
// ui.showGoCrop();
			ui.showPlayStop();
			ui.enableInput();
		}

		return this.done;
	}

	@Override
	public void onAnimationDone (final Animation animation) {
		this.done = true;
	}

}
