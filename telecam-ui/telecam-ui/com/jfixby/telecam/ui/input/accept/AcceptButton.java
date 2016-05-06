
package com.jfixby.telecam.ui.input.accept;

import com.jfixby.cmns.api.log.L;
import com.jfixby.r3.api.ui.unit.input.MouseMovedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDownEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDraggedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchUpEvent;

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
		return false;
	}

	@Override
	public boolean onTouchUp (final TouchUpEvent input_event) {
		L.d("click", this);
		return false;
	}

	@Override
	public boolean onTouchDragged (final TouchDraggedEvent input_event) {
		return false;
	}

}
