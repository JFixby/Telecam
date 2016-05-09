
package com.jfixby.telecam.ui.actions;

import com.jfixby.telecam.ui.TelecamUnit;

public class GoCropper extends TelecamUIAction {

	@Override
	public void start (final TelecamUnit ui) {
	}

	@Override
	public void perform (final TelecamUnit ui) {
	}

	@Override
	public boolean isDone (final TelecamUnit ui) {
		ui.goCropper();
		return true;
	}

}
