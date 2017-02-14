
package com.jfixby.telecam.ui;

import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.update.UnitClocks;
import com.jfixby.r3.api.ui.unit.update.OnUpdateListener;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.scarabei.api.util.JUtils;
import com.jfixby.scarabei.api.util.ProgressIndicator;
import com.jfixby.telecam.ui.input.vplay.VidepPlayPause;

public class ProgressBar implements OnUpdateListener {

	private Layer root;
	OnUpdateListener updater = this;

	private Raster line;

	final ProgressIndicator progress = JUtils.newProgressIndicator();

	private double progressValue;

	private double maxWidth;

	private final VidepPlayPause playResume;
	private final UserInputBar master;
	private VideoPlayerState videoPlayerState;

	public ProgressBar (final UserInputBar userPanel) {
		this.master = userPanel;
		this.playResume = userPanel.getVidepPlayPause();
	}

	public void setup (final Layer root) {
		this.root = root;
		root.attachComponent(this.updater);
		this.line = root.findComponent();
		this.progressValue = 0;
		this.updateRaster();
	}

	public void update (final BackgroundGray bgGray) {
		this.line.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.LEFT, ORIGIN_RELATIVE_VERTICAL.BOTTOM);

		this.line.setPosition(bgGray.getTopLeftCorner());

		this.maxWidth = bgGray.getWidth();

		this.updateRaster();

	}

	public void hide () {
		this.root.hide();
	}

	public void show () {
		this.root.show();
	}

	public void updateRaster () {
		this.line.shape().setWidth(this.maxWidth * this.progressValue);
	}

	public void setProgress (final double progressValue) {
		this.progressValue = progressValue;
	}

	public void updateState (final VideoPlayerState videoPlayerState) {
		this.videoPlayerState = videoPlayerState;
		this.playResume.update(videoPlayerState);
		this.readProgress();
	}

	@Override
	public void onUpdate (final UnitClocks unit_clock) {
		if (this.videoPlayerState == VideoPlayerState.PLAYING) {
			this.readProgress();
		}
	}

	private void readProgress () {
		final double position = this.master.getVideoPlayer().getCurrentVideoPosition();
		final double length = this.master.getVideoPlayer().getVideoTotalLenght();
		final double progress = position / length;
		this.setProgress(progress);
		this.updateRaster();
	}

}
