
package com.jfixby.telecam.ui.actions;

import com.jfixby.telecam.ui.TelecamUnit;

public class PressBlueButton extends TelecamUIAction {

	@Override
	public void start (final TelecamUnit ui) {
		ui.pressBlueButton();
	}

	@Override
	public void push (final TelecamUnit ui) {
	}

	@Override
	public boolean isDone (final TelecamUnit ui) {
		return true;
	}

}
