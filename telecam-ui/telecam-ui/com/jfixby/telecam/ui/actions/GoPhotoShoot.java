
package com.jfixby.telecam.ui.actions;

import com.jfixby.telecam.ui.TelecamUnit;

public class GoPhotoShoot extends TelecamUIAction {

	@Override
	public void perform (final TelecamUnit ui) {
		ui.disableInput();
		ui.goPhotoShoot();
		ui.enableInput();
	}

	@Override
	public void start (final TelecamUnit ui) {
	}

	@Override
	public boolean isDone (final TelecamUnit ui) {
		return true;
	}

}
