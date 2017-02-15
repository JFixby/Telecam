
package com.jfixby.telecam.ui.input.flash;

import com.jfixby.r3.api.ui.unit.animation.AnimationLifecycleListener;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.update.OnUpdateListener;
import com.jfixby.r3.api.ui.unit.update.UnitClocks;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.sys.Sys;
import com.jfixby.telecam.ui.TelecamUnit;

public class SwitchFlashButtonAnimator implements OnUpdateListener {

	private static final int INDEX_OFFSET = 0;
	final Set<FlashIconWrapper> list = Collections.newSet();
	private final SwitchFlashButton master;
	private double y0;
	private double H;

	public SwitchFlashButtonAnimator (final SwitchFlashButton switchFlashButton) {
		this.master = switchFlashButton;

	}

	OnUpdateListener animator = this;
	private long currentTime;
	private double progress;

	public void setup (final Layer root) {
		root.attachComponent(this.animator);
		this.y0 = this.master.getBaseOffsetY();
		this.H = this.master.getHeight();
		this.roll(0);
	}

	public void updateX (final double x) {
		for (int i = 0; i < this.list.size(); i++) {
			final FlashIconWrapper icon = this.list.getElementAt(i);
			icon.setX(x);
		}
	}

	public void add (final FlashIconWrapper flash_auto) {
		this.list.add(flash_auto);
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
			this.current_roll++;
			this.roll(this.current_roll);
			return;
		}
		this.roll(this.current_roll + this.progress);
	}

	int current_roll = 0;

	private void roll (final double roll) {

		for (int i = 0; i < this.list.size(); i++) {
			final FlashIconWrapper icon = this.list.getElementAt(i);
			icon.setIndexedPosition(this.y0, i, roll);

		}
	}

	long delta = TelecamUnit.ANIMATION_DELTA;
	private long begin;
	private long end;
	private AnimationLifecycleListener animation_done_listener;
	private boolean animating;
	private int beginRoll;

	public void animate (final AnimationLifecycleListener animation_done_listener) {
		this.begin = Sys.SystemTime().currentTimeMillis();
		this.end = this.begin + this.delta;
		this.animation_done_listener = animation_done_listener;
		this.animating = true;
		this.beginRoll = this.current_roll;
	}

}
