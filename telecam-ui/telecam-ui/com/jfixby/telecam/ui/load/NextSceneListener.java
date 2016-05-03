package com.jfixby.telecam.ui.load;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.r3.api.ui.Intent;
import com.jfixby.r3.api.ui.UnitsMachine;
import com.jfixby.r3.api.ui.unit.input.MouseMovedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDownEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDraggedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchUpEvent;
import com.jfixby.r3.api.ui.unit.input.U_OnMouseInputEventListener;

public class NextSceneListener implements U_OnMouseInputEventListener {

	private AssetID scene_id;

	public NextSceneListener(AssetID scene_id) {
		this.scene_id = scene_id;
	}

	@Override
	public boolean onMouseMoved(MouseMovedEvent event) {
		return false;
	}

	@Override
	public boolean onTouchDown(TouchDownEvent event) {
		Intent intent = UnitsMachine.newIntent(scene_id);

		UnitsMachine.nextUnit(intent);
		return true;
	}

	@Override
	public boolean onTouchUp(TouchUpEvent event) {
		return false;
	}

	@Override
	public boolean onTouchDragged(TouchDraggedEvent event) {
		return false;
	}

}
