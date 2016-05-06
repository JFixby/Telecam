
package com.jfixby.telecam.ui.core.input.accdecc;

import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.telecam.ui.core.TelecamUnit;
import com.jfixby.telecam.ui.core.UserInput;

public class AcceptDecline {
	private Layer root;
	private final AcceptButton acceptButton;
	private final DeclineButton declineButton;
	private final UserInput master;

	public AcceptDecline (final UserInput userPanel) {
		this.master = userPanel;
		this.acceptButton = new AcceptButton(this);
		this.declineButton = new DeclineButton(this);
	}

	public void setup (final Layer root) {
		this.root = root;
		this.root.listChildren().print("this.root.listChildren()");
		{
			final Layer button_root = this.root.listChildren().findLayer("accept").getElementAt(0);
			this.acceptButton.setup(button_root);
		}
		{
			final Layer button_root = this.root.listChildren().findLayer("decline").getElementAt(0);
			this.declineButton.setup(button_root);
		}
	}

	public void update (final CanvasPosition position, final Rectangle viewport_update) {
		this.acceptButton.update(position, viewport_update);
		this.declineButton.update(position, viewport_update);
	}

	public TelecamUnit getUnit () {
		return this.master.getUnit();
	}
}
