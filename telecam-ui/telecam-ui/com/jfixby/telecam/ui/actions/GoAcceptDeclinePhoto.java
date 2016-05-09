
package com.jfixby.telecam.ui.actions;

import com.jfixby.telecam.ui.TelecamUnit;

public class GoAcceptDeclinePhoto extends TelecamUIAction {

	@Override
	public void perform (final TelecamUnit ui) {

	}

	@Override
	public void start (final TelecamUnit ui) {
	}

	@Override
	public boolean isDone (final TelecamUnit ui) {
		ui.goAcceptDecline(TelecamUIAction.doDiscardPhoto, TelecamUIAction.goAndroidImageGallery);
		ui.showGoCrop();
		return true;
	}
}
