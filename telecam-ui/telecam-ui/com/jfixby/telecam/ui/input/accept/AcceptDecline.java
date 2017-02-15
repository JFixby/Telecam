
package com.jfixby.telecam.ui.input.accept;

import com.jfixby.r3.api.ui.UIAction;
import com.jfixby.r3.api.ui.unit.animation.AnimationLifecycleListener;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.update.OnUpdateListener;
import com.jfixby.r3.api.ui.unit.update.UnitClocks;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.sys.Sys;
import com.jfixby.telecam.ui.TelecamUnit;
import com.jfixby.telecam.ui.UserInputBar;

public class AcceptDecline implements OnUpdateListener {
	private Layer root;
	private final AcceptButton acceptButton;
	private final DeclineButton declineButton;
	private final UserInputBar master;
	private UIAction<TelecamUnit> yesAction;
	private UIAction<TelecamUnit> noAction;

	public AcceptDecline (final UserInputBar userPanel) {
		this.master = userPanel;
		this.acceptButton = new AcceptButton(this);
		this.declineButton = new DeclineButton(this);
	}

	public void setup (final Layer root) {
		this.root = root;

		{
			final Layer button_root = this.root.findComponent("accept");
			this.acceptButton.setup(button_root);
		}
		{
			final Layer button_root = this.root.findComponent("decline");
			this.declineButton.setup(button_root);
		}
		root.attachComponent(this.animator);
	}

	public void update (final CanvasPosition position, final Rectangle viewport_update) {
		this.acceptButton.update(position, viewport_update);
		this.declineButton.update(position, viewport_update);
	}

	public TelecamUnit getUnit () {
		return this.master.getUnit();
	}

	public void hide () {
		this.root.hide();
	}

	public void show () {
		this.root.show();
	}

	public void bindNoAction (final UIAction<TelecamUnit> noAction) {
		this.noAction = noAction;
	}

	public void bindYesAction (final UIAction<TelecamUnit> yesAction) {
		this.yesAction = yesAction;

	}

	public UIAction<TelecamUnit> getYesAction () {
		return this.yesAction;
	}

	public UIAction<TelecamUnit> getNoAction () {
		return this.noAction;
	}

	long delta = TelecamUnit.ANIMATION_DELTA;
	private long begin;
	private long end;
	private AnimationLifecycleListener animation_done_listener;
	private boolean animating;
	private double beginRoll;
	private double current_roll = 0;
	OnUpdateListener animator = this;
	private long currentTime;
	private double progress;

	public void animate (final AnimationLifecycleListener animation_done_listener) {
		this.current_roll = 0;
		this.begin = Sys.SystemTime().currentTimeMillis();
		this.end = this.begin + this.delta;
		this.animation_done_listener = animation_done_listener;
		this.animating = true;
		this.beginRoll = this.current_roll;
		this.roll(this.current_roll);
	}

	@Override
	public void onUpdate (final UnitClocks unit_clock) {
		if (!this.animating) {
			return;
		}
		this.currentTime = Sys.SystemTime().currentTimeMillis() - this.begin;

		this.progress = this.currentTime * 1d / this.delta;

		if (this.progress > 1) {
			this.animating = false;
			this.animation_done_listener.onAnimationDone(null, 0);
			this.current_roll = 1;
			this.roll(this.current_roll);
			return;
		}
		this.roll(this.current_roll + this.progress);
	}

	private void roll (final double radius) {

		this.acceptButton.setRadius(radius);
		this.declineButton.setRadius(radius);
	}
}
