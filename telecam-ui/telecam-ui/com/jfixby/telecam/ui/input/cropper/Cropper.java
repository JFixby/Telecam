
package com.jfixby.telecam.ui.input.cropper;

import com.jfixby.cmns.api.floatn.FixedFloat2;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.input.CustomInput;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.telecam.ui.UserInputBar;

public class Cropper {

	private Layer root;
	private final UserInputBar master;
	private final CropperButtonDone btnDone;
	private final CropperButtonCancel btnCancel;
	private final CropperButtonRotate btnRotate;
	private final CropperButtonReset btnReset;
	private final CropperBackground background;
	private double baseOffsetX;

	public Cropper (final UserInputBar userInputBar) {
		this.master = userInputBar;
		this.btnDone = new CropperButtonDone(this);
		this.btnCancel = new CropperButtonCancel(this, this.btnDone);
		this.btnRotate = new CropperButtonRotate(this);
		this.btnReset = new CropperButtonReset(this, this.btnCancel);
		this.background = new CropperBackground(this);
	}

	public void setup (final Layer root) {
		this.root = root;

		{
			final CustomInput button = root.findComponent("btnCancel");
			this.btnCancel.setup(button);
			this.baseOffsetX = this.btnCancel.getBaseOffsetX();
		}

		{
			final CustomInput button = root.findComponent("btnRotate");
			this.btnRotate.setup(button);
		}

		{
			final CustomInput button = root.findComponent("btnDone");
			this.btnDone.setup(button);
			this.btnDone.setBaseOffsetX(this.baseOffsetX);

		}
		{
			final CustomInput button = root.findComponent("btnReset");
			this.btnReset.setup(button);
		}
		{
			final Layer component_root = this.root.findComponent("bg-black");
			this.background.setup(component_root);
		}

	}

	public void updateScreen (final Rectangle viewport_update) {
		this.background.updateScreen(viewport_update);

		this.btnCancel.update(viewport_update);
		this.btnDone.update(viewport_update);
		this.btnReset.update(viewport_update);
		this.btnRotate.update(viewport_update);

	}

	public void hide () {
		this.root.hide();
	}

	public CropperBackground getBackground () {
		return this.background;
	}

	public FixedFloat2 getOriginalSceneDimentions () {
		return this.master.getOriginalSceneDimentions();
	}

}
