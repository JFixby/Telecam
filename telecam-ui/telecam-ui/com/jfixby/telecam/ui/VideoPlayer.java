
package com.jfixby.telecam.ui;

import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.cmns.api.taskman.Job;
import com.jfixby.cmns.api.taskman.Task;
import com.jfixby.cmns.api.taskman.TaskManager;
import com.jfixby.cmns.api.util.JUtils;
import com.jfixby.cmns.api.util.StateSwitcher;

public class VideoPlayer {
	private final StateSwitcher<VideoPlayerState> state;

	public VideoPlayer () {
		this.state = JUtils.newStateSwitcher(VideoPlayerState.STOPPED);
		this.task = TaskManager.newTask(this.job);
	}

	private double currentVideoPosition;
	private double videoTotalLenght;
	private ProgressBar progressListener;
	private final Job job = new Job() {

		private long lastCall;
		private double delta;
		private long current;

		@Override
		public void doStart () throws Throwable {
		}

		@Override
		public void doDo () throws Throwable {
			if (VideoPlayer.this.state.stateIs(VideoPlayerState.PLAYING)) {
				this.delta = 0;
				this.current = Sys.SystemTime().currentTimeMillis();
				if (this.lastCall > this.current) {
					this.delta = 0;
				} else {
					this.delta = (this.current - this.lastCall) / 1000d;
				}
				VideoPlayer.this.currentVideoPosition = VideoPlayer.this.currentVideoPosition + this.delta;
				this.lastCall = this.current;
			} else {
				this.lastCall = Long.MAX_VALUE;
			}
			if (VideoPlayer.this.currentVideoPosition >= VideoPlayer.this.videoTotalLenght) {
				VideoPlayer.this.stop();
			}
		}

		@Override
		public boolean isDone () {
			return false;
		}

	};
	private final Task task;

	public double getCurrentVideoPosition () {
		return this.currentVideoPosition;
	}

	public double getVideoTotalLenght () {
		return this.videoTotalLenght;
	}

	public void reset () {
		this.currentVideoPosition = 0;
		this.videoTotalLenght = 10;
		this.state.switchState(VideoPlayerState.STOPPED);

	}

	public void start () {
		this.state.expectState(VideoPlayerState.STOPPED);
		this.state.switchState(VideoPlayerState.PLAYING);
		this.updateProgress();

	}

	public boolean isPlaying () {
		return this.state.stateIs(VideoPlayerState.PLAYING);
	}

	public void pause () {
		this.state.expectState(VideoPlayerState.PLAYING);
		this.state.switchState(VideoPlayerState.PAUSED);
		this.updateProgress();
	}

	public boolean isPaused () {
		return this.state.stateIs(VideoPlayerState.PAUSED);
	}

	public void resume () {
		this.state.expectState(VideoPlayerState.PAUSED);
		this.state.switchState(VideoPlayerState.PLAYING);
		this.updateProgress();
	}

	public boolean isStopped () {
		return this.state.stateIs(VideoPlayerState.STOPPED);
	}

	public void stop () {
		this.currentVideoPosition = 0;
		this.state.switchState(VideoPlayerState.STOPPED);
		this.updateProgress();
	}

	private void updateProgress () {
		if (this.progressListener == null) {
			return;
		}
		this.progressListener.updateState(this.state.currentState());
	}

	public void setProgressListener (final ProgressBar progressBar) {
		this.progressListener = progressBar;
	}

}
