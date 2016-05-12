
package com.jfixby.telecam.ui.actions;

import com.jfixby.telecam.ui.TelecamUnit;

public class DisableInput extends TelecamUIAction {

	@Override
	public void start (final TelecamUnit ui) {
		ui.disableInput();
	}

	@Override
	public void perform (final TelecamUnit ui) {
	}

	@Override
	public boolean isDone (final TelecamUnit ui) {
		return true;
	}

}
