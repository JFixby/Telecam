
package com.jfixby.telecam.ui.actions;

import com.jfixby.telecam.ui.TelecamUnit;

public class EnableInput extends TelecamUIAction {

	@Override
	public void start (final TelecamUnit ui) {
		ui.enableInput();
	}

	@Override
	public void push (final TelecamUnit ui) {
	}

	@Override
	public boolean isDone (final TelecamUnit ui) {

		return true;
	}

}
