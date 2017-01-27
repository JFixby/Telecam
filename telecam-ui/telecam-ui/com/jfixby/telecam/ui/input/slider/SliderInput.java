
package com.jfixby.telecam.ui.input.slider;

import com.jfixby.r3.api.ui.unit.input.MouseMovedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDownEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDraggedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchUpEvent;
import com.jfixby.r3.api.ui.unit.user.MouseInputEventListener;
import com.jfixby.telecam.ui.actions.TelecamUIAction;

public class SliderInput extends MouseInputEventListener {

	private final Slider slider;

	public SliderInput (final Slider slider) {
		this.slider = slider;
	}

	@Override
	public boolean onMouseMoved (final MouseMovedEvent input_event) {
		return false;
	}

	@Override
	public boolean onTouchDown (final TouchDownEvent input_event) {
		TelecamUIAction.disableInput.submit();
		if (this.slider.state == Slider.PHOTO) {
			TelecamUIAction.switchToVideoShoot.submit();
		} else {
			TelecamUIAction.switchToPhotoShoot.submit();
		}
		TelecamUIAction.enableInput.submit();
		return true;
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
