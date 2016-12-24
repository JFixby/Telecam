
package com.jfixby.telecam.ui.actions;

import com.jfixby.scarabei.api.sys.Sys;
import com.jfixby.telecam.ui.TelecamUnit;

public class DoBlink extends TelecamUIAction {
	long endBy = 0;
	long delta = TelecamUnit.ANIMATION_DELTA;
	long start = 0;

	@Override
	public void push (final TelecamUnit ui) {
		float value = (Sys.SystemTime().currentTimeMillis() - this.start) * 1f / this.delta;
		if (value > 1f) {
			value = 1;
		}
		if (value < 0f) {
			value = 0;
		}
// value = value * value;
		value = 1 - value;

		ui.setBlink(value);
	}

	@Override
	public void start (final TelecamUnit ui) {
		ui.setBlinkBegin();
		ui.setBlink(1f);
		this.start = Sys.SystemTime().currentTimeMillis();
		this.endBy = this.start + this.delta;
	}

	@Override
	public boolean isDone (final TelecamUnit ui) {
		final boolean done = Sys.SystemTime().currentTimeMillis() >= this.endBy;
		if (done) {
			ui.setBlink(0f);
			ui.setBlinkEnd();
		}
		return done;
	}
}
