
package com.jfixby.telecam.ui.input.accept;

import com.jfixby.r3.api.ui.UI;
import com.jfixby.r3.api.ui.UIAction;
import com.jfixby.r3.api.ui.unit.input.MouseMovedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDownEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDraggedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchUpEvent;
import com.jfixby.telecam.ui.TelecamUnit;

public class AcceptButton extends AccDeccButton {

	public AcceptButton (final AcceptDecline acceptDecline) {
		super(acceptDecline, 1.5f);
	}

	@Override
	public boolean onMouseMoved (final MouseMovedEvent input_event) {
		return false;
	}

	@Override
	public boolean onTouchDown (final TouchDownEvent input_event) {
		final AcceptDecline master = this.getMaster();
		final UIAction<TelecamUnit> yes = master.getYesAction();
		if (yes != null) {
			UI.pushAction(yes);
			return true;
		}
		return false;
	}

	@Override
	public boolean onTouchUp (final TouchUpEvent input_event) {

		return false;
	}

	@Override
	public boolean onTouchDragged (final TouchDraggedEvent input_event) {
		return false;
	}

}
