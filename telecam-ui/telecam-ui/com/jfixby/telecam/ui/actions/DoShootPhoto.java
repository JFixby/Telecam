
package com.jfixby.telecam.ui.actions;

import com.jfixby.cmns.api.log.L;
import com.jfixby.r3.api.ui.UI;
import com.jfixby.r3.api.ui.UIAction;
import com.jfixby.telecam.ui.TelecamUnit;

public class DoShootPhoto implements UIAction<TelecamUnit> {
	@Override
	public void perform (final TelecamUnit ui) {
		L.d("Shoot photo!");
		UI.pushAction(UIOperations.doBlink);
		UI.pushAction(UIOperations.goAcceptDecline);
	}

	@Override
	public void start (final TelecamUnit ui) {
	}

	@Override
	public boolean isDone (final TelecamUnit ui) {
		return true;
	}
}
