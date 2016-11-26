
package com.jfixby.telecam.ui.input.vplay;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.CollectionScanner;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.r3.api.ui.unit.input.CustomInput;
import com.jfixby.r3.api.ui.unit.input.MouseMovedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchArea;
import com.jfixby.r3.api.ui.unit.input.TouchDownEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDraggedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchUpEvent;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.user.MouseInputEventListener;
import com.jfixby.telecam.ui.ProgressBar;
import com.jfixby.telecam.ui.UserInputBar;
import com.jfixby.telecam.ui.VideoPlayer;
import com.jfixby.telecam.ui.VideoPlayerState;

public class VidepPlayPause implements MouseInputEventListener, CollectionScanner<TouchArea> {

	private static final String PAUSED = "PAUSED";
	private static final String PLAYING = "PLAYING";
	private Layer root;
	private CustomInput input;
	private Raster play;
	private Raster pause;

	private Collection<TouchArea> touchAreas;
	private final CollectionScanner<TouchArea> touchAreasAligner = this;
	private CanvasPosition position;
	private final ProgressBar progressBar;
	private final UserInputBar master;

	public VidepPlayPause (final UserInputBar userPanel) {
		this.master = userPanel;
		this.progressBar = userPanel.getProgress();
	}

	public void setup (final Layer root) {
		this.root = root;

		this.input = (CustomInput)root.listChildren().getElementAt(0);

		this.input.setInputListener(this);
		this.input.setDebugRenderFlag(false);
		final Collection<Raster> options = this.input.listOptions();
// options.print("options");
// Sys.exit();
		this.play = options.getElementAt(0);
		this.pause = options.getElementAt(1);

		this.touchAreas = this.input.listTouchAreas();

	}

	public void update (final CanvasPosition position) {
		this.position = position;
		this.play.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.play.setPosition(position);

		this.pause.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.pause.setPosition(position);

		Collections.scanCollection(this.touchAreas, this.touchAreasAligner);
	}

	@Override
	public boolean onMouseMoved (final MouseMovedEvent input_event) {
		return true;
	}

	@Override
	public boolean onTouchDown (final TouchDownEvent input_event) {
		final VideoPlayer player = this.master.getVideoPlayer();
		if (player.isPlaying()) {
			player.pause();
		} else if (player.isPaused()) {
			player.resume();
		} else if (player.isStopped()) {
			player.start();
		}
		return true;
	}

	@Override
	public boolean onTouchUp (final TouchUpEvent input_event) {
		return true;
	}

	@Override
	public boolean onTouchDragged (final TouchDraggedEvent input_event) {
		return true;
	}

	@Override
	public void scanElement (final TouchArea element, final long index) {
		element.shape().setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		element.shape().setPosition(this.position);

	}

	public void hide () {
		this.root.hide();
	}

	public void show () {
		this.root.show();
	}

	public void update (final VideoPlayerState videoPlayerState) {
		if (videoPlayerState == VideoPlayerState.PLAYING) {
			this.play.hide();
			this.pause.show();
		} else {
			this.play.show();
			this.pause.hide();
		}
	}

}
