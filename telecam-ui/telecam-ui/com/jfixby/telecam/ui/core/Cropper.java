
package com.jfixby.telecam.ui.core;

import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.input.Button;
import com.jfixby.r3.api.ui.unit.layer.Layer;

public class Cropper {

	private Layer root;
	private final UserInputBar master;
	private final CropperButtonWrapper btnDone;
	private final CropperButtonWrapper btnCancel;
	private final CropperButtonWrapper btnRotate;
	private final CropperButtonWrapper btnReset;

	public Cropper (final UserInputBar userInputBar) {
		this.master = userInputBar;
		this.btnDone = new CropperButtonWrapper(this);
		this.btnCancel = new CropperButtonWrapper(this);
		this.btnRotate = new CropperButtonWrapper(this);
		this.btnReset = new CropperButtonWrapper(this);
	}

	public void setup (final Layer component_root) {
		this.root = component_root;
		component_root.listChildren().print("component_root.listChildren()");
		{
			final Button button = component_root.findComponent("btnRotate");
			this.btnRotate.setup(button);
		}
		{
			final Button button = component_root.findComponent("btnCancel");
			this.btnCancel.setup(button);
		}
		{
			final Button button = component_root.findComponent("btnDone");
			this.btnDone.setup(button);
		}
		{
			final Button button = component_root.findComponent("btnReset");
			this.btnReset.setup(button);
		}

	}

	public void updateScreen (final Rectangle viewport_update) {
	}

}
