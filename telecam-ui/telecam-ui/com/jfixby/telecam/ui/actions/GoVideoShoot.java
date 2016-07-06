
package com.jfixby.telecam.ui.actions;

import com.jfixby.telecam.ui.TelecamUnit;

public class GoVideoShoot extends TelecamUIAction {

	@Override
	public void push (final TelecamUnit ui) {

	}

	@Override
	public void start (final TelecamUnit ui) {
		ui.disableInput();
	}

	@Override
	public boolean isDone (final TelecamUnit ui) {
		ui.goVideoShoot();
		ui.enableInput();
		return true;
	}

}
