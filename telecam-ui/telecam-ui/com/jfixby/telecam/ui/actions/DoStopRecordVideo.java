
package com.jfixby.telecam.ui.actions;

import com.jfixby.telecam.ui.TelecamUnit;

public class DoStopRecordVideo extends TelecamUIAction {

	@Override
	public void start (final TelecamUnit ui) {
	}

	@Override
	public void push (final TelecamUnit ui) {
	}

	@Override
	public boolean isDone (final TelecamUnit ui) {
		ui.uploadVideoToPlayer();
		return true;
	}

}
