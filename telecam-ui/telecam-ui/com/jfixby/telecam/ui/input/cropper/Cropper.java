
package com.jfixby.telecam.ui.input.cropper;

import com.jfixby.cmns.api.floatn.FixedFloat2;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.input.CustomInput;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.telecam.ui.FontSettings;
import com.jfixby.telecam.ui.UserInputBar;
import com.jfixby.telecam.ui.input.cropper.rotator.Rotator;
import com.jfixby.telecam.ui.input.cropper.tool.CropperTool;

public class Cropper {

	private Layer root;
	private final UserInputBar master;
	private final CropperButtonDone btnDone;
	private final CropperButtonCancel btnCancel;
	private final CropperButtonRotate btnRotate;
	private final CropperButtonReset btnReset;
	private final CropperBackground background;
	private final CropTouch screenTouch;
	private final Rotator rotator;
	private final CropperTool tool;

	public Cropper (final UserInputBar userInputBar) {
		this.master = userInputBar;

		this.btnCancel = new CropperButtonCancel(this);
		this.btnDone = new CropperButtonDone(this, this.btnCancel);
		this.btnRotate = new CropperButtonRotate(this, this.btnDone);

		this.btnReset = new CropperButtonReset(this, this.btnCancel);
		this.background = new CropperBackground(this);

		this.screenTouch = new CropTouch(this);
		this.rotator = new Rotator(this, this.btnRotate);
		this.tool = new CropperTool(this);

	}

	public void setup (final Layer root) {
		this.root = root;

		{
			final CustomInput button = root.findComponent("btnCancel");
// root.printChildren(this + "");
			this.btnCancel.setup(button);

		}

		{
			final CustomInput button = root.findComponent("btnRotate");
			this.btnRotate.setup(button);
		}

		{
			final CustomInput button = root.findComponent("btnDone");
			this.btnDone.setup(button);

		}
		{
			final CustomInput button = root.findComponent("btnReset");
			this.btnReset.setup(button);
		}
		{
			final Layer button = root.findComponent("crop-screen");
			this.screenTouch.setup(button);
		}
		{
			final Layer component_root = this.root.findComponent("bg-black");
			this.background.setup(component_root);
		}
		{
			final Layer component_root = this.root.findComponent("rotator");
			this.rotator.setup(component_root);
		}
		{
			final Layer component_root = this.root.findComponent("cropper-tool");
			this.tool.setup(component_root);
		}

	}

	public void updateScreen (final Rectangle viewport_update) {
		this.background.updateScreen(viewport_update);

		this.btnCancel.update(viewport_update);
		this.btnDone.update(viewport_update);
		this.btnReset.update(viewport_update);
		this.btnRotate.update(viewport_update);
		this.screenTouch.update(viewport_update);
		this.rotator.update(viewport_update);
		this.tool.update(viewport_update);

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

	public FontSettings getFontSettings () {
		return this.master.getFontSettings();
	}

	public void show () {
		this.root.show();
	}

	public void resetCroppingArea () {
		this.tool.reset();
		this.rotator.reset();

	}

	public CropperButtonReset getResetButton () {
		return this.btnReset;
	}

}
