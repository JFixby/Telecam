
package com.jfixby.telecam.ui.actions;

import com.jfixby.telecam.ui.TelecamUnit;

public class GoPhotoShoot extends TelecamUIAction {

	@Override
	public void perform (final TelecamUnit ui) {

	}

	@Override
	public void start (final TelecamUnit ui) {
		ui.disableInput();
	}

	@Override
	public boolean isDone (final TelecamUnit ui) {
		ui.goPhotoShoot();
		ui.enableInput();
		return true;
	}

}
