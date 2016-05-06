
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
	private final CropperBackground background;

	public Cropper (final UserInputBar userInputBar) {
		this.master = userInputBar;
		this.btnDone = new CropperButtonWrapper(this);
		this.btnCancel = new CropperButtonWrapper(this);
		this.btnRotate = new CropperButtonWrapper(this);
		this.btnReset = new CropperButtonWrapper(this);
		this.background = new CropperBackground(this);
	}

	public void setup (final Layer root) {
		this.root = root;

		{
			final Button button = root.findComponent("btnRotate");
			this.btnRotate.setup(button);
		}
		{
			final Button button = root.findComponent("btnCancel");
			this.btnCancel.setup(button);
		}
		{
			final Button button = root.findComponent("btnDone");
			this.btnDone.setup(button);
		}
		{
			final Button button = root.findComponent("btnReset");
			this.btnReset.setup(button);
		}
		{
			final Layer component_root = this.root.findComponent("bg-black");
			this.background.setup(component_root);
		}

	}

	public void updateScreen (final Rectangle viewport_update) {
		this.background.updateScreen(viewport_update);

		this.btnCancel.update(this.background, viewport_update);
		this.btnDone.update(this.background, viewport_update);
		this.btnReset.update(this.background, viewport_update);
		this.btnRotate.update(this.background, viewport_update);

	}

	public void hide () {
		this.root.hide();
	}

}
