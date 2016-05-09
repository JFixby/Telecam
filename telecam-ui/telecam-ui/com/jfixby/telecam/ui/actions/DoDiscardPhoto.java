
package com.jfixby.telecam.ui.actions;

import com.jfixby.r3.api.ui.UI;
import com.jfixby.telecam.ui.TelecamUnit;

public class DoDiscardPhoto extends TelecamUIAction {

	@Override
	public void start (final TelecamUnit ui) {
	}

	@Override
	public void perform (final TelecamUnit ui) {

	}

	@Override
	public boolean isDone (final TelecamUnit ui) {
		UI.pushAction(TelecamUIAction.goPhotoShoot);
		return true;
	}

}
