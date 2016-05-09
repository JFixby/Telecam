
package com.jfixby.telecam.ui.actions;

import com.jfixby.r3.api.ui.UIAction;
import com.jfixby.telecam.ui.TelecamUnit;

public class GoPhotoShoot implements UIAction<TelecamUnit> {

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
